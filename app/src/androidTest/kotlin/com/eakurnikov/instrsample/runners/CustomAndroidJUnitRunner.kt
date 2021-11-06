package com.eakurnikov.instrsample.runners

import android.app.Application
import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.test.runner.AndroidJUnitRunner
import kotlin.jvm.Throws

class CustomAndroidJUnitRunner: AndroidJUnitRunner() {
    private val tag: String = CustomAndroidJUnitRunner::class.java.simpleName

    @Throws(
        InstantiationException::class,
        IllegalAccessException::class,
        ClassNotFoundException::class
    )
    override fun newApplication(
        cl: ClassLoader?,
        className: String?,
        context: Context?
    ): Application {
        val app = super.newApplication(cl, className, context)
        Log.i(tag, "newApplication app=$app")
        return app
    }

    override fun onCreate(arguments: Bundle?) {
        super.onCreate(arguments)
    }
}
