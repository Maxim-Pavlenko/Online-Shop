package com.example.application.services

import android.app.Service
import android.content.Intent
import android.os.IBinder
import kotlinx.coroutines.delay
import java.util.concurrent.Executors

class BackgroundService: Service() {
    override fun onBind(intent: Intent?): IBinder? = null

    override fun onCreate() {
        super.onCreate()
        println("BACK: onCreate")
    }
    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        println("BACK: onStartCommand")

        Executors.newSingleThreadExecutor().execute {
            for (i in 1..100){
                println("BACK: ${i}")
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