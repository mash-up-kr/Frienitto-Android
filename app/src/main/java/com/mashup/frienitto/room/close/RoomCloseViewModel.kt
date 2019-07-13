package com.mashup.frienitto.room.close

import androidx.lifecycle.MutableLiveData
import com.mashup.frienitto.base.BaseViewModel
import com.mashup.frienitto.data.UserPreview

class RoomCloseViewModel : BaseViewModel() {
    val data = MutableLiveData<List<UserPreview>>()
    init {
        val mockArray = ArrayList<UserPreview>()
        mockArray.add(UserPreview(0, "a", 1))
        mockArray.add(UserPreview(1, "b", 2))
        mockArray.add(UserPreview(2, "c", 3))
        mockArray.add(UserPreview(3, "f", 4))
        mockArray.add(UserPreview(4, "e", 5))
        mockArray.add(UserPreview(55, "q", 6))

        data.value = mockArray
    }
}