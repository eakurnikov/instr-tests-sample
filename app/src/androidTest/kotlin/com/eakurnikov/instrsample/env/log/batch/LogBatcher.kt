package com.eakurnikov.instrsample.env.log.batch

import java.io.File
import java.util.logging.FileHandler
import java.util.logging.Formatter
import java.util.logging.Logger

class LogBatcher(
    logger: Logger,
    private val logFormatter: Formatter
) {
    private var bufferedHandler = BufferedHandler()

    init {
        logger.addHandler(bufferedHandler)
    }

    fun push(file: File): Unit = bufferedHandler.push(
        FileHandler(file.absolutePath).apply {
            formatter = logFormatter
        }
    )
}
