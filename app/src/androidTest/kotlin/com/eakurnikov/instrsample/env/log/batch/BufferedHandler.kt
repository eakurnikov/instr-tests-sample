package com.eakurnikov.instrsample.env.log.batch

import java.util.logging.*

class BufferedHandler(
    size: Int = 1_000,
    logLevel: Level = Level.ALL,
    filter: Filter? = null,
    formatter: Formatter = SimpleFormatter()
) : Handler() {

    private val buffer: MutableList<LogRecord> = ArrayList(size)

    init {
        this.level = logLevel
        this.filter = filter
        this.formatter = formatter
    }

    override fun publish(record: LogRecord) {
        if (isLoggable(record)) buffer += record
    }

    fun push(target: Handler): Unit = with(buffer) {
        forEach(target::publish)
        clear()
    }

    override fun flush() = Unit

    override fun close() {
        level = Level.OFF
    }
}
