package com.eakurnikov.instrsample.di.app.mock

import com.eakurnikov.instrsample.data.app.PostsApiMockSuccess
import com.eakurnikov.instrsample.di.component.NetworkComponent
import com.eakurnikov.instrsample.di.component.NetworkComponentImpl

/**
 * All app component mocks can be listed here. Different mock implementation can be used for each.
 * It is configured from the test. See [PostsFailureTest]
 */
class MocksConfig(
    val networkComponentMock: NetworkComponent? = NetworkComponentImpl(
        postsApi = PostsApiMockSuccess()
    )
)
