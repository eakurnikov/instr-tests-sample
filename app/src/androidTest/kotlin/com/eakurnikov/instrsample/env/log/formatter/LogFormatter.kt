package com.eakurnikov.instrsample.env.log.formatter

import com.eakurnikov.instrsample.env.log.adapter.LogTags
import java.text.SimpleDateFormat
import java.util.*
import java.util.logging.Formatter
import java.util.logging.LogRecord

class LogFormatter : Formatter(), LogTags.Receiver by LogTags.Receiver.Impl() {
    private val dateFormat = SimpleDateFormat("MM-dd HH:mm:ss.SSS", Locale.getDefault())

    override fun format(record: LogRecord): String {
        val timeStamp: String = dateFormat.format(record.millis)
        var (levelTag: String, textTag: String?) = record.parameters.extractTags()
        textTag = textTag?.let { "$it " } ?: ""
        return with(record) { "$timeStamp $levelTag/$loggerName: $textTag$message\n" }
    }
}
