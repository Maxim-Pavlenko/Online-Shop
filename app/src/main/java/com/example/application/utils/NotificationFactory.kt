package com.example.application.utils

import android.app.Notification
import android.app.Notification.Builder
import android.app.PendingIntent
import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import com.example.application.R

class NotificationFactory(
    private val context: Context,
    private val channelId: String = "channelId",
    private val pendingIntent: PendingIntent? = null,
) {

    private var lastTitle = ""
    private var lastText = ""

    @RequiresApi(Build.VERSION_CODES.O)
    fun create(
        title: String? = null,
        text: String? = null,
    ): Notification {
        context.createNotificationChannel(channelId)
        return context.createNotification(
            title = title?.also { lastTitle = it } ?: lastTitle,
            text = text?.also { lastText = it } ?: lastTitle,
            channelId = channelId,
            pendingIntent = pendingIntent,
        )
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun Context.createNotification(
        title: String,
        text: String,
        channelId: String,
        pendingIntent: PendingIntent?,
    ) = Builder(this, channelId)
        .setContentTitle(title)
        .setContentText(text)
        .setSmallIcon(R.mipmap.ic_launcher)
        .setContentIntent(pendingIntent)
        .build()
}