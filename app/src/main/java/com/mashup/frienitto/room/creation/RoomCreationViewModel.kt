package com.mashup.frienitto.room.creation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import com.mashup.frienitto.EditType
import com.mashup.frienitto.base.BaseViewModel
import io.reactivex.rxkotlin.Observables
import io.reactivex.subjects.BehaviorSubject
import io.reactivex.subjects.PublishSubject
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.info

class RoomCreationViewModel : BaseViewModel(), AnkoLogger {

    private val _submitName = MutableLiveData<String>()
    val submitName: LiveData<String>
        get() = _submitName


    private val _commonError = MutableLiveData<Int>()
    val commonError: LiveData<Int>
        get() = _commonError

    private val _isEditable = MutableLiveData<Boolean>()
    val isEditable: LiveData<Boolean>
        get() = _isEditable


    private val roomName = BehaviorSubject.createDefault<String>("")
    private val roomCode = BehaviorSubject.createDefault<String>("")
    private val endDate = BehaviorSubject.createDefault<Int>(-1)

    val endDateSubject = PublishSubject.create<Int>()
    val submitBtnSubject = PublishSubject.create<Boolean>()

    private val _btnActive = MediatorLiveData<Boolean>()
    val btnActive: LiveData<Boolean>
        get() = _btnActive



    init {
        _submitName.value = "방 생성하기"
        _isEditable.value = true

        addDisposable(
            Observables.combineLatest(roomName, roomCode, endDate).subscribe {
                val name = it.first
                val code = it.second
                val end = it.third
                info { "tag1 combine $it" }
                if(name.isNotBlank()&&code.isNotBlank()&&end!=-1){
                    //버튼을 활성화하고 버튼 색상을 변경
                    info { "tag1 " }
                    submitBtnSubject.onNext(true)
                }else{
                    submitBtnSubject.onNext(false)
                }

            }
        )


    }


    fun onTextChange(editType:EditType , text: CharSequence) {
        when (editType) {
            EditType.ROOM_NAME -> roomName.onNext(text.toString())
            EditType.ROOM_PW ->  roomCode.onNext(text.toString())
            else -> {

            }

        }
    }

    fun onSubmit() {
        info { "tag1 onSubmit" }
        //db


    }

    fun onClickEndDate(endDateType:Int){
        endDate.onNext(endDateType)
        endDateSubject.onNext(endDateType)
    }
//    private fun isNotBlanks(): Boolean {
//        return roomName.isNotBlank()&&roomCode.isNotBlank()
//    }


}

data class RoomCreation(
    val name: String,
    val code: String,
    val expires_date: String
)