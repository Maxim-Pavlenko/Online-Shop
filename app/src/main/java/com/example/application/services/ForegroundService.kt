package com.example.application.services

import android.annotation.SuppressLint
import android.app.NotificationManager
import android.app.Service
import android.content.Intent
import android.os.Build
import android.os.IBinder
import androidx.annotation.RequiresApi
import com.example.application.MainActivity
import com.example.application.utils.NotificationFactory
import com.example.application.utils.getPendingIntent
import java.util.concurrent.Executors

private const val NOTIFICATION_ID = 1

class ForegroundService: Service() {
    override fun onBind(intent: Intent?): IBinder? = null

    override fun onCreate() {
        super.onCreate()
        println("BACK: onCreate")
    }
    @RequiresApi(Build.VERSION_CODES.O)
    @SuppressLint("ForegroundServiceType")
    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        println("BACK: onStartCommand")

        val notificationFactory = NotificationFactory(
            context = this,
            pendingIntent = getPendingIntent(MainActivity::class.java)
        )
        startForeground(
            NOTIFICATION_ID,
            notificationFactory.create(
                title = "Foreground Service!",
                text =  "Start..."
            )
        )
        Executors.newSingleThreadExecutor().execute {
            for (i in 1..100){
                println("BACK: ${i}")
                val notificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
                notificationManager.notify(
                    NOTIFICATION_ID,
                    notificationFactory.create(
                        text = "Progress: ${i}"
                    )
                )
            }
        }
        stopSelf()
        return START_NOT_STICKY
    }

    override fun onDestroy() {
        super.onDestroy()
        println("BACK: onDestroy")
    }
}