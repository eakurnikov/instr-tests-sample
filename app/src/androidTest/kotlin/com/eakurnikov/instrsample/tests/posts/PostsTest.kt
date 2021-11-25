package com.eakurnikov.instrsample.tests.posts

import androidx.test.ext.junit.rules.activityScenarioRule
import com.eakurnikov.instrsample.R
import com.eakurnikov.instrsample.common.TestStream
import com.eakurnikov.instrsample.data.NetworkConstants
import com.eakurnikov.instrsample.env.TestEnv
import com.eakurnikov.instrsample.screens.MainScreen
import com.eakurnikov.instrsample.screens.PostsScreen
import com.eakurnikov.instrsample.tests.base.CustomTestCase
import com.eakurnikov.instrsample.view.MainActivity
import com.kaspersky.kaspresso.annotations.E2e
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
@DisplayName("PostsTest: Проверка списка постов")
class PostsTest : CustomTestCase() {

    @get:Rule
    override val activityRule = activityScenarioRule<MainActivity>()

    override fun realEnv() = TestEnv.Real()

    override fun mockedEnv() = TestEnv.Mocked()

    @E2e
    @Test
    fun postsE2eTest(): Unit = test(realEnv())

    @Functional
    @Test
    fun postsMockedTest(): Unit = test(mockedEnv())

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

            step("Check list is fine") {
                PostsScreen {
                    errorTextView.isGone()

                    postsList {
                        isVisible()

                        firstChild<PostsScreen.PostItem> {
                            isVisible()
                            title.hasAnyText()
                            body.hasAnyText()
                        }

                        lastChild<PostsScreen.PostItem> {
                            isVisible()
                            title.hasAnyText()
                            body.hasAnyText()
                        }

                        children<PostsScreen.PostItem> {
                            isVisible()
                            title.hasAnyText()
                            body.hasAnyText()
                        }
                    }
                }
            }
        }
    }
}
