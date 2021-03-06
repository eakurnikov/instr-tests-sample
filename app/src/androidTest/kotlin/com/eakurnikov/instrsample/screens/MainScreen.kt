package com.eakurnikov.instrsample.screens

import com.eakurnikov.instrsample.view.MainActivity
import com.eakurnikov.instrsample.R
import com.kaspersky.kaspresso.screens.KScreen
import io.github.kakaocup.kakao.common.views.KView
import io.github.kakaocup.kakao.text.KButton
import io.github.kakaocup.kakao.text.KTextView

object MainScreen : KScreen<MainScreen>() {
    override val viewClass: Class<*> = MainActivity::class.java
    override val layoutId: Int = R.layout.activity_main

    val customBtn = KButton {
        withId(R.id.btn_custom)
    }

    val root = KView {
        withId(R.id.root)
    }

    val title = KTextView {
        withId(R.id.tv_main_title)
    }

    val toSimpleScreenBtn = KButton {
        withId(R.id.btn_simple_screen)
    }

    val toFlakyScreenBtn = KButton {
        withId(R.id.btn_flaky_screen)
    }

    val toPostsScreenBtn = KButton {
        withId(R.id.btn_posts_screen)
    }
}
