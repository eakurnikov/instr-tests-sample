package com.eakurnikov.instrsample.common

import android.os.Handler
import android.os.Looper
import java.util.concurrent.Executor
import java.util.concurrent.Executors

class AppExecutors {
    val diskIo: Executor = Executors.newSingleThreadExecutor()
    val networkIo: Executor = Executors.newFixedThreadPool(3)

    val mainThread: Executor = object : Executor {
        private val mainThreadHandler = Handler(Looper.getMainLooper())

        override fun execute(command: Runnable) {
            mainThreadHandler.post(command)
        }
    }
}
