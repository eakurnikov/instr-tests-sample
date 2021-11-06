package com.eakurnikov.instrsample.tests.simple

import android.Manifest
import androidx.test.ext.junit.rules.activityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.rule.GrantPermissionRule
import com.eakurnikov.instrsample.MainActivity
import com.eakurnikov.instrsample.R
import com.eakurnikov.instrsample.matchers.ClassNameMatcher
import com.eakurnikov.instrsample.matchers.ViewSizeMatcher.Companion.withWidthAndHeight
import com.eakurnikov.instrsample.screens.MainScreen
import com.eakurnikov.instrsample.screens.SimpleScreen
import com.eakurnikov.instrsample.screens.VerySimpleScreen
import com.kaspersky.kaspresso.testcases.api.testcase.TestCase
import org.hamcrest.Matchers
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class GoodKaspressoTest : TestCase() {

    @get:Rule
    val runtimePermissionRule: GrantPermissionRule = GrantPermissionRule.grant(
        Manifest.permission.WRITE_EXTERNAL_STORAGE
    )

    @get:Rule
    val activityRule = activityScenarioRule<MainActivity>()

    @Test
    fun goodKaspressoTest() {
        before {
            /**
             * Some action to prepare the state
             */
        }.after {
            /**
             * Some action to revert the state
             */
        }.run {
            step("Open Simple screen") {
                MainScreen {
                    title.hasText(R.string.main_title)
                    title.hasTextColor(R.color.colorPrimary)

                    toSimpleScreenBtn {
                        isVisible()
                        hasText(R.string.simple_screen)
                        matches {
                            withClassName(ClassNameMatcher("androidx.appcompat.widget.AppCompatButton"))
                            withMatcher(Matchers.not(withWidthAndHeight(42f, 42f)))
                        }
                        isClickable()
                        click()
                    }
                }
            }

            step("Type \"Kaspresso\" and open Second screen") {
                SimpleScreen {
                    title {
                        hasText(R.string.simple_title)
                        hasTextColor(R.color.colorPrimary)
                    }

                    editText {
                        hasHint(R.string.simple_hint)
                        hasEmptyText()
                        typeText("Kaspresso")
                        hasText("Kaspresso")
                    }

                    closeSoftKeyboard()

                    btnNext {
                        isVisible()
                        hasText(R.string.next)
                        isClickable()
                        click()
                    }
                }
            }

            step("Check \"Kaspresso\" is displayed and return to Simple screen") {
                VerySimpleScreen {
                    title {
                        isVisible()
                        hasTextColor(R.color.colorPrimary)
                        hasText("Kaspresso")
                    }

                    pressBack()
                }
            }

            step("Delete text and open Second screen") {
                SimpleScreen {
                    editText.hasText("Kaspresso")

                    btnDelete {
                        isVisible()
                        hasText(R.string.clear)
                        isClickable()
                        click()
                    }

                    editText.hasEmptyText()

                    btnNext.click()
                }
            }

            step("Check stub text is displayed") {
                VerySimpleScreen {
                    title {
                        isVisible()
                        hasTextColor(R.color.colorPrimary)
                        hasText("")
                    }
                }
            }
        }
    }
}
