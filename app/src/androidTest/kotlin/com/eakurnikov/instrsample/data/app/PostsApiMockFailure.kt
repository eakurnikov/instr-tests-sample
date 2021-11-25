package com.eakurnikov.instrsample.data.app

import com.eakurnikov.instrsample.data.api.PostsApi
import com.eakurnikov.instrsample.data.dto.PostDto
import com.eakurnikov.instrsample.data.dto.UserDto
import retrofit2.Call

/**
 * Just for fun and for sample, not for prod.
 * Simple failure mock for api. It is better not to hardcode mocks.
 */
class PostsApiMockFailure : PostsApi {

    override fun getUser(): Call<UserDto> = CallMockFailure(EmptyResponseBody)

    override fun getPosts(): Call<List<PostDto>> = CallMockFailure(EmptyResponseBody)
}
