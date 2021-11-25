package com.eakurnikov.instrsample.di.test.component

import com.eakurnikov.instrsample.env.kaspresso.KaspressoBuildStage
import com.kaspersky.kaspresso.kaspresso.Kaspresso

interface TestComponent {
    fun inject(builder: Kaspresso.Builder, buildStage: KaspressoBuildStage) = Unit
}
