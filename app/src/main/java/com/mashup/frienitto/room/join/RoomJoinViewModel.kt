package com.mashup.frienitto.room.join

import android.app.Application
import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.mashup.frienitto.EditType
import com.mashup.frienitto.R
import com.mashup.frienitto.base.BaseAndroidViewModel
import com.mashup.frienitto.data.RequestJoinRoom
import com.mashup.frienitto.repository.room.RoomRepository
import com.mashup.frienitto.repository.user.UserRepository
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.BehaviorSubject
import io.reactivex.subjects.PublishSubject
import retrofit2.HttpException

class RoomJoinViewModel(
    private val userRepository: UserRepository,
    private val repository: RoomRepository,
    application: Application
) : BaseAndroidViewModel(application) {

    private val context = getApplication<Application>().applicationContext

    val moveActivity = PublishSubject.create<Boolean>()
    val roomNameSubject = BehaviorSubject.createDefault<String>("")
    val roomCodeSubject = BehaviorSubject.createDefault<String>("")

    val commonError = PublishSubject.create<Int>()
    val requestToast = MutableLiveData<String>()

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
        showLoadingDialog()
        userRepository.getUserInfo()?.let {
            addDisposable(
                repository.getJoinRoom(
                    it.token,
                    RequestJoinRoom(roomNameSubject.value.toString(), roomCodeSubject.value.toString())
                )
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe({ response ->
                        Log.d("csh Success", response?.msg)
                        dismissLoadingDialog()
                        if (showJoinRoomToast(response.code)) {
                            repository.roomId = response.data.id
                            //Todo 매칭 유무 확인 후 값을 넣어야함
                            moveActivity.onNext(true)
                        }
                    }, { except ->
                        Log.d("csh Error", except.message)
                        if (except is HttpException)
                            showJoinRoomToast(except.code())
                        dismissLoadingDialog()
                        commonError.onNext(R.string.error_unkown)
                    })
            )
        }
    }

    private fun showJoinRoomToast(code: Int) : Boolean {
        when (code) {
            200 -> {
                requestToast.postValue("완료")
            }
            201 -> {
                requestToast.postValue("Created")
            }
            401 -> {
                requestToast.postValue("인증 되지 않은 사용자입니다.")
            }
            403 -> {
                requestToast.postValue("Forbidden")
            }
            404 -> {
                requestToast.postValue("요청하신 방을 찾을 수가 없습니다.")
            }
            405 -> {
                requestToast.postValue("방 생성 코드가 적합하지 않습니")
            }
            409 -> {
                requestToast.postValue("입장 가능한 시간이 아닙니다.")
            }
            else -> {
                requestToast.postValue("알 수 없는 오류")
            }

        }

        return code / 100 == 2
    }
}