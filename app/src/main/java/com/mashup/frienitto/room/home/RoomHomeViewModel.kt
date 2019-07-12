package com.mashup.frienitto.room.home

import androidx.databinding.ObservableField
import androidx.lifecycle.MutableLiveData
import com.mashup.frienitto.base.BaseViewModel
import com.mashup.frienitto.data.UserPreview

class RoomHomeViewModel : BaseViewModel() {
    val data = MutableLiveData<List<UserPreview>>()
    val isManager = ObservableField<Boolean>(false)

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