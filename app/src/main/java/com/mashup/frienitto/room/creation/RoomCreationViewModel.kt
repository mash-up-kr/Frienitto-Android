package com.mashup.frienitto.room.creation

import androidx.databinding.ObservableField
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import com.mashup.frienitto.EditType
import com.mashup.frienitto.base.BaseViewModel
import io.reactivex.internal.operators.observable.ObservableFilter
import io.reactivex.rxkotlin.Observables
import io.reactivex.subjects.BehaviorSubject
import io.reactivex.subjects.PublishSubject
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.info

class RoomCreationViewModel : BaseViewModel(), AnkoLogger {

    private val _submitName = MutableLiveData<String>()
    val submitName: LiveData<String>
        get() = _submitName


    private val _roomName = MutableLiveData<String>()
    val roomName: LiveData<String>
        get() = _roomName

    private val _roomCode = MutableLiveData<String>()
    val roomCode: LiveData<String>
        get() = _roomCode


    private val _commonError = MutableLiveData<Int>()
    val commonError: LiveData<Int>
        get() = _commonError

    private val _isEditable = MutableLiveData<Boolean>()
    val isEditable: LiveData<Boolean>
        get() = _isEditable


     val roomNameSubject = BehaviorSubject.createDefault<String>("")
     val roomCodeSubject = BehaviorSubject.createDefault<String>("")
    val endDateSubject = BehaviorSubject.createDefault<Int>(-1)

    val submitBtnSubject = PublishSubject.create<Boolean>()

    private val _btnActive = MediatorLiveData<Boolean>()
    val btnActive: LiveData<Boolean>
        get() = _btnActive


    init {
        _submitName.value = "방 생성하기"
        _isEditable.value = true

        addDisposable(
            Observables.combineLatest(roomNameSubject, roomCodeSubject, endDateSubject).subscribe {
                val name = it.first
                val code = it.second
                val end = it.third
                info { "tag1 combine $it" }
                if (name.isNotBlank() && code.isNotBlank() && end != -1) {
                    //버튼을 활성화하고 버튼 색상을 변경
                    submitBtnSubject.onNext(true)
                } else {
                    submitBtnSubject.onNext(false)
                }

            }
        )


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
        info { "tag1 onSubmit" }
        //db

    }

    fun onClickEndDate(endDateType: Int) {
        endDateSubject.onNext(endDateType)
    }

    fun onDeleteContent(editType: EditType) {
        when (editType) {
            EditType.ROOM_NAME -> {
                roomNameSubject.onNext("")

            }
            EditType.ROOM_PW -> {
                roomCodeSubject.onNext("")
            }
            else ->{}
        }

    }

}

data class RoomCreation(
    val name: String,
    val code: String,
    val expires_date: String
)