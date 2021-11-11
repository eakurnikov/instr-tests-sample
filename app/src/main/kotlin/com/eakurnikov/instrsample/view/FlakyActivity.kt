package com.eakurnikov.instrsample.view

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.eakurnikov.instrsample.R
import java.util.concurrent.TimeUnit

class FlakyActivity : AppCompatActivity() {

    companion object {
        private val VISIBILITY_DELAY = TimeUnit.SECONDS.toMillis(1)
        private val TEXT_DELAY = TimeUnit.SECONDS.toMillis(3)

        fun start(context: Context): Unit =
            context.startActivity(Intent(context, FlakyActivity::class.java))
    }

    lateinit var flakyBtn: Button
    lateinit var flakyTv: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_flaky)
        findViewsById()
        initViews()
    }

    private fun findViewsById() {
        flakyBtn = findViewById(R.id.btn_flaky_6)
        flakyTv = findViewById(R.id.tv_flaky_2)
    }

    private fun initViews() {
        flakyBtn.setOnClickListener {
            SimpleActivity.start(this)
        }

        Handler(mainLooper)
            .apply {
                postDelayed(
                    { flakyTv.visibility = View.VISIBLE },
                    VISIBILITY_DELAY
                )
            }
            .apply {
                postDelayed(
                    { flakyTv.text = getString(R.string.flaky_textview_text_end) },
                    VISIBILITY_DELAY + TEXT_DELAY
                )
            }
            .apply {
                postDelayed(
                    { flakyBtn.visibility = View.VISIBLE },
                    VISIBILITY_DELAY * 2 + TEXT_DELAY
                )
            }
            .apply {
                postDelayed(
                    { flakyBtn.text = getString(R.string.flaky_btn_text_end) },
                    VISIBILITY_DELAY * 2 + TEXT_DELAY * 2
                )
            }
    }
}
