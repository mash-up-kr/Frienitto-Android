package com.mashup.frienitto.data

import com.google.gson.annotations.SerializedName

data class ResponseMatchingInfo(
    @SerializedName("code")
    var code: Int,
    @SerializedName("msg")
    var msg: String,
    @SerializedName("data")
    var data: ResponseMatchingInfoData
)

data class ResponseMatchingInfoData(
    @SerializedName("missions")
    var missions: List<Mission>,
    @SerializedName("room_id")
    var roomId: Int,
    @SerializedName("room_status")
    var roomStatus: String
)
