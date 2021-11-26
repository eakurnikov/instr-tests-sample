package com.eakurnikov.instrsample.actions

import com.kaspersky.kaspresso.testcases.core.testcontext.BaseTestContext

fun <T> BaseTestContext.perform(act: () -> T, expect: () -> Unit): T = flakySafely {
    val result: T = act.invoke()
    expect.invoke()
    return@flakySafely result
}
