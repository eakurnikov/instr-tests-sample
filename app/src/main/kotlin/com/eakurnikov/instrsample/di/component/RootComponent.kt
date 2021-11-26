package com.eakurnikov.instrsample.di.component

import com.eakurnikov.instrsample.common.AppExecutors

class RootComponent(
    networkComponentFactory: () -> NetworkComponent,
    appExecutorsFactory: () -> AppExecutors
) {
    val networkComponent: NetworkComponent by lazy(networkComponentFactory)
    val appExecutors: AppExecutors by lazy(appExecutorsFactory)
}
