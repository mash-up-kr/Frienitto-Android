package com.mashup.frienitto.room.home

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
    val commonError = PublishSubject.create<Boolean>()

    init {
        showLoadingDialog()
        UserRepository.getUserInfo()?.let {
            addDisposable(
                roomRepository.getRoomDetail(it.token, roomRepository.getRoomId().toString())
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe({ response ->
                        dissmissLoadingDialog()
                        roomData.value = response.data
                        data.value = response.data.participant
                        isManager.set(response.data.isOwner)
                    }, { except ->
                        dissmissLoadingDialog()
                        commonError.onNext(true)
                    })
            )
        }
    }

    fun startMatching() {
        showLoadingDialog()
        UserRepository.getUserInfo()?.let {
            addDisposable(
                roomRepository.matchingStart(it.token, RequestMatchingStart(roomRepository.getRoomId()!!, 0, "ROOM"))
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe({
                        dissmissLoadingDialog()
                        startMatching.onNext(true)
                    }, {
                        dissmissLoadingDialog()
                        commonError.onNext(true)
                    })
            )
        }
    }
}