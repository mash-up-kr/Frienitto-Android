package com.mashup.frienitto.data

import com.google.gson.annotations.SerializedName

data class RequestDeleteRoom(
        @SerializedName("title")
        var title: String
)
