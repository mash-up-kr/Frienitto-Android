package com.mashup.frienitto.api

import android.util.Log
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
        baseURL = "http://192.168.123.26:8080"

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
        Log.d("csh", "token:"+token+"body:"+body.toString())
        return service.requestSignUp(token, body)
    }

    fun requestSignIn(body: RequestSignIn) : Single<ResponseSignIn> {
        return service.requestSingIn(body)
    }

    // Room



    // Email

    fun requestEmailCode(body: RequestEmailCode) : Single<ResponseEmailCode> {
        return service.requestEmailCode(body)
    }

    fun requestAuth(body: RequestAuth) : Single<ResponseAuth> {
        return service.requestAuth(body)
    }



}