package com.eakurnikov.instrsample.tests.simple

import com.eakurnikov.instrsample.data.dto.UserDto
import com.eakurnikov.instrsample.di.dependency
import com.eakurnikov.instrsample.env.TestEnv

/**
 * Just for fun and for sample, not for prod.
 * Loads user from network.
 */
object LoadUser {

    /**
     * Called from InstrumentationThread so we can go to network
     */
    operator fun invoke(): UserDto? {
        TestEnv.logger.i("Loading user ...")
        var user: UserDto? = null
        val api = TestEnv.targetContext.dependency { networkComponent.postsApi }
        val response = api.getUser().execute()
        if (response.isSuccessful) {
            TestEnv.logger.i("User loaded successfully")
            user = response.body()
        } else {
            TestEnv.logger.i("User load failure")
        }
        return user
    }
}
