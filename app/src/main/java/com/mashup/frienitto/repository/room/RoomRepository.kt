package com.mashup.frienitto.repository.room

import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.util.Log
import com.mashup.frienitto.api.DataSource
import com.mashup.frienitto.data.*
import io.reactivex.Single

class RoomRepository {
    private val ROOM_ID_KEY = "roomid"
    private var roomId: Int? = null
    fun createRoom(token: String, body: RequestCreateRoom): Single<ResponseCreateRoom> {
        return DataSource.requestCreateRoom(token, body)
    }

    fun getJoinRoom(token: String, body: RequestJoinRoom): Single<ResponseRoomDetail> {
        return DataSource.requestJoinRoom(token, body)
    }

    fun getRoomDetail(token: String, id: String): Single<ResponseRoomDetail> {
        return DataSource.requestRoomDetail(token, id)
    }

    fun getRoomList(token: String): Single<ResponseRoomList> {
        return DataSource.requestRoomList(token)
    }

    fun matchingStart(token: String, body: RequestMatchingStart): Single<ResponseMatchingStart> {
        return DataSource.requestMatchingStart(token, body)
    }

    fun getMatchingInfo(token: String, id: String): Single<ResponseMatchingInfo> {
        return DataSource.requestMatchingInfo(token, id)
    }

    fun getRoomId(context: Context): Int? {
        return if (roomId == null) {
            val prefs = context.getSharedPreferences("PrefName", MODE_PRIVATE)
            if (prefs.contains(ROOM_ID_KEY)) {
                roomId = prefs.getInt(ROOM_ID_KEY, 0)
            }
            Log.d("loloss", "id : $roomId")
            return null
        } else {
            roomId
        }
    }

    fun getRoomId() = roomId

    fun setRoomId(context: Context, roomId: Int) {
        val prefs = context.getSharedPreferences("PrefName", MODE_PRIVATE)
        prefs.edit().putInt(ROOM_ID_KEY, roomId).apply()
        this.roomId = roomId
    }
}
