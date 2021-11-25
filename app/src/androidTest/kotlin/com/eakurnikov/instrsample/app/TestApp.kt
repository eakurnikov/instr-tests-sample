package com.eakurnikov.instrsample.app

import com.eakurnikov.instrsample.di.app.module.MockableNetworkModule
import com.eakurnikov.instrsample.di.component.RootComponent
import com.eakurnikov.instrsample.di.module.RootModule

/**
 * Special app impl for tests.
 */
class TestApp : DebugApp() {

    override fun initRootComponent(): RootComponent =
        RootModule(networkModule = MockableNetworkModule()).createComponent()
}
