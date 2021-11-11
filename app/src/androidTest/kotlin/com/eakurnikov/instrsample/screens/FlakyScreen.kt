package com.eakurnikov.instrsample.screens

import com.eakurnikov.instrsample.R
import io.github.kakaocup.kakao.screen.Screen
import io.github.kakaocup.kakao.scroll.KScrollView
import io.github.kakaocup.kakao.text.KButton

object FlakyScreen : Screen<FlakyScreen>() {

    val scrollView = KScrollView { withId(R.id.scroll_view_flaky) }

    val btn1 = KButton { withId(R.id.btn_flaky_1) }

    val btn3 = KButton { withId(R.id.btn_flaky_4) }

    val flakyBtn = KButton { withId(R.id.btn_flaky_6) }

    val flakyTextView = KButton { withId(R.id.tv_flaky_2) }
}