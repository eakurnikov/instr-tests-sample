package com.eakurnikov.instrsample.tests.simple

import android.Manifest
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.action.ViewActions.closeSoftKeyboard
import androidx.test.espresso.action.ViewActions.typeText
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.activityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.rule.GrantPermissionRule
import com.eakurnikov.instrsample.view.MainActivity
import com.eakurnikov.instrsample.R
import com.eakurnikov.instrsample.matchers.BackgroundColorMatcher.Companion.withBackgroundColor
import com.eakurnikov.instrsample.matchers.ClassNameMatcher
import com.eakurnikov.instrsample.matchers.ViewSizeMatcher.Companion.withWidthAndHeight
import com.kaspersky.kaspresso.testcases.api.testcase.TestCase
import org.hamcrest.Matchers
import org.hamcrest.core.AllOf.allOf
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class BadEspressoTest : TestCase() {

    @get:Rule
    val runtimePermissionRule: GrantPermissionRule = GrantPermissionRule.grant(
        Manifest.permission.WRITE_EXTERNAL_STORAGE
    )

    @get:Rule
    val activityRule = activityScenarioRule<MainActivity>()

    @Test
    fun badEspressoTest() {
        onView(withId(R.id.tv_main_title))
            .check(matches(withText(R.string.main_title)))
            .check(matches(hasTextColor(R.color.colorPrimary)))

        onView(withId(R.id.btn_simple_screen))
            .check(matches(withEffectiveVisibility(Visibility.VISIBLE)))
            .check(matches(withText(R.string.simple_screen)))
            .check(matches(withClassName(ClassNameMatcher("androidx.appcompat.widget.AppCompatButton"))))
            .check(matches(Matchers.not(withWidthAndHeight(42f, 42f))))
            .check(matches(isClickable()))
            .perform(ViewActions.click())

        onView(withId(R.id.tv_simple_title))
            .check(matches(withText(R.string.simple_title)))
            .check(matches(hasTextColor(R.color.colorPrimary)))

        onView(withId(R.id.et_simple))
            .check(matches(withHint(R.string.simple_hint)))
            .check(matches(withText("")))
            .perform(typeText("Kaspresso"))
            .check(matches(withText("Kaspresso")))

        onView(isRoot()).perform(closeSoftKeyboard())

        onView(
            allOf(
                isDescendantOfA(withId(R.id.simple_root)),
                hasSibling(withText(R.string.clear)),
                withBackgroundColor(R.color.colorPrimary)
            )
        )
            .check(matches(withEffectiveVisibility(Visibility.VISIBLE)))
            .check(matches(withText(R.string.next)))
            .check(matches(isClickable()))
            .perform(ViewActions.click())

        onView(withId(R.id.tv_very_simple_title))
            .check(matches(withEffectiveVisibility(Visibility.VISIBLE)))
            .check(matches(hasTextColor(R.color.colorPrimary)))
            .check(matches(withText("Kaspresso")))

        onView(isRoot())
            .perform(ViewActions.pressBack())

        onView(withId(R.id.et_simple))
            .check(matches(withText("Kaspresso")))

        onView(withId(R.id.btn_simple_delete))
            .check(matches(withEffectiveVisibility(Visibility.VISIBLE)))
            .check(matches(withText(R.string.clear)))
            .check(matches(isClickable()))
            .perform(ViewActions.click())

        onView(withId(R.id.et_simple))
            .check(matches(withText("")))

        onView(
            allOf(
                isDescendantOfA(withId(R.id.simple_root)),
                hasSibling(withText(R.string.clear)),
                withBackgroundColor(R.color.colorPrimary)
            )
        ).perform(ViewActions.click())

        onView(withId(R.id.tv_very_simple_title))
            .check(matches(withEffectiveVisibility(Visibility.VISIBLE)))
            .check(matches(hasTextColor(R.color.colorPrimary)))
            .check(matches(withText("")))
    }
}
