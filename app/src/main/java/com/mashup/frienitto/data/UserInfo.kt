package com.mashup.frienitto.data

import com.google.gson.annotations.SerializedName

data class UserInfo(
    @SerializedName("token")
    var token: String,
    @SerializedName("token_expires_date")
    var tokenExpiresDate: String,
    @SerializedName("user")
    var user: User
)
