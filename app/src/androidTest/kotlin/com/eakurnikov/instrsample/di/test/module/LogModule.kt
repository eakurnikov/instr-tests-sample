package com.eakurnikov.instrsample.di.test.module

import com.eakurnikov.instrsample.di.test.component.LogComponent
import com.eakurnikov.instrsample.env.log.CustomUiTestLogger
import com.eakurnikov.instrsample.env.log.adapter.LoggerAdapter
import com.eakurnikov.instrsample.env.log.adapter.StandardLoggerAdapter
import com.eakurnikov.instrsample.env.log.batch.LogBatcher
import com.eakurnikov.instrsample.env.log.dump.TestLogcatDumper
import com.eakurnikov.instrsample.env.log.formatter.LogFormatter
import com.kaspersky.kaspresso.device.logcat.dumper.LogcatDumper
import com.kaspersky.kaspresso.files.resources.ResourceFilesProvider
import com.kaspersky.kaspresso.kaspresso.Kaspresso
import com.kaspersky.kaspresso.logger.UiTestLogger
import java.util.logging.Formatter
import java.util.logging.Logger

class LogModule {

    fun createComponent(): LogComponent {
        val instrLogDriver: Logger = Logger.getLogger("INSTR_REPORT")
        val testLogDriver: Logger = Logger.getLogger(Kaspresso.DEFAULT_LIB_LOGGER_TAG)

        val instrLoggerAdapter: LoggerAdapter = StandardLoggerAdapter(instrLogDriver)
        val testLoggerAdapter: LoggerAdapter = StandardLoggerAdapter(testLogDriver)

        val instrLogger: UiTestLogger = CustomUiTestLogger(instrLoggerAdapter)
        val uiTestLogger: UiTestLogger = CustomUiTestLogger(testLoggerAdapter)

        val logFormatter: Formatter = LogFormatter()
        val instrLogBatcher = LogBatcher(instrLogDriver, logFormatter)
        val testLogBatcher = LogBatcher(testLogDriver, logFormatter)

        val logcatDumperFactory: (ResourceFilesProvider) -> LogcatDumper = { fileProvider ->
            TestLogcatDumper(testLogBatcher, fileProvider)
        }

        return LogComponent(
            instrumentationLogger = instrLogger,
            testLogger = uiTestLogger,
            logcatDumperFactory = logcatDumperFactory,
            instrumentationLogBatcher = instrLogBatcher
        )
    }
}
