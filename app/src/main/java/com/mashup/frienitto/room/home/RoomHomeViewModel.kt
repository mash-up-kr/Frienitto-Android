package com.mashup.frienitto.room.home

import androidx.databinding.ObservableField
import androidx.lifecycle.MutableLiveData
import com.mashup.frienitto.base.BaseViewModel
import com.mashup.frienitto.data.ResponseRoomDetailData
import com.mashup.frienitto.data.UserPreview
import com.mashup.frienitto.repository.room.RoomRepository
import com.mashup.frienitto.repository.user.UserRepository
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class RoomHomeViewModel(private val roomRepository: RoomRepository) : BaseViewModel() {
    val data = MutableLiveData<List<UserPreview>>()
    val roomData = MutableLiveData<ResponseRoomDetailData>()
    val isManager = ObservableField<Boolean>(false)

    init {
        UserRepository.getUserToken()?.let {
            addDisposable(
                roomRepository.getRoomDetail(it.token, "5")
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe({ response ->
                        roomData.value = response.data
                        data.value = response.data.participant
                    }, { except ->
                    })
            )
        }
    }
}