package com.eakurnikov.instrsample.di.test

import android.app.Instrumentation
import android.app.UiAutomation
import android.content.Context
import android.content.res.AssetManager
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.uiautomator.UiDevice
import com.eakurnikov.instrsample.common.lazyUnsync
import com.eakurnikov.instrsample.di.test.component.RootTestComponent
import com.eakurnikov.instrsample.runner.CustomInstrRunner
import com.kaspersky.kaspresso.logger.UiTestLogger

/**
 * Facade class for test dependencies graph.
 */
open class TestDependenciesProvider {

    val instrumentation: Instrumentation
        get() = InstrumentationRegistry.getInstrumentation()

    val uiAutomation: UiAutomation
        get() = instrumentation.uiAutomation

    val uiDevice: UiDevice
        get() = UiDevice.getInstance(instrumentation)

    val targetContext: Context
        get() = instrumentation.targetContext

    val context: Context
        get() = instrumentation.context

    val assets: AssetManager
        get() = targetContext.assets

    val logger: UiTestLogger by lazyDependency { logComponent.testLogger }

    inline fun <T> dependency(dependency: RootTestComponent.() -> T): T =
        dependency(instrumentation, dependency)

    inline fun <T> dependency(
        instrumentation: Instrumentation,
        dependency: RootTestComponent.() -> T
    ): T = dependency(getRootTestComponent(instrumentation))

    inline fun <T> lazyDependency(crossinline dependency: RootTestComponent.() -> T): Lazy<T> =
        lazyDependency(instrumentation, dependency)

    inline fun <T> lazyDependency(
        instrumentation: Instrumentation,
        crossinline dependency: RootTestComponent.() -> T
    ): Lazy<T> = lazyUnsync { dependency(instrumentation, dependency) }

    fun getRootTestComponent(
        instrumentation: Instrumentation = this.instrumentation
    ): RootTestComponent =
        (instrumentation as? CustomInstrRunner)?.rootTestComponent
            ?: throw IllegalStateException("Unknown instrumentation runner")
}
