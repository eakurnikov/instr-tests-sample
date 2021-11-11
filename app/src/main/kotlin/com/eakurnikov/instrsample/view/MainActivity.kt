package com.eakurnikov.instrsample.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.eakurnikov.instrsample.R

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.AppTheme)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        findViewById<Button>(R.id.btn_simple_screen).setOnClickListener {
            SimpleActivity.start(this)
        }

        findViewById<Button>(R.id.btn_flaky_screen).setOnClickListener {
            FlakyActivity.start(this)
        }
    }
}