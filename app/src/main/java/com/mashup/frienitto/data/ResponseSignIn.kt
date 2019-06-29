package com.mashup.frienitto.data

import com.google.gson.annotations.SerializedName

data class ResponseSignIn(
    @SerializedName("code")
    var code: Integer,
    @SerializedName("msg")
    var msg: String,
    @SerializedName("data")
    var data: Token
)