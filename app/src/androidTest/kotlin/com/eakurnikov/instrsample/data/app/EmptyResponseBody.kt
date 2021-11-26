package com.eakurnikov.instrsample.data.app

import okhttp3.MediaType
import okhttp3.ResponseBody
import okio.BufferedSource
import java.lang.IllegalStateException

object EmptyResponseBody : ResponseBody() {

    const val code: Int = 500

    override fun contentType(): MediaType? = null

    override fun contentLength(): Long = 0

    override fun source(): BufferedSource {
        throw IllegalStateException("Sorry")
    }
}
