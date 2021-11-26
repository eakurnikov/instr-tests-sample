package com.eakurnikov.instrsample.actions.view

import android.view.View
import androidx.test.espresso.UiController
import androidx.test.espresso.ViewAction
import androidx.test.espresso.matcher.ViewMatchers
import androidx.viewpager.widget.ViewPager
import org.hamcrest.Matcher
import org.hamcrest.Matchers

class GetCurrentPageItemAction(
    private val valueObserver: (Int) -> Unit
) : ViewAction {

    override fun getConstraints(): Matcher<View> = Matchers.allOf(
        ViewMatchers.isAssignableFrom(ViewPager::class.java),
        ViewMatchers.isDisplayed()
    )

    override fun perform(uiController: UiController, view: View) {
        if (view is ViewPager) {
            valueObserver(view.currentItem)
        }
    }

    override fun getDescription(): String = "Get ViewPager current item"
}
