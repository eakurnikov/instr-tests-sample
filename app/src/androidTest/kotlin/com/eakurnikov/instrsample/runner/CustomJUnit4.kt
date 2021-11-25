package com.eakurnikov.instrsample.runner

import androidx.test.ext.junit.runners.AndroidJUnit4
import com.eakurnikov.instrsample.env.permissions.Permission
import com.eakurnikov.instrsample.runner.listener.CustomRunListener
import io.qameta.allure.android.AllureAndroidLifecycle
import io.qameta.allure.kotlin.Allure
import io.qameta.allure.kotlin.junit4.AllureJunit4
import org.junit.runner.Description
import org.junit.runner.Runner
import org.junit.runner.manipulation.Filter
import org.junit.runner.manipulation.Filterable
import org.junit.runner.manipulation.Sortable
import org.junit.runner.manipulation.Sorter
import org.junit.runner.notification.RunNotifier

class CustomJUnit4(clazz: Class<*>) : Runner(), Filterable, Sortable {
    private val delegate = AndroidJUnit4(clazz)

    override fun run(notifier: RunNotifier?) {
        /** Request permissions here for allure results */
        Permission.requestReadWriteExtStoragePermission()

        /** Init allure */
        Allure.lifecycle = AllureAndroidLifecycle

        /** Adding allure reporter run listener, see AllureJunit4 implementation */
        notifier?.addListener(AllureJunit4())

        /** Adding custom run listener */
        notifier?.addListener(CustomRunListener())

        delegate.run(notifier)
    }

    override fun getDescription(): Description = delegate.description

    override fun filter(filter: Filter?): Unit = delegate.filter(filter)

    override fun sort(sorter: Sorter?): Unit = delegate.sort(sorter)
}
