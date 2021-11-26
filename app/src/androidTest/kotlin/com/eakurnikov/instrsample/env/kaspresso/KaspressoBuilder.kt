package com.eakurnikov.instrsample.env.kaspresso

import com.eakurnikov.instrsample.env.TestEnv
import com.kaspersky.components.alluresupport.withAllureSupport
import com.kaspersky.kaspresso.kaspresso.Kaspresso

fun Kaspresso.Builder.Companion.custom(
    customize: Kaspresso.Builder.() -> Unit = {}
): Kaspresso.Builder = withAllureSupport {
    TestEnv.getRootTestComponent().inject(this, KaspressoBuildStage.PRE)
    customize()
}.apply {
    TestEnv.getRootTestComponent().inject(this, KaspressoBuildStage.POST)
}

enum class KaspressoBuildStage {
    PRE,
    POST
}
