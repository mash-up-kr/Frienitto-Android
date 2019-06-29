package com.mashup.frienitto.data

import com.google.gson.annotations.SerializedName

data class UserPreview(
    @SerializedName("id")
    var id: Int,
    @SerializedName("username")
    var userName: String,
    @SerializedName("image_code")
    var imageCode: Int
)