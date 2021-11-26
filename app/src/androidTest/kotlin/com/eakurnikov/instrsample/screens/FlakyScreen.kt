package com.eakurnikov.instrsample.screens

import com.eakurnikov.instrsample.R
import com.eakurnikov.instrsample.view.FlakyActivity
import com.kaspersky.kaspresso.screens.KScreen
import io.github.kakaocup.kakao.common.views.KView
import io.github.kakaocup.kakao.scroll.KScrollView
import io.github.kakaocup.kakao.text.KButton

object FlakyScreen : KScreen<FlakyScreen>() {
    override val viewClass: Class<*> = FlakyActivity::class.java
    override val layoutId: Int = R.layout.activity_flaky

    val scrollView = KScrollView {
        withId(R.id.scroll_view_flaky)
    }

    val container = KView {
        withId(R.id.container_flaky)
    }

    val btn1 = KButton {
        withId(R.id.btn_flaky_1)
    }

    val btn3 = KButton {
        withId(R.id.btn_flaky_4)
    }

    val flakyBtn = KButton {
        withId(R.id.btn_flaky_6)
    }

    val flakyTextView = KButton {
        withId(R.id.tv_flaky_2)
    }
}
