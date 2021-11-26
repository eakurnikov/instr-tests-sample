package com.eakurnikov.instrsample.actions.kview

import com.eakurnikov.instrsample.actions.view.GetTextAction
import io.github.kakaocup.kakao.text.TextViewAssertions

fun TextViewAssertions.getText(): String {
    var text = ""
    view.perform(
        GetTextAction { text = it }
    )
    return text
}
