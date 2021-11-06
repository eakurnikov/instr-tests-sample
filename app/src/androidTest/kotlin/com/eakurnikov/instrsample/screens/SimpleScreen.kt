package com.eakurnikov.instrsample.screens

import com.eakurnikov.instrsample.R
import com.eakurnikov.instrsample.SimpleActivity
import com.kaspersky.kaspresso.screens.KScreen
import io.github.kakaocup.kakao.edit.KEditText
import io.github.kakaocup.kakao.text.KButton
import io.github.kakaocup.kakao.text.KTextView

object SimpleScreen : KScreen<SimpleScreen>() {
    override val viewClass: Class<*> = SimpleActivity::class.java
    override val layoutId: Int = R.layout.activity_simple

    val title = KTextView {
        withId(R.id.tv_simple_title)
    }

    val editText = KEditText {
        withId(R.id.et_simple)
    }

    val btnDelete = KButton {
        withId(R.id.btn_simple_delete)
    }

    val btnNext = KButton {
        isDescendantOfA {
            withId(R.id.simple_root)
        }
        withSibling {
            withText(R.string.clear)
        }
        withBackgroundColor(R.color.colorPrimary)
    }
}
