package com.ms.to_dolistapp.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.ms.to_dolistapp.R
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}