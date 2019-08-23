package com.mashup.frienitto.data

import com.google.gson.annotations.SerializedName

data class RequestSignIn(
    @SerializedName("email")
    var email: String,
    @SerializedName("password")
    var password: String
)