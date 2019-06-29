package com.mashup.frienitto.data

import com.google.gson.annotations.SerializedName

data class ResponseRoom(
    @SerializedName("id")
    var id: Int,
    @SerializedName("title")
    var title: String,
    @SerializedName("expires_date")
    var expiresDate: String,
    @SerializedName("url")
    var url: String,
    @SerializedName("participant")
    var participant: List<UserPreview>

)