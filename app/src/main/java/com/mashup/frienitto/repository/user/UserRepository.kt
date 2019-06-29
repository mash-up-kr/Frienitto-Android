package com.mashup.frienitto.repository.user

import com.mashup.frienitto.api.DataSource
import com.mashup.frienitto.data.*
import io.reactivex.Single

object UserRepository {
    private var token: String = ""

    // User
    fun signUp(token: String, body: RequestSignUp) : Single<ResponseSignUp> {
        return DataSource.requestSignUp(token, body)
    }

    fun signIn(body: RequestSignIn): Single<ResponseSignIn> {
        return DataSource.requestSignIn(body)
    }


    // Email

    fun requestEmail(body: RequestEmailCode) : Single<ResponseEmailCode> {
        return DataSource.requestEmailCode(body)
    }

    fun requestAuth(body: RequestAuth) : Single<ResponseAuth> {
        return DataSource.requestAuth(body)
    }


    // Token
    fun getTokenizer() = token

    fun setTokenizer(token: String) {
        UserRepository.token = token
    }

}