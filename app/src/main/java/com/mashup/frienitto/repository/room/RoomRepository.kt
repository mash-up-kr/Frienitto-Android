package com.mashup.frienitto.repository.room

import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.util.Log
import com.mashup.frienitto.api.DataSource
import com.mashup.frienitto.data.*
import io.reactivex.Single

class RoomRepository {
    private val ROOM_ID_KEY = "roomid"
    var roomId: Int? = null
    var expiredDate: String? = null

    fun createRoom(token: String, body: RequestCreateRoom): Single<ResponseCreateRoom> {
        return DataSource.requestCreateRoom(token, body)
    }

    fun getJoinRoom(token: String, body: RequestJoinRoom): Single<ResponseRoomDetail> {
        return DataSource.requestJoinRoom(token, body)
    }

    fun getRoomDetail(token: String, id: Int): Single<ResponseRoomDetail> {
        return DataSource.requestRoomDetail(token, id)
    }

    fun getRoomList(token: String): Single<ResponseRoomList> {
        return DataSource.requestRoomList(token)
    }

    fun matchingStart(token: String, body: RequestMatchingStart): Single<ResponseMatchingStart> {
        return DataSource.requestMatchingStart(token, body)
    }

    fun getMatchingInfo(token: String, id: Int): Single<ResponseMatchingInfo> {
        return DataSource.requestMatchingInfo(token, id)
    }
}
