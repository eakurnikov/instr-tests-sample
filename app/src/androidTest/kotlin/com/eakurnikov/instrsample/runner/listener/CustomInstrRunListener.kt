package com.eakurnikov.instrsample.runner.listener

import androidx.test.internal.runner.listener.InstrumentationRunListener
import com.eakurnikov.instrsample.common.lazyUnsync
import com.eakurnikov.instrsample.env.TestEnv
import com.eakurnikov.instrsample.env.report.TestInstrumentationReporter
import org.junit.runner.Description
import org.junit.runner.Result
import org.junit.runner.notification.Failure

/**
 * When being called delegates the call to TestInstrumentationReporter.
 */
class CustomInstrRunListener : InstrumentationRunListener() {

    private val reporter: TestInstrumentationReporter by lazyUnsync {
        TestEnv.dependency(instrumentation) { reportComponent.testInstrumentationReporter }
    }

    override fun testRunStarted(description: Description?) {
        reporter.onTestRunStarted(description)
    }

    override fun testStarted(description: Description) {
        reporter.onTestStarted(description)
    }

    override fun testFinished(description: Description) {
        reporter.onTestFinished(description)
    }

    override fun testFailure(failure: Failure) {
        reporter.onTestFailed(failure)
    }

    override fun testAssumptionFailure(failure: Failure) {
        reporter.onTestAssumptionFailed(failure)
    }

    override fun testIgnored(description: Description) {
        reporter.onTestIgnored(description)
    }

    override fun testRunFinished(result: Result?) {
        reporter.onTestRunFinished(result)
    }
}
