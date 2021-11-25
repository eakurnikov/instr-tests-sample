package com.eakurnikov.instrsample.common

sealed class Resource<T> {
    class Success<T>(val data: T) : Resource<T>()
    class Loading<T>(val data: T? = null) : Resource<T>()
    class Error<T>(val message: String? = null, val data: T? = null) : Resource<T>()
}
