package com.mashup.frienitto.data

import com.google.gson.annotations.SerializedName

data class ResponseRoomDetail(
    @SerializedName("code")
    var code: Int,
    @SerializedName("msg")
    var msg: String,
    @SerializedName("data")
    var data: ResponseRoomDetailData
)

data class ResponseRoomDetailData(
    @SerializedName("id")
    var id: Int,
    @SerializedName("title")
    var title: String,
    @SerializedName("expires_date")
    var expiresDate: String,
    @SerializedName("url")
    var url: String,
    @SerializedName("status")
    val status: String,
    @SerializedName("participant")
    var participant: List<UserPreview>

)