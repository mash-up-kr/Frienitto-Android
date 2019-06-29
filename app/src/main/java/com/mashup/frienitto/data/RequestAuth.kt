package com.mashup.frienitto.data

import com.google.gson.annotations.SerializedName

data class RequestAuth(
    @SerializedName("receiver_info")
    var receiverName: String,
    @SerializedName("type")
    var type: String,
    @SerializedName("code")
    var code: String
)