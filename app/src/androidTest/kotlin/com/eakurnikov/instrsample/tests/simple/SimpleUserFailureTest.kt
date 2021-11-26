package com.eakurnikov.instrsample.tests.simple

import androidx.test.ext.junit.rules.activityScenarioRule
import androidx.test.filters.SmallTest
import com.eakurnikov.instrsample.view.MainActivity
import com.eakurnikov.instrsample.R
import com.eakurnikov.instrsample.common.TestStream
import com.eakurnikov.instrsample.data.NetworkConstants
import com.eakurnikov.instrsample.data.app.PostsApiMockFailure
import com.eakurnikov.instrsample.di.app.mock.MocksConfig
import com.eakurnikov.instrsample.di.component.NetworkComponentImpl
import com.eakurnikov.instrsample.env.TestEnv
import com.eakurnikov.instrsample.matchers.ClassNameMatcher
import com.eakurnikov.instrsample.matchers.ViewSizeMatcher.Companion.withWidthAndHeight
import com.eakurnikov.instrsample.scenarios.TypeTextAndCheckTitleScenario
import com.eakurnikov.instrsample.screens.MainScreen
import com.eakurnikov.instrsample.tests.base.CustomTestCase
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
@DisplayName("SimpleUserFailureTest: Неудачная загрузка юзера и ввод различных строк в поле")
class SimpleUserFailureTest : CustomTestCase() {

    @get:Rule
    override val activityRule = activityScenarioRule<MainActivity>()

    override fun mockedEnv() = TestEnv.Mocked(
        mocksConfig = MocksConfig(
            networkComponentMock = NetworkComponentImpl(
                postsApi = PostsApiMockFailure()
            )
        )
    )

    @Functional
    @SmallTest
    @Test
    fun simpleUserFailureMockedTest() = test(mockedEnv())

    override fun test(env: TestEnv) {
        before {
            env.prepare()
            assert(LoadUser() == null)
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

            scenario(TypeTextAndCheckTitleScenario("1"))
            scenario(TypeTextAndCheckTitleScenario(" "))
            scenario(TypeTextAndCheckTitleScenario("Kaspresso"))
            scenario(TypeTextAndCheckTitleScenario(""))
        }
    }
}
