package com.mashup.frienitto.api

import com.mashup.frienitto.data.*
import io.reactivex.Single
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST

interface Service {

    // User
    @POST("api/v1/sign-up")
    fun requestSignUp(@Header("X-Register-Token") token: String,
                      @Body body: RequestSignUp) : Single<ResponseSignUp>

    @POST("api/v1/sign-in")
    fun requestSingIn(@Body body: RequestSignIn) : Single<ResponseSignIn>


    // Room


    // Email

    @POST("api/v1/issue/code")
    fun requestEmailCode(@Body body: RequestEmailCode) : Single<ResponseEmailCode>

    @POST("api/v1/verify/code")
    fun requestAuth(@Body body: RequestAuth) : Single<ResponseAuth>


}