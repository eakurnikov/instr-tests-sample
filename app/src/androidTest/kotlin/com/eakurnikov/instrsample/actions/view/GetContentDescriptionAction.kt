package com.eakurnikov.instrsample.actions.view

import android.view.View
import androidx.test.espresso.UiController
import androidx.test.espresso.ViewAction
import androidx.test.espresso.matcher.ViewMatchers
import org.hamcrest.Matcher

class GetContentDescriptionAction(
    private val valueObserver: (String) -> Unit
) : ViewAction {

    override fun getConstraints(): Matcher<View> =
        ViewMatchers.isAssignableFrom(View::class.java)

    override fun perform(uiController: UiController, view: View) {
        valueObserver(view.contentDescription.toString())
    }

    override fun getDescription() = "getting content description from a View"
}
