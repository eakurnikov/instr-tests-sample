package com.eakurnikov.instrsample.runner

import android.app.Activity
import android.app.Application
import android.content.Context
import android.content.Intent
import android.content.pm.ActivityInfo
import android.os.Bundle
import android.os.IBinder
import androidx.test.runner.AndroidJUnitRunner
import com.eakurnikov.instrsample.app.TestApp
import com.eakurnikov.instrsample.di.test.component.RootTestComponent
import com.eakurnikov.instrsample.di.test.module.RootTestModule
import com.eakurnikov.instrsample.env.report.TestInstrumentationReporter
import com.eakurnikov.instrsample.runner.config.TestRunConfigurator
import com.kaspersky.kaspresso.logger.UiTestLogger
import kotlin.jvm.Throws

class CustomInstrRunner : AndroidJUnitRunner() {
    private val tag: String = this::class.java.name
    private val testRunConfigurator = TestRunConfigurator()
    private lateinit var reporter: TestInstrumentationReporter
    private lateinit var logger: UiTestLogger
    lateinit var rootTestComponent: RootTestComponent

    /**
     * Use special TestApp for tests.
     */
    @Throws(
        InstantiationException::class,
        IllegalAccessException::class,
        ClassNotFoundException::class
    )
    override fun newApplication(cl: ClassLoader?, className: String?, ctx: Context?): Application {
        return super.newApplication(cl, TestApp::class.java.name, ctx).also { init(it) }
    }

    override fun onCreate(arguments: Bundle) {
        testRunConfigurator.onInstrumentationCreate(arguments)
        super.onCreate(arguments)
        reporter.onInstrumentationCreated(this, arguments)
    }

    override fun callApplicationOnCreate(app: Application?) {
        super.callApplicationOnCreate(app)
        logger.i(tag, "callApplicationOnCreate=[$app]")
    }

    override fun onException(obj: Any?, e: Throwable?): Boolean {
        reporter.onInstrumentationException(obj, e)
        return super.onException(obj, e)
    }

    override fun finish(resultCode: Int, results: Bundle?) {
        reporter.onInstrumentationFinish(this, resultCode, results)
        super.finish(resultCode, results)
    }

    override fun start() {
        super.start()
        logger.i(tag, "Instrumentation thread started")
    }

    override fun onStart() {
        super.onStart()
        logger.i(tag, "Instrumental tests started")
    }

    override fun onDestroy() {
        super.onDestroy()
        logger.i(tag, "destroyed")
    }

    override fun newActivity(
        clazz: Class<*>?,
        context: Context?,
        token: IBinder?,
        application: Application?,
        intent: Intent?,
        info: ActivityInfo?,
        title: CharSequence?,
        parent: Activity?,
        id: String?,
        lastNonConfigurationInstance: Any?
    ): Activity {
        val activity = super.newActivity(
            clazz,
            context,
            token,
            application,
            intent,
            info,
            title,
            parent,
            id,
            lastNonConfigurationInstance
        )
        logger.i(tag, "activity=[$clazz] created")
        return activity
    }

    /**
     * Initialize tests dependency graph here.
     * So we can get it anywhere via InstrumentationRegistry.getInstrumentation().
     * It is made more convenient in [TestDependenciesProvider]
     */
    private fun init(app: Application) {
        rootTestComponent = RootTestModule().createComponent(this).apply {
            logComponent.instrumentationLogger.i("Test dependencies initialized")
            reporter = reportComponent.testInstrumentationReporter.also {
                testRunConfigurator.onNewApplication(it)
                it.onNewApplication(app)
            }
            logger = logComponent.instrumentationLogger
        }
    }
}
