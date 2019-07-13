package com.mashup.frienitto.data

import com.google.gson.annotations.SerializedName

data class RequestJoinRoom(
    @SerializedName("title")
    var title: String,
    @SerializedName("code")
    var code: String
)