package com.eakurnikov.instrsample.runner.config

import com.eakurnikov.instrsample.env.report.TestInstrumentationReporter

class TestExceptionHandler(
    private val reporter: TestInstrumentationReporter
) : Thread.UncaughtExceptionHandler {

    private val defaultHandler: Thread.UncaughtExceptionHandler? =
        Thread.getDefaultUncaughtExceptionHandler()

    override fun uncaughtException(thread: Thread, exception: Throwable) {
        reporter.onUncaughtException(exception)
        defaultHandler?.uncaughtException(thread, exception)
    }
}
