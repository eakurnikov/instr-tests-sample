package com.eakurnikov.instrsample.env.log

import com.eakurnikov.instrsample.env.log.adapter.LoggerAdapter
import com.kaspersky.kaspresso.logger.UiTestLogger

class CustomUiTestLogger(
    private val adapter: LoggerAdapter
) : UiTestLogger {

    override fun i(text: String): Unit = adapter.i(null, text)
    override fun d(text: String): Unit = adapter.d(null, text)
    override fun w(text: String): Unit = adapter.w(null, text)
    override fun e(text: String): Unit = adapter.e(null, text)

    override fun i(tag: String, text: String): Unit = adapter.i(tag, text)
    override fun d(tag: String, text: String): Unit = adapter.d(tag, text)
    override fun w(tag: String, text: String): Unit = adapter.w(tag, text)
    override fun e(tag: String, text: String): Unit = adapter.e(tag, text)

    override fun header(text: String) {
        line()
        i(text)
    }

    override fun footer(text: String) {
        i(text)
        line()
    }

    override fun section(text: String) {
        i("---------------------------------------------------------------------------")
        i(text)
        i("---------------------------------------------------------------------------")
    }

    override fun line() {
        i("___________________________________________________________________________")
    }
}
