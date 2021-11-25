package com.eakurnikov.instrsample.env.log.dump

import com.eakurnikov.instrsample.env.log.batch.LogBatcher
import com.kaspersky.kaspresso.device.logcat.dumper.LogcatDumper
import com.kaspersky.kaspresso.files.resources.ResourceFilesProvider
import java.io.File

class TestLogcatDumper(
    private val logBatcher: LogBatcher,
    private val resourceFilesProvider: ResourceFilesProvider
) : LogcatDumper {

    override fun charge() = Unit

    override fun dump(tag: String): Unit = doDump(null)

    override fun dumpAndApply(tag: String, block: File.() -> Unit): Unit = doDump(block)

    private fun doDump(block: (File.() -> Unit)?) {
        val file: File = resourceFilesProvider.provideLogcatFile("TestLogcat")
        logBatcher.push(file)
        block?.invoke(file)
    }
}
