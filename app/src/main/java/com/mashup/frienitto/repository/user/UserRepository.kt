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
    private var userToken: Token? = null

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


    // Token
    fun getTokenizer() = emailToken

    fun setTokenizer(token: String) {
        this.emailToken = token
    }

    fun getUserToken(context: Context): Token? {
        return if (userToken == null) {
            val prefs = context.getSharedPreferences("PrefName", MODE_PRIVATE)
            val json = prefs.getString(LOGIN_TOKEN_KEY, null)
            return if (json == null) null else {
                userToken = Gson().fromJson<Any>(json, Token::class.java) as Token
                userToken
            }
        } else {
            userToken
        }
    }

    fun getUserToken() = userToken

    fun setUserToken(context: Context, token: Token) {
        val prefs = context.getSharedPreferences("PrefName", MODE_PRIVATE)
        val json = Gson().toJson(token)
        prefs.edit().putString(LOGIN_TOKEN_KEY, json).apply()
        this.userToken = token
    }
}
