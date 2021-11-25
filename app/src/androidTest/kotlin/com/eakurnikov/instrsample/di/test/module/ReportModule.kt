package com.eakurnikov.instrsample.di.test.module

import com.eakurnikov.instrsample.di.test.component.FileComponent
import com.eakurnikov.instrsample.di.test.component.LogComponent
import com.eakurnikov.instrsample.di.test.component.ReportComponent
import com.eakurnikov.instrsample.env.report.TestInstrumentationReporter

class ReportModule {

    fun createComponent(
        logComponent: LogComponent,
        fileComponent: FileComponent
    ): ReportComponent {
        return ReportComponent(
            testInstrumentationReporter = TestInstrumentationReporter(
                logger = logComponent.instrumentationLogger,
                batcher = logComponent.instrumentationLogBatcher,
                fileProvider = fileComponent.resourceFilesProvider
            )
        )
    }
}
