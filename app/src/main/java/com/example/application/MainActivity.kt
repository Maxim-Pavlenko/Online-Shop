package com.example.application

import android.content.ComponentName
import android.content.Intent
import android.content.ServiceConnection
import android.os.Build
import android.os.Bundle
import android.os.IBinder
import androidx.appcompat.app.AppCompatActivity
import com.example.application.databinding.ActivityMainBinding
import com.example.application.services.BackgroundService
import com.example.application.services.BoundService
import com.example.application.services.ForegroundService

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private val connection = object: ServiceConnection {
        override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
            val uploadBinder = (service as BoundService.UploadBinder)
            uploadBinder.subscribeToProgress { progress ->
                runOnUiThread {
                    binding.progress.text = "$progress"
                }
            }
        }

        override fun onServiceDisconnected(name: ComponentName?) {}

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        binding.startBackground.setOnClickListener {
            Intent(this, BackgroundService::class.java).also {
                startService(it)
            }
        }

        binding.startForeground.setOnClickListener {
            Intent(this, ForegroundService::class.java).also {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    startForegroundService(it)
                }
            }
        }

        binding.startBound.setOnClickListener {
            Intent(this, BoundService::class.java).also {
                startService(it)
            }
        }

        binding.tryIPC.setOnClickListener {}

        binding.startWork.setOnClickListener { }
    }

    override fun onStart() {
        super.onStart()
        Intent(this, BoundService::class.java).also {
            bindService(it, connection, BIND_AUTO_CREATE)
        }
    }

    override fun onStop() {
        super.onStop()
        unbindService(connection)
    }
}