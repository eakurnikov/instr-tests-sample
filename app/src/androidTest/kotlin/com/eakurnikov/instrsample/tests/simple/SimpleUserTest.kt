package com.eakurnikov.instrsample.tests.simple

import androidx.test.ext.junit.rules.activityScenarioRule
import androidx.test.filters.SmallTest
import com.eakurnikov.instrsample.view.MainActivity
import com.eakurnikov.instrsample.R
import com.eakurnikov.instrsample.common.TestStream
import com.eakurnikov.instrsample.data.NetworkConstants
import com.eakurnikov.instrsample.data.dto.UserDto
import com.eakurnikov.instrsample.env.TestEnv
import com.eakurnikov.instrsample.matchers.ClassNameMatcher
import com.eakurnikov.instrsample.matchers.ViewSizeMatcher.Companion.withWidthAndHeight
import com.eakurnikov.instrsample.scenarios.TypeTextAndCheckTitleScenario
import com.eakurnikov.instrsample.screens.MainScreen
import com.eakurnikov.instrsample.tests.base.CustomTestCase
import com.kaspersky.kaspresso.annotations.E2e
import com.kaspersky.kaspresso.annotations.Functional
import io.qameta.allure.kotlin.Epic
import io.qameta.allure.kotlin.Link
import io.qameta.allure.kotlin.junit4.DisplayName
import org.hamcrest.Matchers
import org.junit.Rule
import org.junit.Test

@Link(
    value = "TestCase #111",
    name = "TestCase #111",
    url = NetworkConstants.TMS_URL,
    type = "tms"
)
@Epic(TestStream.SIMPLE)
@DisplayName("SimpleUserTest: Загрузка юзера и ввод его имени и почты в поле")
class SimpleUserTest : CustomTestCase() {

    @get:Rule
    override val activityRule = activityScenarioRule<MainActivity>()

    override fun realEnv() = TestEnv.Real()

    override fun mockedEnv() = TestEnv.Mocked()

    @E2e
    @SmallTest
    @Test
    fun simpleUserE2eTest() = test(realEnv())

    @Functional
    @SmallTest
    @Test
    fun simpleUserMockedTest() = test(mockedEnv())

    override fun test(env: TestEnv) {
        var user: UserDto? = null
        before {
            env.prepare()
            user = LoadUser()
            assert(user != null)
        }.after {
            env.clean()
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

            scenario(TypeTextAndCheckTitleScenario("${user!!.name}: ${user!!.email}"))
        }
    }
}
