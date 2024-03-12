package com.example.application

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.firebase.database.FirebaseDatabase

class BaseActivity : AppCompatActivity() {
    private lateinit var dataBase: FirebaseDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        dataBase = FirebaseDatabase.getInstance()
    }
}