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
    @SerializedName("room_status")
    var roomStatus: String,
    @SerializedName("missions")
    var missions: List<Mission>
)

data class Mission(
    @SerializedName("description")
    var description: String,
    @SerializedName("from")
    var fromUserInfo: MatchingUserInfo,
    @SerializedName("id")
    var id: Int,
    @SerializedName("status")
    var status: String,
    @SerializedName("to")
    var toUserInfo: MatchingUserInfo,
    @SerializedName("type")
    var type: String
)

data class MatchingUserInfo(
    @SerializedName("description")
    var description: String,
    @SerializedName("id")
    var id: Int,
    @SerializedName("image_code")
    var imageCode: Int,
    @SerializedName("username")
    var username: String
)