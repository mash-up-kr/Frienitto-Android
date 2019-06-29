package com.mashup.frienitto.data

import com.google.gson.annotations.SerializedName

data class RequestMatchingStart(
    @SerializedName("room_id")
    var roomID: String,
    @SerializedName("participant_id")
    var participantID: List<Int>
)