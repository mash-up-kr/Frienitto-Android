package com.mashup.frienitto.data

import com.google.gson.annotations.SerializedName

data class RequestSignUp(
    @SerializedName("username")
    var userName: String,
    @SerializedName("description")
    var description: String,
    @SerializedName("image_code")
    var imageCode: Int,
    @SerializedName("email")
    var email: String,
    @SerializedName("password")
    var password: String
)