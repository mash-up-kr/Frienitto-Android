package com.mashup.frienitto.repository.room

import com.mashup.frienitto.api.DataSource
import com.mashup.frienitto.data.*
import io.reactivex.Single

class RoomRepository {

    fun createRoom(token: String, body: RequestCreateRoom) : Single<ResponseCreateRoom> {
        return DataSource.requestCreateRoom(token, body)
    }

    fun joinRoom(token: String, id: String) : Single<ResponseCreateRoom> {
        return DataSource.requestJoinRoom(token, id)
    }

    fun getRoomDetail(token: String, id: String) : Single<ResponseCreateRoom> {
        return DataSource.requestRoomDetail(token, id)
    }

    fun matchingStart(token: String, body: RequestMatchingStart) : Single<ResponseMatchingStart> {
        return DataSource.requestMatchingStart(token, body)
    }


}