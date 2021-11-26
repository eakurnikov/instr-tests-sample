package com.eakurnikov.instrsample.di

import android.content.Context
import android.content.ContextWrapper
import com.eakurnikov.instrsample.di.component.RootComponent

class DependenciesProvider(
    context: Context,
    rootComponentFactory: () -> RootComponent
) : ContextWrapper(context) {

    val rootComponent: RootComponent by lazy(rootComponentFactory)
}

inline fun <T> Context.dependency(dependency: RootComponent.() -> T): T =
    dependency(rootComponent)

inline fun <T> Context.lazyDependency(crossinline dependency: RootComponent.() -> T): Lazy<T> =
    lazy { dependency(dependency) }

val Context.rootComponent: RootComponent
    get() {
        var context: Context? = applicationContext
        while (context is ContextWrapper) {
            context = context.baseContext
            if (context is DependenciesProvider) {
                return context.rootComponent
            }
        }
        throw IllegalStateException("Could not resolve dependency provider")
    }
