package com.eakurnikov.instrsample.runners

import android.Manifest
import android.app.Instrumentation
import android.os.Build
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.runner.permission.PermissionRequester
import androidx.test.uiautomator.UiDevice
import org.junit.runner.Description
import org.junit.runner.Runner
import org.junit.runner.manipulation.Filter
import org.junit.runner.manipulation.Filterable
import org.junit.runner.manipulation.Sortable
import org.junit.runner.manipulation.Sorter
import org.junit.runner.notification.RunNotifier
import java.util.*

class CustomAndroidJUnit4(clazz: Class<*>) : Runner(), Filterable, Sortable {
    private val delegate = AndroidJUnit4(clazz)

    override fun run(notifier: RunNotifier?) {
        if (isDeviceTest()) {
            requestReadWriteExtStoragePermission()
        }
        delegate.run(notifier)
    }

    override fun getDescription(): Description = delegate.description

    override fun filter(filter: Filter?): Unit = delegate.filter(filter)

    override fun sort(sorter: Sorter?): Unit = delegate.sort(sorter)

    private fun isDeviceTest(): Boolean =
        System.getProperty("java.runtime.name")
            ?.lowercase(Locale.getDefault())
            ?.contains("android")
            ?: false

    private fun requestReadWriteExtStoragePermission() {
        val instrumentation: Instrumentation = InstrumentationRegistry.getInstrumentation()

        val execShellCommand: (String) -> String =
            UiDevice.getInstance(instrumentation)::executeShellCommand

        val packageName: String = instrumentation.targetContext.packageName

        when {
            Build.VERSION.SDK_INT in (Build.VERSION_CODES.N..Build.VERSION_CODES.P) -> {
                PermissionRequester().apply {
                    addPermissions(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    addPermissions(Manifest.permission.READ_EXTERNAL_STORAGE)
                    requestPermissions()
                }
            }
            Build.VERSION.SDK_INT == Build.VERSION_CODES.Q -> {
                execShellCommand("appops set --uid $packageName LEGACY_STORAGE allow")
            }
            Build.VERSION.SDK_INT >= Build.VERSION_CODES.R -> {
                execShellCommand("appops set --uid $packageName MANAGE_EXTERNAL_STORAGE allow")
            }
            else -> return
        }
    }
}
