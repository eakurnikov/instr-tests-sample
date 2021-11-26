package com.eakurnikov.instrsample.tests.component

import android.app.Activity
import android.view.View
import android.widget.Button
import android.widget.LinearLayout
import androidx.test.ext.junit.rules.ActivityScenarioRule
import com.eakurnikov.instrsample.R
import com.eakurnikov.instrsample.actions.view.SetCustomViewAction
import com.eakurnikov.instrsample.data.dto.UserDto
import com.eakurnikov.instrsample.di.dependency
import com.kaspersky.kaspresso.testcases.core.testcontext.BaseTestContext
import io.github.kakaocup.kakao.common.views.KView

/**
 * Just for fun and for sample, not for prod.
 * Inserts views from instrumentation tests thread to activity in main thread.
 */
object ViewsInjector {

    fun injectCustomBtnViaRule(activityRule: ActivityScenarioRule<out Activity>) {
        activityRule.scenario.onActivity { activity: Activity ->
            /** Switched to main thread */
            val customView = View.inflate(activity, R.layout.view_custom, null)

            customView.findViewById<Button>(R.id.btn_custom).setOnClickListener {
                (it as Button).text = it.resources.getText(R.string.custom_btn_title_after)
            }

            activity.findViewById<LinearLayout>(R.id.root).addView(customView, 0)
        }
    }

    fun injectCustomBtnViaAction(testContext: BaseTestContext, containerView: KView) {
        val activity: Activity? = testContext.device.activities.getResumed()
        assert(activity != null)

        val btn = Button(activity).apply {
            text = activity!!.getText(R.string.custom_btn_title_before)
        }

        /** View Acions are executed on the main thread, so we can change the hierarchy */
        containerView.view.perform(SetCustomViewAction(btn, 1))
    }

    /**
     * This method is called from InstrumentationThread
     */
    fun setLoadedUserNameToCustomBtn(activityRule: ActivityScenarioRule<out Activity>) {
        activityRule.scenario.onActivity { activity: Activity ->
            /** Switched to main thread */
            var user: UserDto? = null
            val api = activity.dependency { networkComponent.postsApi }
            val mainExecutor = activity.dependency { appExecutors.mainThread }
            val networkExecutor = activity.dependency { appExecutors.networkIo }

            networkExecutor.execute {
                /** Switched to background thread */
                val response = api.getUser().execute()
                if (response.isSuccessful) {
                    user = response.body()
                    assert(user != null)
                }

                mainExecutor.execute {
                    /** Return on main thread */
                    activity.findViewById<Button>(R.id.btn_custom).text = user!!.name
                }
            }
        }
    }
}
