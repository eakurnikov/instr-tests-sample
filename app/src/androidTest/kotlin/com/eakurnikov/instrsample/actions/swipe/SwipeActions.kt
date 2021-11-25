package com.eakurnikov.instrsample.actions.swipe

import androidx.test.espresso.ViewAction
import androidx.test.espresso.action.*

fun swipeUpFromCenterAction(): ViewAction =
    ViewActions.actionWithAssertions(
        GeneralSwipeAction(
            Swipe.FAST,
            ElementTranslation.CENTER_UP,
            GeneralLocation.TOP_CENTER,
            Press.FINGER
        )
    )

fun swipeRightFromCenterAction(): ViewAction =
    ViewActions.actionWithAssertions(
        GeneralSwipeAction(
            Swipe.FAST,
            ElementTranslation.CENTER_RIGHT,
            GeneralLocation.CENTER_RIGHT,
            Press.FINGER
        )
    )

fun swipeDownFromCenterAction(): ViewAction =
    ViewActions.actionWithAssertions(
        GeneralSwipeAction(
            Swipe.FAST,
            ElementTranslation.CENTER_DOWN,
            GeneralLocation.BOTTOM_CENTER,
            Press.FINGER
        )
    )

fun swipeLeftFromCenterAction(): ViewAction =
    ViewActions.actionWithAssertions(
        GeneralSwipeAction(
            Swipe.FAST,
            ElementTranslation.CENTER_LEFT,
            GeneralLocation.CENTER_LEFT,
            Press.FINGER
        )
    )
