package com.eakurnikov.instrsample.tests.simple

import android.Manifest
import androidx.test.ext.junit.rules.activityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.rule.GrantPermissionRule
import com.eakurnikov.instrsample.view.MainActivity
import com.eakurnikov.instrsample.R
import com.eakurnikov.instrsample.matchers.ClassNameMatcher
import com.eakurnikov.instrsample.matchers.ViewSizeMatcher.Companion.withWidthAndHeight
import com.eakurnikov.instrsample.scenarios.TypeTextAndCheckTitleScenario
import com.eakurnikov.instrsample.screens.MainScreen
import com.kaspersky.components.alluresupport.withAllureSupport
import com.kaspersky.kaspresso.annotations.E2e
import com.kaspersky.kaspresso.annotations.Functional
import com.kaspersky.kaspresso.kaspresso.Kaspresso
import com.kaspersky.kaspresso.testcases.api.testcase.TestCase
import org.hamcrest.Matchers
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class PerfectKaspressoTest : TestCase(
    kaspressoBuilder = Kaspresso.Builder.withAllureSupport()
) {
    @get:Rule
    val runtimePermissionRule: GrantPermissionRule = GrantPermissionRule.grant(
        Manifest.permission.WRITE_EXTERNAL_STORAGE
    )

    @get:Rule
    val activityRule = activityScenarioRule<MainActivity>()

    @E2e
    @SmallTest
    @Test
    fun e2eTest() = perfectKaspressoTest()

    @Functional
    @SmallTest
    @Test
    fun mockedTest() = perfectKaspressoTest()

    private fun perfectKaspressoTest() {
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
                testLogger.i("Instrumentation=${InstrumentationRegistry.getInstrumentation()}")
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

            scenario(TypeTextAndCheckTitleScenario("1"))
            scenario(TypeTextAndCheckTitleScenario(" "))
            scenario(TypeTextAndCheckTitleScenario("Kaspresso"))
            scenario(TypeTextAndCheckTitleScenario(""))
        }
    }
}