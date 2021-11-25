package com.eakurnikov.instrsample.di.test.component

import com.eakurnikov.instrsample.env.report.TestInstrumentationReporter

class ReportComponent(
    val testInstrumentationReporter: TestInstrumentationReporter
) : TestComponent
