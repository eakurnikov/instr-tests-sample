package com.eakurnikov.instrsample.di.app.mock

interface MockableComponent<T> {
    fun mock(mock: T)
    fun unmock()
}
