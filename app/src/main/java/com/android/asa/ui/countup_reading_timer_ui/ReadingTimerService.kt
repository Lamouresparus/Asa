package com.android.asa.ui.countup_reading_timer_ui

import android.app.Service
import android.content.Intent
import android.os.Build
import android.os.IBinder
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.android.asa.R
import com.android.asa.extensions.secondsToTime
import com.android.asa.utils.NotificationHelper
import io.reactivex.Observable
import io.reactivex.Scheduler
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import java.io.Serializable
import java.util.concurrent.TimeUnit

class ReadingTimerService : Service() {

    private val compositeDisposable = CompositeDisposable()

    private var timerState: TimerState = TimerState.INITIALIZED

    private var timeElapse = 0

    private val notificationHelper by lazy { NotificationHelper(this) }

    override fun onBind(intent: Intent?): IBinder? = null

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        super.onStartCommand(intent, flags, startId)
        intent?.extras?.run {
            when (getSerializable(SERVICE_COMMAND) as TimerState) {
                TimerState.START -> startTimer()
                TimerState.PAUSE -> pauseTimerService()
                TimerState.STOP -> endTimerService()
                else -> return START_NOT_STICKY
            }
        }
        return START_NOT_STICKY
    }

    private fun broadcastUpdate() {
        if (timerState == TimerState.START) {
            // to notification tray
            notificationHelper.updateNotification(timeElapse.secondsToTime())
            // to fragment view
            publishTimeUpdateToUI()
        } else if (timerState == TimerState.STOP) {
            notificationHelper.updateNotification("Reading ended")
        }
    }

    private fun publishTimeUpdateToUI() {
        val intent = Intent(ACTION_READING_TIMER).apply {
            putExtra(READING_TIMER_TEXT, timeElapse)
        }
        LocalBroadcastManager.getInstance(this).sendBroadcast(intent)
    }

    private fun startTimer() {
        val disposable = Observable
            .interval(1, TimeUnit.SECONDS)
            .doOnSubscribe {
                timerState = TimerState.START
                startForeground(NotificationHelper.NOTIFICATION_ID, notificationHelper.getNotification())
            }
            .doOnDispose { timerState = TimerState.STOP }
            .observeOn(Schedulers.io())
            .doOnNext {
                if (timerState == TimerState.START) {
                    timeElapse++
                    broadcastUpdate()
                }
            }.subscribe()
        compositeDisposable.add(disposable)
    }

    private fun pauseTimerService() {
        timerState = TimerState.PAUSE
        compositeDisposable.clear()
        stopService()
    }

    private fun endTimerService() {
        timerState = TimerState.STOP
        compositeDisposable.clear()
        timeElapse = 0
        stopService()
    }

    private fun stopService() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            stopForeground(true)
        } else {
            stopSelf()
        }
    }

    companion object {
        const val SERVICE_COMMAND = "ReadingTimerServiceCommand"
        const val READING_TIMER_TEXT = "NotificationText"
        const val ACTION_READING_TIMER = "com.android.asa.READING_TIMER"
    }
}

enum class TimerState : Serializable {
    INITIALIZED,
    START,
    PAUSE,
    STOP
}