package com.eakurnikov.instrsample.screens

import com.eakurnikov.instrsample.R
import com.eakurnikov.instrsample.VerySimpleActivity
import com.kaspersky.kaspresso.screens.KScreen
import io.github.kakaocup.kakao.text.KTextView

object VerySimpleScreen : KScreen<VerySimpleScreen>() {
    override val viewClass: Class<*> = VerySimpleActivity::class.java
    override val layoutId: Int = R.layout.activity_simple_very

    val title = KTextView {
        withId(R.id.tv_very_simple_title)
    }
}
