package com.eakurnikov.instrsample.view

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import com.eakurnikov.instrsample.R

class SimpleActivity : AppCompatActivity() {

    companion object {
        fun start(context: Context): Unit =
            context.startActivity(Intent(context, SimpleActivity::class.java))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_simple)

        val input = findViewById<EditText>(R.id.et_simple)

        findViewById<Button>(R.id.btn_simple_delete).setOnClickListener {
            input.text = null
        }

        findViewById<Button>(R.id.btn_simple_next).setOnClickListener {
            VerySimpleActivity.start(this, input.text.toString())
        }
    }
}
