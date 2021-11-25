package com.eakurnikov.instrsample.actions.kview

import io.github.kakaocup.kakao.common.actions.SwipeableActions
import com.eakurnikov.instrsample.actions.view.GetCurrentPageItemAction

val SwipeableActions.currentItem: Int
    get() {
        var result = -1
        view.perform(GetCurrentPageItemAction { result = it })
        return result
    }
