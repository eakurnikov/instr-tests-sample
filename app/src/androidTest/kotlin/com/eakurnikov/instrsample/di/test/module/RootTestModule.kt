package com.eakurnikov.instrsample.di.test.module

import android.app.Instrumentation
import com.eakurnikov.instrsample.di.test.component.FileComponent
import com.eakurnikov.instrsample.di.test.component.LogComponent
import com.eakurnikov.instrsample.di.test.component.ReportComponent
import com.eakurnikov.instrsample.di.test.component.RootTestComponent

class RootTestModule {
    private val logModule = LogModule()
    private val fileModule = FileModule()
    private val reportModule = ReportModule()

    fun createComponent(instrumentation: Instrumentation): RootTestComponent {
        val logComponent: LogComponent = logModule.createComponent()
        val fileComponent: FileComponent = fileModule.createComponent(instrumentation)
        val reportComponent: ReportComponent =
            reportModule.createComponent(logComponent, fileComponent)

        return RootTestComponent(
            logComponent = logComponent,
            fileComponent = fileComponent,
            reportComponent = reportComponent,
        )
    }
}
