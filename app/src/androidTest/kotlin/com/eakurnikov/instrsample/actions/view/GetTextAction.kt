package com.eakurnikov.instrsample.actions.view

import android.view.View
import android.widget.TextView
import androidx.test.espresso.UiController
import androidx.test.espresso.ViewAction
import androidx.test.espresso.matcher.ViewMatchers
import com.eakurnikov.instrsample.env.TestEnv
import org.hamcrest.Matcher

class GetTextAction(
    private val valueObserver: (String) -> Unit
) : ViewAction {

    override fun getConstraints(): Matcher<View> =
        ViewMatchers.isAssignableFrom(TextView::class.java)

    override fun perform(uiController: UiController, view: View) {
        TestEnv.logger.d("GetTextAction start")
        valueObserver((view as? TextView)?.text.toString())
        TestEnv.logger.d("GetTextAction finish ${(view as? TextView)?.text.toString()}")
    }

    override fun getDescription() = "getting text from a TextView"
}
