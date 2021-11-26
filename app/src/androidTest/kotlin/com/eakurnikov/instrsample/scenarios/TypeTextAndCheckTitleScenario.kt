package com.eakurnikov.instrsample.scenarios

import com.eakurnikov.instrsample.R
import com.eakurnikov.instrsample.screens.SimpleScreen
import com.eakurnikov.instrsample.screens.VerySimpleScreen
import com.kaspersky.kaspresso.testcases.api.scenario.Scenario
import com.kaspersky.kaspresso.testcases.core.testcontext.TestContext

class TypeTextAndCheckTitleScenario(
    private val text: String
) : Scenario() {

    override val steps: TestContext<Unit>.() -> Unit = {
        testLogger.i("The given text is \"$text\"")

        step("Type \"$text\" and open Very Simple Screen") {
            SimpleScreen {
                title {
                    hasText(R.string.simple_title)
                    hasTextColor(R.color.colorPrimary)
                }

                btnDelete {
                    isVisible()
                    hasText(R.string.clear)
                    isClickable()
                    click()
                }

                editText {
                    hasHint(R.string.simple_hint)
                    hasEmptyText()
                    typeText(text)
                    hasText(text)
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

        step("Check \"$text\" is displayed and return to Simple screen") {
            VerySimpleScreen {
                title {
                    isVisible()
                    hasTextColor(R.color.colorPrimary)
                    hasText(text)
                }

                pressBack()
            }
        }
    }
}
