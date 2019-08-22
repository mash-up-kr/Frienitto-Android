package com.mashup.frienitto.data

import com.google.gson.annotations.SerializedName

data class RequestMatchingStart(
    /*
    @SerializedName("room_id")
    var roomID: Int,
    @SerializedName("USER")
    var type: String,
    @SerializedName("participant_id")
    var participantID: List<Int>*/
    @SerializedName("room_id")
    var roomID: Int,
    @SerializedName("type")
    var type: String
)