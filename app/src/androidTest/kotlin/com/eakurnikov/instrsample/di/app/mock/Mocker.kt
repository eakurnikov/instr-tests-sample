package com.eakurnikov.instrsample.di.app.mock

import com.eakurnikov.instrsample.di.component.RootComponent
import com.eakurnikov.instrsample.di.dependency
import com.eakurnikov.instrsample.env.TestEnv

class Mocker {

    inline fun <reified T> mockComponent(mock: T, component: RootComponent.() -> T) {
        requireNotNull(mock)
        mockableComponent(component)?.mock(mock)
    }

    inline fun <reified T> unmockComponent(component: RootComponent.() -> T) {
        mockableComponent(component)?.unmock()
    }

    inline fun <reified T> mockableComponent(
        component: RootComponent.() -> T
    ): MockableComponent<T>? {
        val mockableComponent: MockableComponent<T>? =
            TestEnv.targetContext.dependency(component) as? MockableComponent<T>
        mockableComponent ?: TestEnv.logger.e("Component ${T::class.java.name} is not mockable")
        return mockableComponent
    }
}
