package com.mashup.frienitto.room.home

import android.util.Log
import androidx.databinding.ObservableField
import androidx.lifecycle.MutableLiveData
import com.mashup.frienitto.base.BaseViewModel
import com.mashup.frienitto.data.RequestMatchingStart
import com.mashup.frienitto.data.ResponseRoomDetailData
import com.mashup.frienitto.data.UserPreview
import com.mashup.frienitto.repository.room.RoomRepository
import com.mashup.frienitto.repository.user.UserRepository
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.PublishSubject

class RoomHomeViewModel(private val roomRepository: RoomRepository) : BaseViewModel() {
    val data = MutableLiveData<List<UserPreview>>()
    val roomData = MutableLiveData<ResponseRoomDetailData>()
    val isManager = ObservableField<Boolean>(false)
    val startMatching  = PublishSubject.create<Boolean>()

    init {
        UserRepository.getUserToken()?.let {
            addDisposable(
                roomRepository.getRoomDetail(it.token, roomRepository.getRoomId().toString())
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe({ response ->
                        roomData.value = response.data
                        data.value = response.data.participant
                        isManager.set(response.data.isOwner)
                    }, { except ->
                    })
            )
        }
    }

    fun startMatching() {
        UserRepository.getUserToken()?.let {
            addDisposable(
                roomRepository.matchingStart(it.token, RequestMatchingStart(roomRepository.getRoomId()!!, 0, "ROOM"))
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe({
                        startMatching.onNext(true)
                    }, {
                    })
            )
        }
    }
}