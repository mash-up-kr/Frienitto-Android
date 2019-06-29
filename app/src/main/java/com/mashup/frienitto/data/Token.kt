package com.mashup.frienitto.data

import com.google.gson.annotations.SerializedName

data class Token(
    @SerializedName("token")
    var token: String,
    @SerializedName("token_expires_date")
    var tokenExpiresDate: String
)