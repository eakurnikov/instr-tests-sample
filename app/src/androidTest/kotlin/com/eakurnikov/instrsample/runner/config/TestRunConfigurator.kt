package com.eakurnikov.instrsample.runner.config

import android.os.Bundle
import android.os.StrictMode
import com.eakurnikov.instrsample.env.report.TestInstrumentationReporter
import com.eakurnikov.instrsample.runner.listener.CustomAppListener
import com.eakurnikov.instrsample.runner.listener.CustomInstrRunListener

class TestRunConfigurator {

    fun onInstrumentationCreate(args: Bundle) {
        configureRunArgs(args)
    }

    fun onNewApplication(reporter: TestInstrumentationReporter) {
        configureUncaughtExceptionHandling(reporter)
        configureStrictMode()
    }

    /**
     * Set uncaught exception handler here.
     **/
    private fun configureUncaughtExceptionHandling(reporter: TestInstrumentationReporter) {
        Thread.setDefaultUncaughtExceptionHandler(
            TestExceptionHandler(reporter)
        )
    }

    /**
     * Set custom thread policy here.
     **/
    private fun configureStrictMode() {
        StrictMode.setThreadPolicy(
            StrictMode.ThreadPolicy.Builder().permitAll().build()
        )
    }

    /**
     * Add run listeners to instrumentation arguments bundle.
     **/
    private fun configureRunArgs(args: Bundle) {
        args.putArgs(
            "listener",
            CustomInstrRunListener::class.java.name
        )
        args.putArgs(
            "appListener",
            CustomAppListener::class.java.name
        )
    }

    /**
     * Run listeners names are stored in the string line, comma-separated.
     * So we add our listeners to this line's tail.
     **/
    private fun Bundle.putArgs(key: String, vararg values: CharSequence) {
        val valuesArg: String = listOfNotNull<CharSequence>(
            getCharSequence(key),
            *values
        ).joinToString(separator = ",")
        putCharSequence(key, valuesArg)
    }
}
