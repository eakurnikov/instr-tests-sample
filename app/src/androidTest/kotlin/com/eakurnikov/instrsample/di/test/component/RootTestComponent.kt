package com.eakurnikov.instrsample.di.test.component

import com.eakurnikov.instrsample.env.kaspresso.KaspressoBuildStage
import com.kaspersky.kaspresso.kaspresso.Kaspresso

class RootTestComponent(
    val logComponent: LogComponent,
    val fileComponent: FileComponent,
    val reportComponent: ReportComponent
) : TestComponent {

    override fun inject(builder: Kaspresso.Builder, buildStage: KaspressoBuildStage) {
        logComponent.inject(builder, buildStage)
        fileComponent.inject(builder, buildStage)
        reportComponent.inject(builder, buildStage)
    }
}
