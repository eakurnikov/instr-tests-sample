package com.eakurnikov.instrsample.di.component

import com.eakurnikov.instrsample.data.api.PostsApi

interface NetworkComponent {
    val postsApi: PostsApi
}

class NetworkComponentImpl(
    override val postsApi: PostsApi
) : NetworkComponent
