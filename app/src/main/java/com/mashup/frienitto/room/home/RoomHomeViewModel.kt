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

class RoomHomeViewModel(
    private val roomId: Int,
    private val userRepository: UserRepository,
    private val roomRepository: RoomRepository
) :
    BaseViewModel() {
    val data = MutableLiveData<List<UserPreview>>()
    val roomData = MutableLiveData<ResponseRoomDetailData>()
    val isManager = ObservableField<Boolean>(false)
    val startMatching = PublishSubject.create<Boolean>()
    val commonError = PublishSubject.create<Boolean>()

    init {
        showLoadingDialog()
        userRepository.getUserInfo()?.let {
            if (roomId == -1)
                return@let
            addDisposable(
                roomRepository.getRoomDetail(it.token, roomId)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe({ response ->
                        dissmissLoadingDialog()
                        Log.d("csh Success", response.toString())
                        roomData.value = response.data
                        data.value = response.data.participant
                        isManager.set(response.data.isOwner)
                    }, { except ->
                        Log.d("csh Error", except.toString())
                        dissmissLoadingDialog()
                        commonError.onNext(true)
                    })
            )
        }
    }

    fun startMatching() {
        showLoadingDialog()
        userRepository.getUserInfo()?.let {
            Log.d("lolo", it.token + "   " + roomData.value!!.id + "   " + it.user.id)
            addDisposable(
                roomRepository.matchingStart(it.token, RequestMatchingStart(roomData.value!!.id, "USER"))
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe({ response ->
                        Log.d("csh Success", response.toString())
                        dissmissLoadingDialog()
                        startMatching.onNext(true)
                    }, { except ->
                        Log.d("csh Error", except.toString())
                        dissmissLoadingDialog()
                        commonError.onNext(true)
                    })
            )
        }
    }
}