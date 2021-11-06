package com.eakurnikov.instrsample.app

import android.app.Application
import android.util.Log

class DebugApp : Application() {

    override fun onCreate() {
        super.onCreate()
        Log.i("DebugApp", "onCreate=${this}")
    }
}
