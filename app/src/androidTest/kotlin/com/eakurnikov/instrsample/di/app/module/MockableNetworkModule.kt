package com.eakurnikov.instrsample.di.app.module

import com.eakurnikov.instrsample.di.app.component.MockableNetworkComponent
import com.eakurnikov.instrsample.di.app.mock.MockableDependency
import com.eakurnikov.instrsample.di.component.NetworkComponent
import com.eakurnikov.instrsample.di.module.NetworkModule
import com.eakurnikov.instrsample.di.module.NetworkModuleImpl

class MockableNetworkModule : NetworkModule {

    override fun createComponent(): NetworkComponent {
        val realNetworkModule: NetworkModule = NetworkModuleImpl()
        val realNetworkComponent: NetworkComponent = realNetworkModule.createComponent()

        return MockableNetworkComponent(
            mockablePostsApi = MockableDependency(
                real = realNetworkComponent.postsApi
            )
        )
    }
}
