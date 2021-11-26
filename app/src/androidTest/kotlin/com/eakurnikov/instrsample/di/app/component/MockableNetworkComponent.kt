package com.eakurnikov.instrsample.di.app.component

import com.eakurnikov.instrsample.data.api.PostsApi
import com.eakurnikov.instrsample.di.app.mock.MockableComponent
import com.eakurnikov.instrsample.di.app.mock.MockableDependency
import com.eakurnikov.instrsample.di.component.NetworkComponent

class MockableNetworkComponent(
    private val mockablePostsApi: MockableDependency<PostsApi>
) : NetworkComponent, MockableComponent<NetworkComponent> {

    override val postsApi: PostsApi
        get() = mockablePostsApi.value

    override fun mock(mock: NetworkComponent) {
        mockablePostsApi.mock(mock.postsApi)
    }

    override fun unmock() {
        mockablePostsApi.unmock()
    }
}
