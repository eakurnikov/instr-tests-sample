package com.eakurnikov.instrsample.actions.kview

import androidx.test.espresso.action.GeneralLocation
import com.eakurnikov.instrsample.actions.view.GetContentDescriptionAction
import com.kaspersky.kaspresso.testcases.core.testcontext.TestContext
import io.github.kakaocup.kakao.common.actions.BaseActions

fun BaseActions.getContentDescription(): String {
    var descr = ""
    view.perform(
        GetContentDescriptionAction { descr = it }
    )
    return descr
}

fun BaseActions.clickWith(
    testContext: TestContext<out Any?>,
    location: GeneralLocation = GeneralLocation.VISIBLE_CENTER,
    expect: () -> Unit
) {
    testContext.flakySafely {
        click(location)
        expect.invoke()
    }
}
