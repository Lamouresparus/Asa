package com.android.asa.ui.countup_reading_timer_ui

import android.app.*
import android.app.NotificationManager.IMPORTANCE_LOW
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.*
import android.util.Log
import android.widget.Chronometer
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import androidx.lifecycle.MutableLiveData
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.android.asa.MainActivity
import com.android.asa.R
import com.android.asa.utils.Constants
import com.android.asa.utils.Constants.CHANNEL_ID
import com.android.asa.utils.Constants.CHANNEL_NAME
import com.android.asa.utils.Constants.INTENT_SHOW_TIMER_FRAGMENT
import com.android.asa.utils.Constants.INTENT_USER_COURSE_DATA_BUNDLE
import com.android.asa.utils.Constants.NOTIFICATION_ID
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject

@AndroidEntryPoint
class CountUpTimerService : Service() {
    private val mBinder: IBinder = TimerBinder()
    private lateinit var chronometer: Chronometer
    private var isBound = false
    var userCourseData: UserCourses = UserCourses("", "")
    private val job = Job()
    var timeFlow: MutableStateFlow<String> = MutableStateFlow("")
    var totalTimeInMilli: MutableLiveData<Long> = MutableLiveData(0L)

    @Inject
    lateinit var baseNotificationBuilder: NotificationCompat.Builder

    lateinit var curNotificationBuilder: NotificationCompat.Builder


    private var notificationReceiver: BroadcastReceiver =
            object : BroadcastReceiver() {
                override fun onReceive(context: Context, intent: Intent) {
                    when (intent.extras?.getString(Constants.ACTION_NOTIFICATION_NAME)) {
                        Constants.ACTION_STOP -> {
                            stopForegroundService()
                        }
                    }
                }
            }


    override fun onCreate() {
        super.onCreate()
        curNotificationBuilder = baseNotificationBuilder
        startForegroundService()

        isBound = true
        chronometer = Chronometer(this)
        chronometer.base = SystemClock.elapsedRealtime()
        chronometer.start()
    }


    @RequiresApi(Build.VERSION_CODES.O)
    private fun createNotificationChannel(notificationManager: NotificationManager) {
        val channel = NotificationChannel(
                CHANNEL_ID,
                CHANNEL_NAME,
                IMPORTANCE_LOW
        )
        notificationManager.createNotificationChannel(channel)
    }

    private fun startForegroundService() {

        val notificationManager = this.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            createNotificationChannel(notificationManager)
        }
        startForeground(NOTIFICATION_ID, baseNotificationBuilder.build())

        registerReceiver(notificationReceiver, IntentFilter(Constants.ACTION_NOTIFICATION_KEY))

    }


    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        val bundle = Bundle().apply {
            putParcelable("userCourses", userCourseData)
        }
        val notificationManager = this.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val pendingIntent = PendingIntent.getActivity(
                this,
                0,
                Intent(this, MainActivity::class.java).apply {
                    putExtra(INTENT_USER_COURSE_DATA_BUNDLE, bundle)
                    putExtra(INTENT_SHOW_TIMER_FRAGMENT, true)
                    addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_SINGLE_TOP)
                }
                , PendingIntent.FLAG_UPDATE_CURRENT
        )
        CoroutineScope(job).launch(Dispatchers.IO) {

            // while isBound is true, means the service has a work to do because it is used in
            // While loop, So the service does not stopped and you should break from While loop.
            // Otherwise it recreates the service and notification again (after clicking on stop action)

            while (isBound) {
                if (!isBound) break

                timeFlow.value = getTimestamp().also { time ->
                    val notification = curNotificationBuilder
                            .setContentText(time)
                            .setContentIntent(pendingIntent)
                    notificationManager.notify(NOTIFICATION_ID, notification.build())
                }
//                Log.d(TAG, timeFlow.value)
                delay(1000)
            }
        }

        return START_NOT_STICKY
    }

    override fun onBind(intent: Intent): IBinder {
        isBound = true
        return mBinder
    }


    override fun onDestroy() {
        super.onDestroy()
        Log.i(TAG, "Stop Service ...............................")
        isBound = false
        chronometer.stop()
        unregisterReceiver(notificationReceiver)
    }

    private fun getTimestamp(): String {
        val elapsedMillis = SystemClock.elapsedRealtime() - chronometer.base
        totalTimeInMilli.postValue(elapsedMillis)

        val hours = (elapsedMillis / 3600000).toInt()
        val minutes = (elapsedMillis - hours * 3600000).toInt() / 60000
        val seconds = (elapsedMillis - hours * 3600000 - minutes * 60000).toInt() / 1000

        val time = getString(R.string.time, hours, minutes, seconds)

        sendBroadcastEvent(Constants.ACTION_TIME_KEY, time)

        return time
    }

    private fun sendBroadcastEvent(action: String, time: String) {
        val timerIntent = Intent(action).apply {
            putExtra(Constants.ACTION_TIME_VALUE, time)
        }
        LocalBroadcastManager.getInstance(this).sendBroadcast(timerIntent)
    }

    private fun stopForegroundService() {
        Log.d(TAG, "Stop foreground service.")

        isBound = false

        chronometer.stop()

        // Stop foreground service and remove the notification.
        stopForeground(true)

        // Stop the foreground service.
        stopSelf()

        // TODO: send message to activity when Timer going to be stopped (optional)
        sendBroadcastEvent(Constants.ACTION_TIME_KEY, Constants.ACTION_TIMER_STOP)
    }

    inner class TimerBinder : Binder() {
        val service: CountUpTimerService
            get() = this@CountUpTimerService
    }

    companion object {
        private const val TAG = "CountUpTimerService"
    }
}