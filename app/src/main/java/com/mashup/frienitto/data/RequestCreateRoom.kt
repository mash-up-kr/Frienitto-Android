package com.mashup.frienitto.data

import com.google.gson.annotations.SerializedName

data class RequestCreateRoom(
    @SerializedName("name")
    var name: String,
    @SerializedName("code")
    var code: String,
    @SerializedName("expires_date")
    var expiresDate: String
)