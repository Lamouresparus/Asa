package com.android.asa.di

import android.app.Notification
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.drawable.Icon
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import androidx.core.content.res.ResourcesCompat.getColor
import androidx.navigation.Navigator
import com.android.asa.MainActivity
import com.android.asa.R
import com.android.asa.ui.reading_time_set_up.ReadingTimeActivity
import com.android.asa.ui.reading_ui.NotificationBroadcastReceiver
import com.android.asa.utils.Constants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ServiceComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.android.scopes.ServiceScoped
import javax.inject.Named


@Module
@InstallIn(ServiceComponent::class)
object ServiceModule {


    @ServiceScoped
    @Provides
    fun provideMainActivityPendingIntent(
            @ApplicationContext app: Context
    ) = PendingIntent.getActivity(
            app,
            0,
            Intent(app, MainActivity::class.java).apply {
                putExtra(Constants.INTENT_SHOW_TIMER_FRAGMENT, true)
                flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            }, 0
    )

    @ServiceScoped
    @Provides
    @Named("stopService")
    fun providePendingIntentToStopService(
            @ApplicationContext app: Context
    ) = PendingIntent.getBroadcast(
            app, 0, Intent(app, NotificationBroadcastReceiver::class.java).apply {
        action = Constants.ACTION_STOP
    }, PendingIntent.FLAG_UPDATE_CURRENT
    )

    @RequiresApi(Build.VERSION_CODES.M)
    @ServiceScoped
    @Provides
    fun provideBaseNotificationBuilder(
            @ApplicationContext app: Context,
            pendingIntentMain: PendingIntent,
            @Named("stopService") pendingIntentStop: PendingIntent
    ) = NotificationCompat.Builder(app, Constants.CHANNEL_ID)
            .setContentTitle("Timer")
            .setContentText("0")
            .setSmallIcon(R.drawable.ic_mdi_alarm)
            .setContentIntent(pendingIntentMain)
            .setAutoCancel(true)
            .setOnlyAlertOnce(true)
            .setOngoing(true)
//            .setColor(getColor(R.color.purple_700,54))
            .setColorized(true)
            .setCategory(Notification.CATEGORY_PROGRESS)
            .setTicker(R.string.time.toString())
            .setAutoCancel(true)
            //.setCustomContentView(RemoteViews(this.packageName, R.layout.layout_notification))
            .addAction(R.drawable.ic_baseline_close_24, "Stop", pendingIntentStop
            )
}