package com.eakurnikov.instrsample.di.app.mock

class MockableDependency<Dependency>(
    private val real: Dependency
) {
    var value: Dependency = real

    fun mock(mock: Dependency) {
        value = mock
    }

    fun unmock() {
        value = real
    }
}
