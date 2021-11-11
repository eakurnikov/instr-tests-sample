package com.eakurnikov.instrsample.tests

import android.Manifest
import android.provider.CallLog
import android.provider.Telephony
import androidx.test.ext.junit.rules.activityScenarioRule
import androidx.test.rule.GrantPermissionRule
import com.eakurnikov.instrsample.R
import com.eakurnikov.instrsample.screens.FlakyScreen
import com.eakurnikov.instrsample.screens.MainScreen
import com.eakurnikov.instrsample.screens.SimpleScreen
import com.eakurnikov.instrsample.view.MainActivity
import com.kaspersky.kaspresso.testcases.api.testcase.TestCase
import com.kaspersky.kaspresso.testcases.core.testcontext.BaseTestContext
import io.github.kakaocup.kakao.screen.Screen
import org.junit.Assert
import org.junit.Rule
import org.junit.Test
import java.util.concurrent.TimeUnit

class FlakyTest : TestCase() {

    companion object {
        private const val CALL_PHONE_NUMBER = "+79222222222"
        private const val SMS_PHONE_NUMBER = "+79111111111"
        private const val SMS_MESSAGE_TEXT = "KASPRESSO"
        private const val CONTENT_UPDATE_DELAY = 5_000L
        private const val CALL_DURATION = 2_000L
    }

    @get:Rule
    val runtimePermissionRule: GrantPermissionRule = GrantPermissionRule.grant(
        Manifest.permission.READ_CALL_LOG,
        Manifest.permission.READ_SMS,
        Manifest.permission.WRITE_EXTERNAL_STORAGE,
        Manifest.permission.READ_EXTERNAL_STORAGE
    )

    @get:Rule
    val activityTestRule = activityScenarioRule<MainActivity>()

    @Test
    fun flakyViewsTest(): Unit = run {
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

        step("Type \"Kaspresso\" on Simple screen and open Second screen") {
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

        var messageInfo: SmsInfo? = null

        step("Receive SMS message") {
            device.exploit.rotate()
            device.phone.receiveSms(
                SMS_PHONE_NUMBER,
                SMS_MESSAGE_TEXT
            )
            Screen.idle(CONTENT_UPDATE_DELAY)

            messageInfo = getLastSmsInfo()
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

            Assert.assertEquals(phoneNumberToCall, getLastCallPhoneNumber())
        }
    }

    private fun BaseTestContext.getLastCallPhoneNumber(): String? {
        val cursor = device.targetContext.contentResolver.query(
            CallLog.Calls.CONTENT_URI,
            null,
            null,
            null,
            CallLog.Calls.DATE + " DESC"
        )

        cursor?.use {
            return if (it.moveToFirst()) {
                it.getString(it.getColumnIndexOrThrow(CallLog.Calls.NUMBER))
            } else {
                null
            }
        } ?: return null
    }

    private fun BaseTestContext.getLastSmsInfo(): SmsInfo? {
        val cursor = device.targetContext.contentResolver.query(
            Telephony.Sms.Inbox.CONTENT_URI,
            arrayOf(
                Telephony.TextBasedSmsColumns.ADDRESS,
                Telephony.TextBasedSmsColumns.BODY
            ),
            null,
            null,
            Telephony.Sms.Inbox.DEFAULT_SORT_ORDER
        )

        cursor?.use {
            return if (it.moveToFirst()) {
                val address = it.getString(
                    it.getColumnIndexOrThrow(Telephony.TextBasedSmsColumns.ADDRESS)
                )
                val message = it.getString(
                    it.getColumnIndexOrThrow(Telephony.TextBasedSmsColumns.BODY)
                )
                SmsInfo(address, message)
            } else {
                null
            }
        } ?: return null
    }

    private data class SmsInfo(val address: String, val message: String)
}
