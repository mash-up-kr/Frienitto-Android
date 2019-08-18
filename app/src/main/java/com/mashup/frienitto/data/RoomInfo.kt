package com.mashup.frienitto.data

import com.google.gson.annotations.SerializedName

data class RoomInfo(
        @SerializedName("id")
        var id: Int,
        @SerializedName("title")
        var title: String,
        @SerializedName("expires_date")
        var expiresDate: String,
        @SerializedName("url")
        var url: String,
        @SerializedName("status")
        val status: String
)