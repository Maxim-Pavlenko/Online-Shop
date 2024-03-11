package com.example.application.services

import android.app.Service
import android.content.Intent
import android.os.Binder
import android.os.IBinder
import java.util.concurrent.Executors

class BoundService: Service() {

    private var onProgress: ((Int) -> Unit)? = null
    inner class UploadBinder: Binder() {
        fun subscribeToProgress(onProgress: (Int) -> Unit) {
            this@BoundService.onProgress = onProgress
        }
    }
    override fun onBind(intent: Intent?): IBinder = UploadBinder()

    override fun onCreate() {
        super.onCreate()
        println("BACK: onCreate")
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        println("BACK: onStartCommand")

        Executors.newSingleThreadExecutor().execute {
            for (i in 1..100){
                println("BACK: ${i}")
                onProgress?.invoke(i)
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