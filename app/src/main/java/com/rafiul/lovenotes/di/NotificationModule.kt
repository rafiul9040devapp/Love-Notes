package com.rafiul.lovenotes.di

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.rafiul.lovenotes.R
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object NotificationModule {

    private const val NOTIFICATION_CHANNEL_ID = "com.rafiul.lovenotes.di"
    private const val NOTIFICATION_CHANNEL_NAME = "General Notifications"
    private const val NOTIFICATION_TITLE = "LOVE NOTES"

    @Singleton
    @Provides
    fun providesNotificationBuilder(@ApplicationContext context: Context): NotificationCompat.Builder{
        return NotificationCompat.Builder(context, NOTIFICATION_CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setContentTitle(NOTIFICATION_TITLE)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
    }

    @Singleton
    @Provides
    fun providesNotificationManager(@ApplicationContext context: Context):NotificationManagerCompat{
        val notificationManager = NotificationManagerCompat.from(context)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel(NOTIFICATION_CHANNEL_ID, NOTIFICATION_CHANNEL_NAME, importance)
            notificationManager.createNotificationChannel(channel)
        }
        return notificationManager
    }
}