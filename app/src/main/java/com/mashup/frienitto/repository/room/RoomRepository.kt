package com.mashup.frienitto.repository.room

import com.mashup.frienitto.api.DataSource
import com.mashup.frienitto.data.*
import io.reactivex.Single

class RoomRepository {

    fun createRoom(token: String, body: RequestCreateRoom) : Single<ResponseCreateRoom> {
        return DataSource.requestCreateRoom(token, body)
    }

    fun getJoinRoom(token: String, body: RequestJoinRoom) : Single<ResponseCreateRoom> {
        return DataSource.requestJoinRoom(token, body)
    }

    fun getRoomDetail(token: String, id: String) : Single<ResponseRoomDetail> {
        return DataSource.requestRoomDetail(token, id)
    }

    fun getRoomList(token: String) : Single<ResponseRoomList> {
        return DataSource.requestRoomList(token)
    }

    fun matchingStart(token: String, body: RequestMatchingStart) : Single<ResponseMatchingStart> {
        return DataSource.requestMatchingStart(token, body)
    }

    fun getMatchingInfo(token: String, id: String) : Single<ResponseMatchingInfo> {
        return DataSource.requestMatchingInfo(token, id)
    }



}