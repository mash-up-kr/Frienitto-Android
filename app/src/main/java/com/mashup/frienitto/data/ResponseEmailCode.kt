package com.mashup.frienitto.data

import com.google.gson.annotations.SerializedName

data class ResponseEmailCode(
    @SerializedName("code")
    var code: Int,
    @SerializedName("msg")
    var msg: String
)