package com.mashup.frienitto.data

import com.google.gson.annotations.SerializedName

data class ResponseMatchingStart(
    @SerializedName("code")
    var code: Int,
    @SerializedName("msg")
    var msg: String,
    @SerializedName("data")
    var data: ResponseMatchingStartData
)

data class ResponseMatchingStartData(
    @SerializedName("room_id")
    var code: Int,
    @SerializedName("missions")
    var missions: List<Mission>
)

data class Mission(
    @SerializedName("id")
    var id: Int,
    @SerializedName("source_id")
    var sourceID: Int,
    @SerializedName("target_id")
    var targetID: Int,
    @SerializedName("description")
    var description: String,
    @SerializedName("status")
    var status: String,
    @SerializedName("type")
    var type: String
)