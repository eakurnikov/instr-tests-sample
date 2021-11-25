package com.eakurnikov.instrsample.env.permissions

import android.Manifest
import android.os.Build
import androidx.test.runner.permission.PermissionRequester
import com.eakurnikov.instrsample.env.TestEnv

object Permission {

    fun requestReadWriteExtStoragePermission() {
        val execShellCommand: (String) -> String = TestEnv.uiDevice::executeShellCommand
        val packageName: String = TestEnv.targetContext.packageName

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
