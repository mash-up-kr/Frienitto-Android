package com.mashup.frienitto.room.home

import android.util.Log
import androidx.databinding.ObservableField
import androidx.lifecycle.MutableLiveData
import com.mashup.frienitto.base.BaseViewModel
import com.mashup.frienitto.data.RequestDeleteRoom
import com.mashup.frienitto.data.RequestMatchingStart
import com.mashup.frienitto.data.ResponseRoomDetailData
import com.mashup.frienitto.data.UserPreview
import com.mashup.frienitto.repository.room.RoomRepository
import com.mashup.frienitto.repository.user.UserRepository
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.PublishSubject
import retrofit2.HttpException

class RoomHomeViewModel(
    private val userRepository: UserRepository,
    private val roomRepository: RoomRepository
) :
    BaseViewModel() {
    val data = MutableLiveData<List<UserPreview>>()
    val roomData = MutableLiveData<ResponseRoomDetailData>()
    val isManager = ObservableField<Boolean>(false)
    val startMatching = PublishSubject.create<Boolean>()
    val commonError = PublishSubject.create<Boolean>()
    val deleteRoom = PublishSubject.create<Boolean>()
    val requestToast = MutableLiveData<String>()

    init {
        showLoadingDialog()
        userRepository.getUserInfo()?.let {
            if (roomRepository.roomId == null) {
                dissmissLoadingDialog()
                commonError.onNext(false)
                return@let
            }
            addDisposable(
                roomRepository.getRoomDetail(it.token, roomRepository.roomId!!)
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
                        if (showStartMatchToast(response.code)) {

                        }
                        dissmissLoadingDialog()
                        startMatching.onNext(true)
                    }, { except ->
                        Log.d("csh Error", except.toString())
                        if (except is HttpException)
                            showStartMatchToast(except.code())
                        dissmissLoadingDialog()
                        commonError.onNext(true)
                    })
            )
        }
    }

    fun exitRoom() {
        userRepository.getUserInfo()?.let {
            if (roomRepository.roomTitle == null) {
                dissmissLoadingDialog()
                commonError.onNext(false)
                return@let
            }
            addDisposable(
                    roomRepository.exitRoom(it.token, RequestDeleteRoom(roomRepository.roomTitle!!))
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe({
                                Log.d("csh Success", "success")
                                dissmissLoadingDialog()
                                deleteRoom.onNext(true)
                            }, {
                                Log.d("csh Error", "error")
                                dissmissLoadingDialog()
                                commonError.onNext(true)
                            })
            )
        }
    }

    private fun showStartMatchToast(code: Int) : Boolean {
        when (code) {
            200 -> {
                requestToast.postValue("완료")
            }
            201 -> {
                requestToast.postValue("Created")
            }
            400 -> {
                requestToast.postValue("매칭을 시작하려면 매칭 인원이 2명 이상이어야 합니다")
            }
            401 -> {
                requestToast.postValue("방장이 아니거나 토큰 값이 유효하지 않습니다.")
            }
            403 -> {
                requestToast.postValue("Forbidden")
            }
            404 -> {
                requestToast.postValue("Not Found")
            }
            5003 -> {
                requestToast.postValue("해당 요청에서 미션 타입이 적절하지 않습니다.")
            }
            else -> {
                requestToast.postValue("알 수 없는 오류! ($code)")
            }
        }

        return code / 100 == 2
    }
}