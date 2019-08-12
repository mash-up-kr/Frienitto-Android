package com.mashup.frienitto.room.join

import android.app.Application
import android.util.Log
import com.mashup.frienitto.EditType
import com.mashup.frienitto.R
import com.mashup.frienitto.base.BaseAndroidViewModel
import com.mashup.frienitto.base.BaseViewModel
import com.mashup.frienitto.data.RequestJoinRoom
import com.mashup.frienitto.repository.room.RoomRepository
import com.mashup.frienitto.repository.user.UserRepository
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.BehaviorSubject
import io.reactivex.subjects.PublishSubject

class RoomJoinViewModel(private val repository: RoomRepository, application: Application) : BaseAndroidViewModel(application) {
    private val context = getApplication<Application>().applicationContext

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

//            repository.getJoinRoom("tooooken", roomCodeSubject.value.toString())
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe({ response ->
//                    Log.d("csh Success", response?.msg)
//                    if(response.msg=="방입장이성공한경우")
//                        moveActivity.onNext(true)
//                    else if(response.msg=="뭔가튕겨나갔을경우")
//                        commonError.onNext(R.string.fail_entry_room)
//                }, { except ->
//                    Log.d("csh Error", except.message)
//                    commonError.onNext(R.string.error_unkown)
//                })
        UserRepository.getUserToken()?.let {
            addDisposable(
                repository.getJoinRoom(
                    it.token,
                    RequestJoinRoom(roomNameSubject.value.toString(), roomCodeSubject.value.toString())
                )
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe({ response ->
                        Log.d("csh Success", response?.msg)
                        if (response.code == 200) {
                            repository.setRoomId(context, response.data.id)
                            //Todo 매칭 유무 확인 후 값을 넣어야함
                            moveActivity.onNext(true)
                        }
                        else
                            commonError.onNext(R.string.fail_join_room)
                    }, { except ->
                        Log.d("csh Error", except.message)
                        commonError.onNext(R.string.error_unkown)
                    })
            )
        }
    }
}