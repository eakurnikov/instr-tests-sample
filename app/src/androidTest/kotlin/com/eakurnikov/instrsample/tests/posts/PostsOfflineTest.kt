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
@DisplayName("PostsOfflineTest: Проверка списка постов без сети")
class PostsOfflineTest : CustomTestCase() {

    @get:Rule
    override val activityRule = activityScenarioRule<MainActivity>()

    override fun realEnv() = TestEnv.Real()

    @E2e
    @Test
    @DisplayName("PostsOfflineTest: Проверка списка постов без сети. Description можно писать и тут")
    fun postsOfflineE2eTest(): Unit = test(realEnv())

    override fun test(env: TestEnv) {
        before {
            env.prepare()
            device.network.disable()
        }.after {
            env.clean()
            device.network.enable()
        }.run {
            step("Open Posts screen") {
                MainScreen {
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

            step("Check list is gone and error label is visible") {
                PostsScreen {
                    postsList.isGone()

                    errorTextView {
                        isVisible()
                        hasText(R.string.error)
                    }
                }
            }
        }
    }
}
