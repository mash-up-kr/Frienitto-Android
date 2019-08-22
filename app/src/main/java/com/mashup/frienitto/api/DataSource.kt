package com.mashup.frienitto.api

import com.mashup.frienitto.data.*
import io.reactivex.Single
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

object DataSource {

    private val service: Service
    private val baseURL: String

    init {
        //baseURL = "http://192.168.123.26:8080"
        baseURL = "https://codingsquid-side-project.com"

        val okHttpClient = OkHttpClient.Builder()
            .build()

        service = Retrofit.Builder()
            .client(okHttpClient)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(baseURL)
            .build()
            .create(Service::class.java)
    }


    // User
    fun requestSignUp(token: String, body: RequestSignUp) : Single<ResponseSignUp> {
        return service.requestSignUp(token, body)
    }

    fun requestSignIn(body: RequestSignIn) : Single<ResponseSignIn> {
        return service.requestSingIn(body)
    }


    // Room
    fun requestCreateRoom(token: String, body: RequestCreateRoom) : Single<ResponseCreateRoom> {
        return service.requestCreateRoom(token, body)
    }

    fun requestJoinRoom(token: String, body: RequestJoinRoom) : Single<ResponseRoomDetail> {
        return service.requestJoinRoom(token, body)
    }

    fun requestRoomDetail(token: String, id: String) : Single<ResponseRoomDetail> {
        return service.requestRoomDetail(token, id)
    }

    fun requestRoomList(token: String) : Single<ResponseRoomList> {
        return service.requestRoomList(token)
    }


    fun requestMatchingStart(token: String, body: RequestMatchingStart) : Single<ResponseMatchingStart> {
        return service.requestMatchingStart(token, body)
    }

    fun requestMatchingInfo(token: String, id: String) : Single<ResponseMatchingInfo> {
        return service.requestMatchingInfo(token, id)
    }




    // Email

    fun requestEmailCode(body: RequestEmailCode) : Single<ResponseEmailCode> {
        return service.requestEmailCode(body)
    }

    fun requestAuth(body: RequestAuth) : Single<ResponseAuth> {
        return service.requestAuth(body)
    }



}