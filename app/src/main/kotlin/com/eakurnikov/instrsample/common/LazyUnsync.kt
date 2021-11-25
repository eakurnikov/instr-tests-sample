package com.eakurnikov.instrsample.common

fun <T> lazyUnsync(initializer: () -> T): Lazy<T> = lazy(LazyThreadSafetyMode.NONE, initializer)
