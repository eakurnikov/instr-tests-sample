package com.eakurnikov.instrsample.di.module

import android.util.Log
import com.eakurnikov.instrsample.data.NetworkConstants
import com.eakurnikov.instrsample.data.api.PostsApi
import com.eakurnikov.instrsample.di.component.NetworkComponent
import com.eakurnikov.instrsample.di.component.NetworkComponentImpl
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

interface NetworkModule {
    fun createComponent(): NetworkComponent
}

class NetworkModuleImpl : NetworkModule {

    override fun createComponent(): NetworkComponent {
        val interceptor: HttpLoggingInterceptor = createHttpLoggingInterceptor()
        val okHttpClient: OkHttpClient = createOkHttpClient(interceptor)
        val retrofit: Retrofit = createRetrofit(okHttpClient)
        val postsApi: PostsApi = createPostsApi(retrofit)
        return NetworkComponentImpl(postsApi)
    }

    private fun createHttpLoggingInterceptor(): HttpLoggingInterceptor =
        HttpLoggingInterceptor { message: String ->
            Log.i(NetworkConstants.NETWORK_LOG_TAG, message)
        }.apply {
            level = HttpLoggingInterceptor.Level.BODY
        }

    private fun createOkHttpClient(loggingInterceptor: HttpLoggingInterceptor): OkHttpClient =
        OkHttpClient.Builder()
            .readTimeout(NetworkConstants.READ_TIMEOUT_SEC, TimeUnit.SECONDS)
            .connectTimeout(NetworkConstants.CONNECT_TIMEOUT_SEC, TimeUnit.SECONDS)
            .addInterceptor(loggingInterceptor)
            .build()

    private fun createRetrofit(okHttpClient: OkHttpClient): Retrofit =
        Retrofit.Builder()
            .baseUrl(NetworkConstants.BASE_URL)
            .addConverterFactory(
                GsonConverterFactory.create()
            )
            .client(okHttpClient)
            .build()

    private fun createPostsApi(retrofit: Retrofit): PostsApi =
        retrofit.create(PostsApi::class.java)
}
