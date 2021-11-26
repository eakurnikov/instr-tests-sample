package com.eakurnikov.instrsample.app

import android.app.Application
import android.content.Context
import android.util.Log
import com.eakurnikov.instrsample.di.DependenciesProvider
import com.eakurnikov.instrsample.di.component.RootComponent
import com.eakurnikov.instrsample.di.module.RootModule

open class ProdApp : Application() {

    override fun attachBaseContext(base: Context) {
        val contextWrapper = DependenciesProvider(base, ::initRootComponent)
        super.attachBaseContext(contextWrapper)
    }

    open fun initRootComponent(): RootComponent {
        return RootModule().createComponent()
    }
}
