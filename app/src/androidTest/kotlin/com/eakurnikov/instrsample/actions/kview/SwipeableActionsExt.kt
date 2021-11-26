package com.eakurnikov.instrsample.actions.kview

import com.eakurnikov.instrsample.actions.swipe.swipeDownFromCenterAction
import com.eakurnikov.instrsample.actions.swipe.swipeLeftFromCenterAction
import com.eakurnikov.instrsample.actions.swipe.swipeRightFromCenterAction
import com.eakurnikov.instrsample.actions.swipe.swipeUpFromCenterAction
import io.github.kakaocup.kakao.common.actions.SwipeableActions

fun SwipeableActions.swipeUpFromCenter() {
    view.perform(swipeUpFromCenterAction())
}

fun SwipeableActions.swipeRightFromCenter() {
    view.perform(swipeRightFromCenterAction())
}

fun SwipeableActions.swipeDownFromCenter() {
    view.perform(swipeDownFromCenterAction())
}

fun SwipeableActions.swipeLeftFromCenter() {
    view.perform(swipeLeftFromCenterAction())
}
