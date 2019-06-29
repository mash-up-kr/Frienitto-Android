package com.mashup.frienitto.api

import com.mashup.frienitto.data.RequestEmailCode
import com.mashup.frienitto.data.ResponseEmailCode
import io.reactivex.Single
import retrofit2.http.Body
import retrofit2.http.POST

interface Service {

    @POST("api/v1/issue/code")
    fun requestEmailCode(@Body body: RequestEmailCode) : Single<ResponseEmailCode>

}