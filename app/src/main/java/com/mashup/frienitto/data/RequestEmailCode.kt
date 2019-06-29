package com.mashup.frienitto.data

import com.google.gson.annotations.SerializedName

data class RequestEmailCode(
    @SerializedName("receiver_info")
    var receiverInfo: String,
    @SerializedName("type")
    var type: String
)