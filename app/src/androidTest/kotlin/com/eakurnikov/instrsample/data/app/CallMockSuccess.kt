package com.eakurnikov.instrsample.data.app

import okhttp3.Request
import okio.Timeout
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CallMockSuccess<T>(
    private val response: T
) : Call<T> {

    override fun clone(): Call<T> = CallMockSuccess(response)

    override fun execute(): Response<T> = Response.success(response)

    override fun enqueue(callback: Callback<T>) {
        callback.onResponse(this, Response.success(response))
    }

    override fun isExecuted(): Boolean = false

    override fun cancel() = Unit

    override fun isCanceled(): Boolean = false

    override fun request(): Request? = null

    override fun timeout(): Timeout? = null
}
