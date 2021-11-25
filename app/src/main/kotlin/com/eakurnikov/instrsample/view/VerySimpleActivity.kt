package com.eakurnikov.instrsample.view

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.eakurnikov.instrsample.R
import com.eakurnikov.instrsample.common.lazyUnsync

class VerySimpleActivity : AppCompatActivity() {

    private val title: TextView by lazyUnsync { findViewById(R.id.tv_very_simple_title) }

    companion object {
        private const val TITLE_KEY: String = "TITLE_KEY"

        fun start(context: Context, title: String): Unit = context.startActivity(
            Intent(context, VerySimpleActivity::class.java).putExtra(TITLE_KEY, title)
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_simple_very)

        intent?.extras?.get(TITLE_KEY)?.toString().let { titleText: String? ->
            title.text = titleText
        }
    }
}
