package com.mashup.frienitto.matching.home

import androidx.databinding.ObservableField
import com.mashup.frienitto.base.BaseViewModel
import com.mashup.frienitto.repository.room.RoomRepository
import com.mashup.frienitto.repository.user.UserRepository
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.TimeUnit
import java.text.SimpleDateFormat
import java.util.*


class MatchingHomeViewModel(private val roomRepository: RoomRepository) : BaseViewModel() {
    val isManager = ObservableField<Boolean>(false)
    val remainText = ObservableField<String>()

    init {
        UserRepository.getUserToken()?.let {
            addDisposable(
                roomRepository.getMatchingInfo(it.token, roomRepository.getRoomId().toString())
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe({ response ->
                        val f = SimpleDateFormat("yyyy-MM-dd-HH-mm-ss", Locale.getDefault())
                        val d1 = f.parse("2019-08-10-00-00-00")
                        var now = System.currentTimeMillis()
                        var nowTime = f.format(Date(now))
                        var d2 = f.parse(nowTime)
                        var diff = d1.time - d2.time
                        remainText.set(convertSecondsToHMmSs(diff / 1000))
                        addDisposable(
                            Observable.interval(1, TimeUnit.SECONDS)
                                .map { o ->
                                    now = System.currentTimeMillis()
                                    nowTime = f.format(Date(now))
                                    d2 = f.parse(nowTime)
                                    diff = d1.time - d2.time
                                    convertSecondsToHMmSs(diff / 1000)
                                }
                                .subscribe { remainText.set(it) })
                    }, { except ->
                    })
            )
        }
    }

    private fun convertSecondsToHMmSs(seconds: Long): String {
        val s = seconds % 60
        val m = seconds / 60 % 60
        val h = seconds / (60 * 60) % 24
        val d = seconds / (60 * 60 * 24)
        return String.format("%d : %d : %02d : %02d", d, h, m, s)
    }
}