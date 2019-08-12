package com.mashup.frienitto.api

import com.mashup.frienitto.data.*
import io.reactivex.Single
import retrofit2.http.*

interface Service {

    // User
    @POST("api/v1/sign-up")
    fun requestSignUp(@Header("X-Register-Token") token: String,
                      @Body body: RequestSignUp) : Single<ResponseSignUp>

    @POST("api/v1/sign-in")
    fun requestSingIn(@Body body: RequestSignIn) : Single<ResponseSignIn>


    // Room
    @POST("api/v1/register/room")
    fun requestCreateRoom(@Header("X-Authorization") token: String,
                          @Body body: RequestCreateRoom) : Single<ResponseCreateRoom>

    @POST("api/v1/join/room")
    fun requestJoinRoom(@Header("X-Authorization") token: String,
                        @Body body: RequestJoinRoom) : Single<ResponseRoomDetail>

    @GET("api/v1/room/{id}")
    fun requestRoomDetail(@Header("X-Authorization") token: String,
                          @Path("id") id: String) : Single<ResponseRoomDetail>

    @GET("api/v1/room/list")
    fun requestRoomList(@Header("X-Authorization") token: String) : Single<ResponseRoomList>


    @POST("api/v1/matching")
    fun requestMatchingStart(@Header("X-Authorization") token: String,
                             @Body body: RequestMatchingStart) : Single<ResponseMatchingStart>


    @GET("api/v1/matching-info/room/{id}")
    fun requestMatchingInfo(@Header("X-Authorization") token: String,
                          @Path("id") id: String) : Single<ResponseMatchingInfo>


    // Email

    @POST("api/v1/issue/code")
    fun requestEmailCode(@Body body: RequestEmailCode) : Single<ResponseEmailCode>

    @POST("api/v1/verify/code")
    fun requestAuth(@Body body: RequestAuth) : Single<ResponseAuth>


}