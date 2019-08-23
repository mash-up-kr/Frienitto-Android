package com.mashup.frienitto.matching.home

import android.util.Log
import androidx.databinding.ObservableField
import androidx.lifecycle.MutableLiveData
import com.mashup.frienitto.base.BaseViewModel
import com.mashup.frienitto.data.Mission
import com.mashup.frienitto.repository.room.RoomRepository
import com.mashup.frienitto.repository.user.UserRepository
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.PublishSubject
import java.util.concurrent.TimeUnit
import java.text.SimpleDateFormat
import java.util.*


class MatchingHomeViewModel(private val userRepository: UserRepository, private val roomRepository: RoomRepository) :
    BaseViewModel() {
    val isManager = ObservableField<Boolean>(false)
    val dayText = ObservableField<String>()
    val hourText = ObservableField<String>()
    val minText = ObservableField<String>()
    val secText = ObservableField<String>()
    val commonError = PublishSubject.create<Boolean>()
    val missionData = MutableLiveData<Mission>()

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
                        Log.d("lolo", response.toString())
                        val simpleFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
                        val format = SimpleDateFormat("yyyy-MM-dd-HH-mm-ss", Locale.getDefault())
                        val d1 = simpleFormat.parse(roomRepository.expiredDate)
                        var now = System.currentTimeMillis()
                        var nowTime = format.format(Date(now))
                        var d2 = format.parse(nowTime)
                        var diff = d1.time - d2.time
                        if (diff > 0) {
                            convertSecondsToHMmSs(diff / 1000)
                            addDisposable(
                                Observable.interval(1, TimeUnit.SECONDS)
                                    .map { o ->
                                        now = System.currentTimeMillis()
                                        nowTime = format.format(Date(now))
                                        d2 = format.parse(nowTime)
                                        diff = d1.time - d2.time
                                    }
                                    .subscribe { convertSecondsToHMmSs(diff / 1000) })
                        } else {
                            convertSecondsToHMmSs(0);
                        }
                        missionData.value = response.data.first { data -> data.fromUserInfo.id == it.user.id }
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

    private fun convertSecondsToHMmSs(seconds: Long) {
        val s = seconds % 60
        val m = seconds / 60 % 60
        val h = seconds / (60 * 60) % 24
        val d = seconds / (60 * 60 * 24)
        dayText.set(d.toString())
        hourText.set(h.toString())
        minText.set(String.format("%02d", m))
        secText.set(String.format("%02d", s))
    }
}
