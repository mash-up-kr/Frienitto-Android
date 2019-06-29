package com.mashup.frienitto.data

import com.google.gson.annotations.SerializedName

data class ResponseSignUp(
    @SerializedName("code")
    var code: Int,
    @SerializedName("msg")
    var msg: String,
    @SerializedName("data")
    var data: ResponseSignUpData
)

data class ResponseSignUpData(
    @SerializedName("username")
    var userName: String,
    @SerializedName("description")
    var description: String,
    @SerializedName("image_code")
    var imageCode: Int,
    @SerializedName("email")
    var email: String
)