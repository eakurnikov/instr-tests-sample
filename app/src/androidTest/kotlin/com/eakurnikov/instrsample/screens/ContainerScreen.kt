package com.eakurnikov.instrsample.screens

import com.eakurnikov.instrsample.R
import com.eakurnikov.instrsample.view.ContainerActivity
import com.kaspersky.kaspresso.screens.KScreen
import io.github.kakaocup.kakao.common.views.KView

object ContainerScreen : KScreen<ContainerScreen>() {
    override val viewClass: Class<*> = ContainerActivity::class.java
    override val layoutId: Int = R.layout.activity_container

    val root = KView {
        withId(R.id.root)
    }
}
