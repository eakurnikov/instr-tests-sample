package com.eakurnikov.instrsample.tests.posts

import androidx.test.ext.junit.rules.activityScenarioRule
import com.eakurnikov.instrsample.R
import com.eakurnikov.instrsample.common.TestStream
import com.eakurnikov.instrsample.data.NetworkConstants
import com.eakurnikov.instrsample.data.app.EmptyResponseBody
import com.eakurnikov.instrsample.data.app.PostsApiMockFailure
import com.eakurnikov.instrsample.di.app.mock.MocksConfig
import com.eakurnikov.instrsample.di.component.NetworkComponentImpl
import com.eakurnikov.instrsample.env.TestEnv
import com.eakurnikov.instrsample.screens.MainScreen
import com.eakurnikov.instrsample.screens.PostsScreen
import com.eakurnikov.instrsample.tests.base.CustomTestCase
import com.eakurnikov.instrsample.view.MainActivity
import com.kaspersky.kaspresso.annotations.Functional
import io.qameta.allure.kotlin.Epic
import io.qameta.allure.kotlin.Link
import io.qameta.allure.kotlin.junit4.DisplayName
import org.junit.Rule
import org.junit.Test

@Link(
    value = "TestCase #111",
    name = "TestCase #111",
    url = NetworkConstants.TMS_URL,
    type = "tms"
)
@Epic(TestStream.POSTS)
@DisplayName("PostsFailureTest: Проверка экрана со списком постов при неудачной загрузке")
class PostsFailureTest : CustomTestCase() {

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
    @Test
    fun postsFailureMockedTest(): Unit = test(mockedEnv())

    override fun test(env: TestEnv) {
        before {
            env.prepare()
        }.after {
            env.clean()
        }.run {
            step("Open Posts screen") {
                MainScreen {
                    adbServer
                    title.hasText(R.string.main_title)
                    title.hasTextColor(R.color.colorPrimary)

                    toPostsScreenBtn {
                        isVisible()
                        hasText(R.string.posts_screen)
                        isClickable()
                        click()
                    }
                }
            }

            step("Check list is gone and response code is visible") {
                PostsScreen {
                    postsList.isGone()

                    errorTextView {
                        isVisible()
                        hasText(EmptyResponseBody.code.toString())
                    }
                }
            }
        }
    }
}
