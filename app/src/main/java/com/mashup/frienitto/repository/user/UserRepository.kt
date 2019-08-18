package com.mashup.frienitto.repository.user

import com.mashup.frienitto.api.DataSource
import com.mashup.frienitto.data.*
import io.reactivex.Single
import android.content.Context
import android.content.Context.MODE_PRIVATE
import com.google.gson.Gson


object UserRepository {
    private const val LOGIN_TOKEN_KEY = "logintoken"
    private var emailToken: String = ""
    private var userInfo: UserInfo? = null

    // User
    fun signUp(token: String, body: RequestSignUp): Single<ResponseSignUp> {
        return DataSource.requestSignUp(token, body)
    }

    fun signIn(body: RequestSignIn): Single<ResponseSignIn> {
        return DataSource.requestSignIn(body)
    }


    // Email

    fun requestEmail(body: RequestEmailCode): Single<ResponseEmailCode> {
        return DataSource.requestEmailCode(body)
    }

    fun requestAuth(body: RequestAuth): Single<ResponseAuth> {
        return DataSource.requestAuth(body)
    }


    // UserInfo
    fun getTokenizer() = emailToken

    fun setTokenizer(token: String) {
        this.emailToken = token
    }

    fun getUserInfo(context: Context): UserInfo? {
        return if (userInfo == null) {
            val prefs = context.getSharedPreferences("PrefName", MODE_PRIVATE)
            val json = prefs.getString(LOGIN_TOKEN_KEY, null)
            return if (json == null) null else {
                userInfo = Gson().fromJson<Any>(json, UserInfo::class.java) as UserInfo
                userInfo
            }
        } else {
            userInfo
        }
    }

    fun getUserInfo() = userInfo

    fun setUserInfo(context: Context, userInfo: UserInfo) {
        val prefs = context.getSharedPreferences("PrefName", MODE_PRIVATE)
        val json = Gson().toJson(userInfo)
        prefs.edit().putString(LOGIN_TOKEN_KEY, json).apply()
        this.userInfo = userInfo
    }
}
