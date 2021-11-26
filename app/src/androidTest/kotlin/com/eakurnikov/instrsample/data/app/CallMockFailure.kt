package com.eakurnikov.instrsample.data.app

import okhttp3.Request
import okhttp3.ResponseBody
import okio.Timeout
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CallMockFailure<T>(
    private val body: ResponseBody
): Call<T> {

    override fun clone(): Call<T> = CallMockFailure(body)

    override fun execute(): Response<T> = Response.error(EmptyResponseBody.code, body)

    override fun enqueue(callback: Callback<T>) {
        callback.onResponse(this, Response.error(EmptyResponseBody.code, body))
    }

    override fun isExecuted(): Boolean = false

    override fun cancel() = Unit

    override fun isCanceled(): Boolean = false

    override fun request(): Request? = null

    override fun timeout(): Timeout? = null
}
