package com.eakurnikov.instrsample.runner.listener

import android.app.Application
import androidx.test.runner.lifecycle.ApplicationLifecycleCallback
import androidx.test.runner.lifecycle.ApplicationStage
import com.eakurnikov.instrsample.env.TestEnv
import com.eakurnikov.instrsample.env.report.TestInstrumentationReporter

/**
 * Custom app lifecycle listener.
 */
class CustomAppListener : ApplicationLifecycleCallback {

    private val reporter: TestInstrumentationReporter by TestEnv.lazyDependency {
        reportComponent.testInstrumentationReporter
    }

    override fun onApplicationLifecycleChanged(app: Application?, stage: ApplicationStage?) {
        when (stage) {
            null -> Unit
            ApplicationStage.PRE_ON_CREATE -> reporter.onAppCreateStarted(app)
            ApplicationStage.CREATED -> reporter.onAppCreatedFinished(app)
        }
    }
}
