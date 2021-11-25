package com.eakurnikov.instrsample.data.api

import com.eakurnikov.instrsample.data.dto.PostDto
import com.eakurnikov.instrsample.data.dto.UserDto
import retrofit2.Call
import retrofit2.http.GET

interface PostsApi {

    @GET("/users/1")
    fun getUser(): Call<UserDto>

    @GET("/posts")
    fun getPosts(): Call<List<PostDto>>
}
