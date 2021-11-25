package com.eakurnikov.instrsample.data.app

import com.eakurnikov.instrsample.data.api.PostsApi
import com.eakurnikov.instrsample.data.dto.*
import retrofit2.Call

/**
 * Just for fun and for sample, not for prod.
 * Simple success mock for api. It is better not to hardcode mocks.
 */
class PostsApiMockSuccess : PostsApi {

    override fun getUser(): Call<UserDto> = CallMockSuccess(
        UserDto(
            1,
            "Test user #1",
            "Test username #1",
            "test@email.com",
            "Test phone #1",
            "Test website #1"
        )
    )

    override fun getPosts(): Call<List<PostDto>> = CallMockSuccess(
        listOf(
            PostDto(1, 1, "Test post #1", "Test post body #1"),
            PostDto(2, 1, "Test post #2", "Test post body #2"),
            PostDto(3, 1, "Test post #3", "Test post body #3"),
            PostDto(4, 1, "Test post #4", "Test post body #4"),
            PostDto(5, 1, "Test post #5", "Test post body #5"),
            PostDto(6, 1, "Test post #6", "Test post body #6"),
            PostDto(7, 1, "Test post #7", "Test post body #7"),
            PostDto(8, 1, "Test post #8", "Test post body #8"),
            PostDto(9, 1, "Test post #9", "Test post body #9"),
            PostDto(10, 1, "Test post #10", "Test post body #10"),
            PostDto(11, 1, "Test post #11", "Test post body #11"),
            PostDto(12, 1, "Test post #12", "Test post body #12"),
            PostDto(13, 1, "Test post #13", "Test post body #13"),
            PostDto(14, 1, "Test post #14", "Test post body #14")
        )
    )
}
