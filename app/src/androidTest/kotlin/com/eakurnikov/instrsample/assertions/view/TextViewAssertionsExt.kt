package com.eakurnikov.instrsample.assertions.view

import com.eakurnikov.instrsample.actions.kview.getContentDescription
import com.eakurnikov.instrsample.actions.kview.getText
import io.github.kakaocup.kakao.common.actions.BaseActions
import io.github.kakaocup.kakao.text.TextViewAssertions

fun TextViewAssertions.hasTextFrom(otherTextView: TextViewAssertions) {
    val text: String = otherTextView.getText()
    hasText(text)
}

fun TextViewAssertions.containsTextFrom(otherTextView: TextViewAssertions) {
    val text: String = otherTextView.getText()
    containsText(text)
}

fun TextViewAssertions.hasContentDescriptionFrom(otherView: BaseActions) {
    val descr: String = otherView.getContentDescription()
    hasContentDescription(descr)
}
