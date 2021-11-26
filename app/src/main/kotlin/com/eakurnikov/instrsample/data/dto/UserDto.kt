package com.eakurnikov.instrsample.data.dto

import com.google.gson.annotations.SerializedName

data class UserDto(

    @SerializedName("id")
    val id: Long,

    @SerializedName("name")
    val name: String,

    @SerializedName("username")
    val username: String,

    @SerializedName("email")
    val email: String,

    @SerializedName("phone")
    val phone: String,

    @SerializedName("website")
    val website: String
)
