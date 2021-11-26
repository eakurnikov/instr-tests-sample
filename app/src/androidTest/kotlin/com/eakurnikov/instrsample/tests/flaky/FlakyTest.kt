package com.eakurnikov.instrsample.tests.flaky

import android.Manifest
import androidx.test.ext.junit.rules.activityScenarioRule
import androidx.test.rule.GrantPermissionRule
import com.eakurnikov.instrsample.R
import com.eakurnikov.instrsample.common.TestStream
import com.eakurnikov.instrsample.data.NetworkConstants
import com.eakurnikov.instrsample.env.TestEnv
import com.eakurnikov.instrsample.screens.FlakyScreen
import com.eakurnikov.instrsample.screens.MainScreen
import com.eakurnikov.instrsample.screens.SimpleScreen
import com.eakurnikov.instrsample.tests.base.CustomTestCase
import com.eakurnikov.instrsample.tests.component.ViewsInjector
import com.eakurnikov.instrsample.view.MainActivity
import com.kaspersky.kaspresso.annotations.E2e
import io.github.kakaocup.kakao.screen.Screen
import io.github.kakaocup.kakao.text.KButton
import io.qameta.allure.kotlin.Epic
import io.qameta.allure.kotlin.Link
import io.qameta.allure.kotlin.junit4.DisplayName
import org.junit.Assert
import org.junit.Rule
import org.junit.Test
import java.util.concurrent.TimeUnit

@Link(
    value = "TestCase #111",
    name = "TestCase #111",
    url = NetworkConstants.TMS_URL,
    type = "tms"
)
@Epic(TestStream.FLAKY)
@DisplayName("FlakyTest: Очень странный тест, но так тоже можно")
class FlakyTest : CustomTestCase() {

    companion object {
        private const val CALL_PHONE_NUMBER = "+79222222222"
        private const val SMS_PHONE_NUMBER = "+79111111111"
        private const val SMS_MESSAGE_TEXT = "KASPRESSO"
        private const val CONTENT_UPDATE_DELAY = 5_000L
        private const val CALL_DURATION = 2_000L
    }

    @get:Rule
    override val runtimePermissionRule: GrantPermissionRule = GrantPermissionRule.grant(
        Manifest.permission.WRITE_EXTERNAL_STORAGE,
        Manifest.permission.READ_CALL_LOG,
        Manifest.permission.READ_SMS
    )

    @get:Rule
    override val activityRule = activityScenarioRule<MainActivity>()

    override fun realEnv() = TestEnv.Real()

    @E2e
    @Test
    fun flakyViewsE2eTest(): Unit = test(realEnv())

    override fun test(env: TestEnv): Unit =
        before {
            env.prepare()
            ViewsInjector.injectCustomBtnViaRule(activityRule)
        }.after {
            env.clean()
        }.run {
            step("Check custom btn") {
                MainScreen {
                    root {
                        KButton { withId(R.id.btn_custom) }.invoke {
                            hasText(R.string.custom_btn_title_before)
                            click()
                            hasText(R.string.custom_btn_title_after)
                        }
                    }
                }
            }

            step("Open Flaky screen") {
                MainScreen {
                    title.hasText(R.string.main_title)
                    title.hasTextColor(R.color.colorPrimary)

                    toFlakyScreenBtn {
                        isVisible()
                        hasText(R.string.flaky_screen)
                        isClickable()
                        click()
                    }
                }
            }

            step("Check ScrollView screen is visible") {
                FlakyScreen {
                    scrollView.isVisible()
                }
            }

            step("Check flaky text view is visible") {
                FlakyScreen {
                    flakyTextView {
                        flakySafely(timeoutMs = 3000) { isVisible() }
                        hasText(R.string.flaky_textview_text_start)
                    }
                }
            }

            step("Check flaky text view's text") {
                FlakyScreen {
                    flakySafely(timeoutMs = TimeUnit.SECONDS.toMillis(4)) {
                        flakyTextView.hasText(R.string.flaky_textview_text_end)
                    }
                }
            }

            step("Check flaky button is visible") {
                FlakyScreen {
                    flakyBtn {
                        isVisible()
                        hasText(R.string.flaky_btn_text_start)
                    }
                }
            }

            step("Check flaky button's text") {
                FlakyScreen {
                    flakySafely(timeoutMs = TimeUnit.SECONDS.toMillis(5)) {
                        flakyBtn {
                            hasText(R.string.flaky_btn_text_end)
                            click()
                        }
                    }
                }
            }

            step("Type \"Kaspresso\" on Simple screen and open Very Simple screen") {
                SimpleScreen {
                    title {
                        hasText(R.string.simple_title)
                        hasTextColor(R.color.colorPrimary)
                    }

                    editText {
                        hasHint(R.string.simple_hint)
                        hasEmptyText()
                        typeText(SMS_MESSAGE_TEXT)
                        hasText(SMS_MESSAGE_TEXT)
                    }

                    closeSoftKeyboard()
                }
            }

            step("Rotate device and check if entered text is present") {
                device.exploit.rotate()

                SimpleScreen {
                    editText {
                        hasText(SMS_MESSAGE_TEXT)
                    }
                }
            }

            step("Open Second screen") {
                SimpleScreen {
                    btnNext {
                        isVisible()
                        hasText(R.string.next)
                        isClickable()
                        click()
                    }
                }
            }

            step("Open url in Chrome") {
                device.exploit.pressHome()

                device.apps.openUrlInChrome("https://github.com/KasperskyLab/Kaspresso")
                Thread.sleep(3_000)

                device.exploit.pressHome()

                device.apps.openRecent("Debug sample")
            }

            var messageInfo: Phone.SmsInfo? = null

            step("Receive SMS message") {
                device.phone.receiveSms(
                    SMS_PHONE_NUMBER,
                    SMS_MESSAGE_TEXT
                )
                Screen.idle(CONTENT_UPDATE_DELAY)

                messageInfo = Phone.getLastSmsInfo(this)
                Assert.assertNotNull(messageInfo)
                Assert.assertEquals(SMS_PHONE_NUMBER, messageInfo?.address)
                Assert.assertEquals(SMS_MESSAGE_TEXT, messageInfo?.message)
            }

            step("Emulate a call") {
                Assert.assertNotNull(messageInfo)
                val phoneNumberToCall = CALL_PHONE_NUMBER
                device.phone.emulateCall(phoneNumberToCall)
                Screen.idle(CALL_DURATION)

                device.phone.cancelCall(phoneNumberToCall)
                Screen.idle(CONTENT_UPDATE_DELAY)

                Assert.assertEquals(phoneNumberToCall, Phone.getLastCallPhoneNumber(this))
            }
        }
}
