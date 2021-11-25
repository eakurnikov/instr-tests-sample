package com.eakurnikov.instrsample.data.dto

import com.google.gson.annotations.SerializedName

data class PostDto(

    @SerializedName("id")
    val id: Long,

    @SerializedName("userId")
    val userId: Long,

    @SerializedName("title")
    val title: String,

    @SerializedName("body")
    val body: String
)
