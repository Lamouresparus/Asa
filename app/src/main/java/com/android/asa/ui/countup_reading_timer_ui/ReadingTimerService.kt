package com.android.asa.ui.countup_reading_timer_ui

import android.app.Service
import android.content.Context
import android.content.Intent
import android.os.IBinder
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.android.asa.extensions.milliSecondsToTime
import com.android.asa.utils.NotificationHelper
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.TimeUnit

class ReadingTimerService : Service() {

    private val compositeDisposable = CompositeDisposable()

    private val notificationHelper by lazy { NotificationHelper(this) }

    private var timeElapseInMills = 0L

    override fun onBind(intent: Intent?): IBinder? = null

    override fun onCreate() {
        super.onCreate()
        startForeground(NotificationHelper.NOTIFICATION_ID, notificationHelper.getNotification())
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        super.onStartCommand(intent, flags, startId)
        intent?.extras?.run {
            val ordinal = getInt(SERVICE_COMMAND, 0)
            when (TimerActions.values()[ordinal]) {
                TimerActions.START -> startTimer()
                TimerActions.PAUSE -> pauseTimerService()
                TimerActions.RESUME -> resumeTimerService()
                TimerActions.STOP -> endTimerService()
            }
        }
        return START_NOT_STICKY
    }

    private fun broadcastUpdate() {
        if (timerState == TimerState.RUNNING) {
            publishTimeUpdateToUI()
            notificationHelper.updateNotification(timeElapseInMills.milliSecondsToTime())
        } else if (timerState == TimerState.STOPPED) {
            notificationHelper.updateNotification("Reading ended")
        }
    }

    private fun publishTimeUpdateToUI() {
        val intent = Intent(ACTION_READING_TIMER).apply {
            putExtra(READING_TIMER_TEXT, timeElapseInMills)
        }
        LocalBroadcastManager.getInstance(this).sendBroadcast(intent)
    }

    private fun startTimer() {
        if (timerState == TimerState.RUNNING) return
        Observable
            .interval(1, TimeUnit.SECONDS)
            .doOnSubscribe {
                timerState = TimerState.RUNNING
                startForeground(NotificationHelper.NOTIFICATION_ID, notificationHelper.getNotification())
            }
            .observeOn(Schedulers.io())
            .doOnNext {
                if (timerState == TimerState.RUNNING) {
                    timeElapseInMills++
                    broadcastUpdate()
                }
            }.subscribe().addToContainer()
    }

    private fun pauseTimerService() {
        timerState = TimerState.PAUSED
    }

    private fun resumeTimerService() {
        timerState = TimerState.RUNNING
    }

    private fun endTimerService() {
        compositeDisposable.clear()
        timerState = TimerState.STOPPED
        timeElapseInMills = 0L
        publishTimeUpdateToUI()
        stopService()
    }

    private fun stopService() {
        stopForeground(true)
        stopSelf()
    }

    override fun onDestroy() {
        compositeDisposable.clear()
        timerState = TimerState.IDLE
        super.onDestroy()
    }

    private fun Disposable.addToContainer() = compositeDisposable.add(this)

    companion object {
        const val SERVICE_COMMAND = "ReadingTimerServiceCommand"
        const val READING_TIMER_TEXT = "NotificationText"
        const val ACTION_READING_TIMER = "com.android.asa.READING_TIMER"

        private var timerState: TimerState = TimerState.IDLE

        fun getTimerState() = timerState

        fun intent(context: Context, command: TimerActions): Intent {
            return Intent(context, ReadingTimerService::class.java).apply {
                putExtra(SERVICE_COMMAND, command.ordinal)
            }
        }
    }
}

enum class TimerState {
    IDLE,
    RUNNING,
    PAUSED,
    STOPPED
}

enum class TimerActions {
    START,
    RESUME,
    PAUSE,
    STOP
}