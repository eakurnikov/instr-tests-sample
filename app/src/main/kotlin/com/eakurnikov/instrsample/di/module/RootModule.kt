package com.eakurnikov.instrsample.di.module

import com.eakurnikov.instrsample.common.AppExecutors
import com.eakurnikov.instrsample.di.component.RootComponent

class RootModule(
    private val networkModule: NetworkModule = NetworkModuleImpl()
) {
    fun createComponent() = RootComponent(
        networkComponentFactory = networkModule::createComponent,
        appExecutorsFactory = ::AppExecutors
    )
}
