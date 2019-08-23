package com.mashup.frienitto.data

import com.google.gson.annotations.SerializedName

data class ResponseAuth(
    @SerializedName("code")
    var code: Int,
    @SerializedName("msg")
    var msg: String,
    @SerializedName("data")
    var data: RegisterToken
)

data class RegisterToken(
    @SerializedName("register_token")
    var registerToken: String
)