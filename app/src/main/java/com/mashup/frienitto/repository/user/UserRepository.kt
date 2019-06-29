package com.mashup.frienitto.repository.user

import com.mashup.frienitto.api.DataSource
import com.mashup.frienitto.data.RequestEmailCode
import com.mashup.frienitto.data.ResponseEmailCode
import io.reactivex.Single

object UserRepository {

    fun requestEmail(body: RequestEmailCode) : Single<ResponseEmailCode> {
        return DataSource.requestEmailCode(body)
    }

}