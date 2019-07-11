package com.mashup.frienitto.room.entry

import android.annotation.SuppressLint
import android.util.Log
import com.mashup.frienitto.EditType
import com.mashup.frienitto.R
import com.mashup.frienitto.base.BaseViewModel
import com.mashup.frienitto.repository.room.RoomRepository
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.BehaviorSubject
import io.reactivex.subjects.PublishSubject

class RoomEntryViewModel(private val repository: RoomRepository) : BaseViewModel() {

    val moveActivity = PublishSubject.create<Boolean>()
    val roomNameSubject = BehaviorSubject.createDefault<String>("")
    val roomCodeSubject = BehaviorSubject.createDefault<String>("")

    val commonError = PublishSubject.create<Int>()

    fun onDeleteContent(editType: EditType) {
        when (editType) {
            EditType.ROOM_NAME -> {
                roomNameSubject.onNext("")

            }
            EditType.ROOM_PW -> {
                roomCodeSubject.onNext("")
            }
            else -> {
            }
        }

    }

    fun onTextChange(editType: EditType, text: CharSequence) {
        when (editType) {
            EditType.ROOM_NAME -> {
                roomNameSubject.onNext(text.toString())
            }
            EditType.ROOM_PW -> {
                roomCodeSubject.onNext(text.toString())
            }
            else -> {
            }
        }
    }


    fun onSubmit() {
        addDisposable(
            repository.joinRoom("tooooken", roomCodeSubject.value.toString())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ response ->
                    Log.d("csh Success", response?.msg)
                    if(response.msg=="방입장이성공한경우")
                        moveActivity.onNext(true)
                    else if(response.msg=="뭔가튕겨나갔을경우")
                        commonError.onNext(R.string.fail_entry_room)
                }, { except ->
                    Log.d("csh Error", except.message)
                    commonError.onNext(R.string.error_unkown)
                })
        )
    }
}