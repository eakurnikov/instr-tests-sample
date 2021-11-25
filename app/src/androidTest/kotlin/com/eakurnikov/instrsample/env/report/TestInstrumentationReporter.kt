package com.eakurnikov.instrsample.env.report

import android.app.Application
import android.app.Instrumentation
import android.os.Bundle
import android.util.Log
import com.eakurnikov.instrsample.env.file.ReportFileProvider
import com.eakurnikov.instrsample.env.log.batch.LogBatcher
import com.kaspersky.kaspresso.logger.UiTestLogger
import org.junit.runner.Description
import org.junit.runner.Result
import org.junit.runner.notification.Failure
import java.io.File

/**
 * Writes instrumentation logs to /sdcard/report/InstrumentationReport.txt
 */
class TestInstrumentationReporter(
    private val logger: UiTestLogger,
    private val batcher: LogBatcher,
    private val fileProvider: ReportFileProvider
) {
    fun onNewApplication(app: Application) {
        logger.d("New application ${app::class.java.name} instance created")
    }

    fun onAppCreateStarted(app: Application?) {
        app ?: return
        logger.e("Application ${app::class.java.name} onCreate started")
    }

    fun onAppCreatedFinished(app: Application?) {
        app ?: return
        logger.e("Application ${app::class.java.name} onCreate finished")
    }

    fun onInstrumentationCreated(instr: Instrumentation, arguments: Bundle) {
        logger.i("Test instrumentation ${instr::class.java.name} created with args: $arguments")
    }

    fun onUncaughtException(exception: Throwable) {
        logger.e("Uncaught exception", Log.getStackTraceString(exception))
    }

    fun onInstrumentationException(obj: Any?, exception: Throwable?) {
        logger.e("Exception", "${obj.toString()} ${Log.getStackTraceString(exception)}")
    }

    fun onTestRunStarted(description: Description?) {
        logger.section("Test run started: ${description.toString()}")
    }

    fun onTestStarted(description: Description) {
        logger.section("Test started: $description")
    }

    fun onTestFinished(description: Description) {
        logger.i("Test finished: $description")
    }

    fun onTestFailed(failure: Failure) {
        logger.i("Test failed: $failure")
    }

    fun onTestAssumptionFailed(failure: Failure) {
        logger.i("Test assumption failed: $failure")
    }

    fun onTestIgnored(description: Description) {
        logger.i("Test ignored: $description")
    }

    fun onTestRunFinished(result: Result?) {
        logger.line()
        result?.apply {
            logger.i("Test run ${if (wasSuccessful()) "SUCCEEDED" else "FAILED"}")
            logger.i("Test run time: ${runTime / 1000 / 60} min ${runTime / 1000 % 60} sec")
            logger.i("Tests run count: $runCount")
            logger.i("Tests ignored count: $ignoreCount")
            logger.i("Test failures count: $failureCount")
            logger.i("Test assumption failures: $assumptionFailureCount")
        } ?: logger.i("Test run finished: failure")
        logger.line()
    }

    fun onInstrumentationFinish(instr: Instrumentation, resultCode: Int, results: Bundle?) {
        logger.i("Test instrumentation ${instr::class.java.name} finished with code: $resultCode")
        logger.e(results.toString())
        report()
    }

    fun report() {
        val reportFile: File = fileProvider.provideReportFile("InstrumentationReport")
        batcher.push(reportFile)
    }
}
