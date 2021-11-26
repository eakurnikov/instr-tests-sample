package com.eakurnikov.instrsample.actions.view

import android.view.View
import android.view.ViewGroup
import androidx.test.espresso.UiController
import androidx.test.espresso.ViewAction
import androidx.test.espresso.matcher.ViewMatchers
import org.hamcrest.Matcher

class SetCustomViewAction(
    private val customView: View,
    private val index: Int = -1
) : ViewAction {

    override fun getConstraints(): Matcher<View> =
        ViewMatchers.isAssignableFrom(ViewGroup::class.java)

    override fun perform(uiController: UiController, view: View) {
        (view as? ViewGroup)?.addView(customView, index)
    }

    override fun getDescription(): String = "set custom view ($customView)"
}
