package com.eakurnikov.instrsample.env

import com.eakurnikov.instrsample.di.app.mock.Mocker
import com.eakurnikov.instrsample.di.app.mock.MocksConfig
import com.eakurnikov.instrsample.di.component.NetworkComponent
import com.eakurnikov.instrsample.di.test.TestDependenciesProvider

/**
 * Facade class for working with different dependencies from tests.
 */
sealed class TestEnv {

    /** Facade class for working with tests (not app) dependencies graph */
    companion object : TestDependenciesProvider()

    abstract fun prepare()
    abstract fun clean()

    /**
     * Still can configure something (not mocks) in app using this api
     */
    class Real : TestEnv() {
        init {
            TestEnv.logger.i("Real test env created")
        }

        override fun prepare() = Unit
        override fun clean() = Unit
    }

    /**
     * Special api for mocking app dependencies for tests.
     */
    class Mocked(
        val mocksConfig: MocksConfig = MocksConfig()
    ) : TestEnv() {

        private val mocker = Mocker()

        init {
            TestEnv.logger.i("Mocked test env created")
        }

        /**
         * Mocking needed app components. Called before test is started.
         */
        override fun prepare() {
            mocksConfig.networkComponentMock?.let { mock: NetworkComponent ->
                mocker.mockComponent(mock) { networkComponent }
            }
        }

        /**
         * Removing app components mocks. Called after test is finished.
         */
        override fun clean() {
            mocker.unmockComponent { networkComponent }
        }
    }
}
