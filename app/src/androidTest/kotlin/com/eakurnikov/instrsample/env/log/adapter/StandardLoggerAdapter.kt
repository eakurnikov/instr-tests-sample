package com.eakurnikov.instrsample.env.log.adapter

import java.util.logging.Level
import java.util.logging.Logger

class StandardLoggerAdapter(
    private val logger: Logger
): LoggerAdapter, LogTags.Source by LogTags.Source.Impl() {

    init {
        logger.level = Level.ALL
    }

    override fun i(tag: String?, text: String): Unit = logger.log(
        Level.INFO,
        text,
        packTags("I", tag)
    )

    override fun d(tag: String?, text: String): Unit = logger.log(
        Level.FINE,
        text,
        packTags("D", tag)
    )

    override fun w(tag: String?, text: String): Unit = logger.log(
        Level.WARNING,
        text,
        packTags("W", tag)
    )

    override fun e(tag: String?, text: String): Unit = logger.log(
        Level.SEVERE,
        text,
        packTags("E", tag)
    )
}
