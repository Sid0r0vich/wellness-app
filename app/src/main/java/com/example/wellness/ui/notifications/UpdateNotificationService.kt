package com.example.wellness.ui.notifications

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import androidx.core.app.NotificationCompat
import com.example.wellness.R
import kotlin.random.Random

class UpdateNotificationService(private val context: Context) {
    val notificationManager = context.getSystemService(NotificationManager::class.java)
    val notificationChannel = NotificationChannel(
        "update_channel",
        "Update check channel",
        NotificationManager.IMPORTANCE_HIGH
    )

    init {
        notificationManager.createNotificationChannel(notificationChannel)
    }

    fun showNotification(message: String) {
        val notification = NotificationCompat.Builder(context, "update_channel")
            .setContentTitle("Updates")
            .setContentText(message)
            .setSmallIcon(R.drawable.notification_icon)
            .setPriority(NotificationManager.IMPORTANCE_HIGH)
            .setAutoCancel(true)
            .build()

        notificationManager.notify(Random.nextInt(), notification)
    }
}