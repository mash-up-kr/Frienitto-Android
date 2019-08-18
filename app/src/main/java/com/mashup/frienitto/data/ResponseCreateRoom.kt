package com.mashup.frienitto.data

import com.google.gson.annotations.SerializedName

data class ResponseCreateRoom(
    @SerializedName("code")
    var code: Int,
    @SerializedName("msg")
    var msg: String,
    @SerializedName("data")
    var data: RoomInfo
)

