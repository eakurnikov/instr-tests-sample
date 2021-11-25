package com.eakurnikov.instrsample.di.test.component

import com.eakurnikov.instrsample.env.kaspresso.KaspressoBuildStage
import com.eakurnikov.instrsample.env.log.batch.LogBatcher
import com.kaspersky.kaspresso.device.logcat.dumper.LogcatDumper
import com.kaspersky.kaspresso.files.resources.ResourceFilesProvider
import com.kaspersky.kaspresso.kaspresso.Kaspresso
import com.kaspersky.kaspresso.logger.UiTestLogger

class LogComponent(
    val instrumentationLogger: UiTestLogger,
    val testLogger: UiTestLogger,
    val logcatDumperFactory: (ResourceFilesProvider) -> LogcatDumper,
    val instrumentationLogBatcher: LogBatcher
) : TestComponent {

    override fun inject(builder: Kaspresso.Builder, buildStage: KaspressoBuildStage) {
        when (buildStage) {
            KaspressoBuildStage.PRE -> {
                builder.libLogger = testLogger
                builder.testLogger = testLogger
            }
            KaspressoBuildStage.POST -> {
                builder.logcatDumper = logcatDumperFactory(builder.resourceFilesProvider)
            }
        }
    }
}
