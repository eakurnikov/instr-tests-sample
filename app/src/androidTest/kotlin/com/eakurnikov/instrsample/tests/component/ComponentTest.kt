package com.eakurnikov.instrsample.tests.component

import androidx.test.ext.junit.rules.activityScenarioRule
import com.eakurnikov.instrsample.R
import com.eakurnikov.instrsample.common.TestStream
import com.eakurnikov.instrsample.data.NetworkConstants
import com.eakurnikov.instrsample.env.TestEnv
import com.eakurnikov.instrsample.screens.ContainerScreen
import com.eakurnikov.instrsample.tests.base.CustomTestCase
import com.eakurnikov.instrsample.view.ContainerActivity
import com.kaspersky.kaspresso.annotations.E2e
import com.kaspersky.kaspresso.annotations.Functional
import io.github.kakaocup.kakao.text.KButton
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
@Epic(TestStream.COMPONENT)
@DisplayName("ComponentTest: Компонентный тест на одну вьюшку")
class ComponentTest : CustomTestCase() {

    @get:Rule
    override val activityRule = activityScenarioRule<ContainerActivity>()

    override fun realEnv() = TestEnv.Real()

    override fun mockedEnv() = TestEnv.Mocked()

    @E2e
    @Test
    fun componentE2eTest(): Unit = test(realEnv())

    @Functional
    @Test
    fun componentMockedTest(): Unit = test(mockedEnv())

    override fun test(env: TestEnv): Unit =
        before {
            env.prepare()
        }.after {
            env.clean()
        }.run {
            step("Insert custom btm") {
                ViewsInjector.injectCustomBtnViaRule(activityRule)
            }

            step("Check custom btn") {
                ContainerScreen {
                    root {
                        KButton { withId(R.id.btn_custom) }.invoke {
                            hasText(R.string.custom_btn_title_before)
                            click()
                            hasText(R.string.custom_btn_title_after)
                        }
                    }
                }
            }

            step("Load user from network and print his name on custom button") {
                ViewsInjector.setLoadedUserNameToCustomBtn(activityRule)
            }

            step("Insert another custom btn") {
                ContainerScreen {
                    ViewsInjector.injectCustomBtnViaAction(this@run, root)
                }
            }
        }
}
