package com.eakurnikov.instrsample.runner.listener

import com.eakurnikov.instrsample.env.TestEnv
import com.kaspersky.kaspresso.logger.UiTestLogger
import org.junit.runner.Description
import org.junit.runner.notification.RunListener

class CustomRunListener : RunListener() {

    private val instrLogger: UiTestLogger by TestEnv.lazyDependency {
        logComponent.instrumentationLogger
    }

    override fun testRunStarted(description: Description?) {
        instrLogger.i("CustomInstrRunListener testRunStarted: $description")
    }
}
