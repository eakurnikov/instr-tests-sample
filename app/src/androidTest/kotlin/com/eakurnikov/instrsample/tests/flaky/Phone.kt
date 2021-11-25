package com.eakurnikov.instrsample.tests.flaky

import android.provider.CallLog
import android.provider.Telephony
import com.kaspersky.kaspresso.testcases.core.testcontext.BaseTestContext

/**
 * Just for fun and for sample, not for prod.
 * Works with call logs and telephony.
 */
object Phone {

    fun getLastCallPhoneNumber(testContext: BaseTestContext): String? {
        val cursor = testContext.device.targetContext.contentResolver.query(
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

    fun getLastSmsInfo(testContext: BaseTestContext): SmsInfo? {
        val cursor = testContext.device.targetContext.contentResolver.query(
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

    data class SmsInfo(val address: String, val message: String)
}
