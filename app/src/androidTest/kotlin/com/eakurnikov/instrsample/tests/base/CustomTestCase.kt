package com.eakurnikov.instrsample.tests.base

import android.Manifest
import android.app.Activity
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.rule.GrantPermissionRule
import com.eakurnikov.instrsample.env.TestEnv
import com.eakurnikov.instrsample.env.kaspresso.custom
import com.eakurnikov.instrsample.runner.CustomJUnit4
import com.kaspersky.kaspresso.kaspresso.Kaspresso
import com.kaspersky.kaspresso.testcases.api.testcase.TestCase
import org.junit.Rule
import org.junit.runner.RunWith

/** Using custom JUnit4 runner for setting run listeners */
@RunWith(CustomJUnit4::class)
abstract class CustomTestCase(
    /** Using custom Kaspresso builder */
    kaspressoBuilder: Kaspresso.Builder = Kaspresso.Builder.custom()
) : TestCase(kaspressoBuilder) {

    @get:Rule
    open val runtimePermissionRule: GrantPermissionRule = GrantPermissionRule.grant(
        Manifest.permission.WRITE_EXTERNAL_STORAGE
    )

    @get:Rule
    abstract val activityRule: ActivityScenarioRule<out Activity>

    protected open fun realEnv(): TestEnv.Real? = null

    protected open fun mockedEnv(): TestEnv.Mocked? = null

    protected abstract fun test(env: TestEnv)
}
