package com.mashup.frienitto.matching.finish

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.mashup.frienitto.base.BaseViewModel
import com.mashup.frienitto.data.Mission
import com.mashup.frienitto.data.UserPreview
import com.mashup.frienitto.repository.room.RoomRepository
import com.mashup.frienitto.repository.user.UserRepository
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.PublishSubject
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit
import kotlin.collections.ArrayList

class MatchingFinishViewModel(private val userRepository: UserRepository, private val roomRepository: RoomRepository) : BaseViewModel() {
    val data = MutableLiveData<List<Mission>>()
    val commonError = PublishSubject.create<Boolean>()
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
                roomRepository.getMatchingInfo(it.token, roomRepository.roomId!!)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe({ response ->
                        data.value = response.data
                        dissmissLoadingDialog()
                        Log.d("csh Success", response.toString())
                    }, { except ->
                        Log.d("csh Error", except.toString())
                        dissmissLoadingDialog()
                        commonError.onNext(true)
                    })
            )
        }
    }


}