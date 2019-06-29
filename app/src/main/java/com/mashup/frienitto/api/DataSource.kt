package com.mashup.frienitto.api

import com.mashup.frienitto.data.RequestEmailCode
import com.mashup.frienitto.data.ResponseEmailCode
import io.reactivex.Single
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

object DataSource {

    private val service: Service
    private val baseURL: String


    init {

        baseURL = "http://192.168.43.12:8080"

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

    fun requestEmailCode(request: RequestEmailCode) : Single<ResponseEmailCode> {
        return service.requestEmailCode(request)
    }


}