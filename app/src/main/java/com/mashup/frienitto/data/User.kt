package com.mashup.frienitto.data

import com.google.gson.annotations.SerializedName

data class User(
    @SerializedName("description")
    var description: String,
    @SerializedName("email")
    var email: String,
    @SerializedName("id")
    var id: Int,
    @SerializedName("image_code")
    var image_code: Int,
    @SerializedName("nickname")
    var nickname: String,
    @SerializedName("username")
    var username: String
)
