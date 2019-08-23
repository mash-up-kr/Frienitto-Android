package com.mashup.frienitto.room.creation

import android.app.Application
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import com.mashup.frienitto.EditType
import com.mashup.frienitto.base.BaseAndroidViewModel
import com.mashup.frienitto.data.RequestCreateRoom
import com.mashup.frienitto.repository.room.RoomRepository
import com.mashup.frienitto.repository.user.UserRepository
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.Observables
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.BehaviorSubject
import io.reactivex.subjects.PublishSubject
import org.jetbrains.anko.AnkoLogger
import retrofit2.HttpException
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

class RoomCreationViewModel(private val userRepository: UserRepository, private val repository: RoomRepository, application: Application) : BaseAndroidViewModel(application), AnkoLogger {
    private val context = getApplication<Application>().applicationContext

    private val _submitName = MutableLiveData<String>()
    val submitName: LiveData<String>
        get() = _submitName

    private val _isEditable = MutableLiveData<Boolean>()
    val isEditable: LiveData<Boolean>
        get() = _isEditable

    private var expiredDate : String =""

    val roomNameSubject = BehaviorSubject.createDefault<String>("")
    val roomCodeSubject = BehaviorSubject.createDefault<String>("")
    val endDateSubject = BehaviorSubject.createDefault<Int>(-1)

    val submitBtnSubject = PublishSubject.create<Boolean>()

    private val _btnActive = MediatorLiveData<Boolean>()
    val btnActive: LiveData<Boolean>
        get() = _btnActive

    val isFinish = MutableLiveData<Boolean>()

    val requestToast = MutableLiveData<String>()

    init {
        _submitName.value = "방 생성하기"
        _isEditable.value = true

        addDisposable(
            Observables.combineLatest(roomNameSubject, roomCodeSubject, endDateSubject).subscribe {
                val name = it.first
                val code = it.second
                val end = it.third
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

        Log.d("csh", "userToken:" + userRepository.getUserInfo())
        Log.d("csh", "name: " + roomNameSubject.value!! + "  code: " + roomCodeSubject.value!!)
        showLoadingDialog()
        userRepository.getUserInfo()?.let {
            addDisposable(
                repository.createRoom(it.token, RequestCreateRoom(roomNameSubject.value!!, roomCodeSubject.value!!, expiredDate))
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe({ response ->

                        Log.d("csh Success", response?.msg)
                        Log.d("csh", "RoomID:" + response.data.id.toString())
                        if (showSubmitToast(response.code)) {
                            repository.roomId = response.data.id
                            isFinish.value = true
                        }
                        dismissLoadingDialog()
                    }, { except ->
                        if (except is HttpException)
                            showSubmitToast(except.code())
                        dismissLoadingDialog()
                        Log.d("csh Error", except.message)
                    })
            )
        }
    }

    fun onClickEndDate(endDateType: Int) {
        endDateSubject.onNext(endDateType)
        val cal = Calendar.getInstance()
        cal.time = Date()
        val df: DateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.KOREA)
        when(endDateType){
            1-> {
                cal.add(Calendar.DATE, 3)
                expiredDate= df.format(cal.time).toString()
            }
            2->{
                cal.add(Calendar.DATE,7)
                expiredDate= df.format(cal.time).toString()
            }

        }
    }

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

    private fun showSubmitToast(code: Int) : Boolean {
        when (code) {
            200 -> {
                requestToast.postValue("완료")
            }
            201 -> {
                requestToast.postValue("방 생성 완료")
            }
            401 -> {
                requestToast.postValue("인증되지 않은 사용자입니다.")
            }
            403 -> {
                requestToast.postValue("Forbidden")
            }
            404 -> {
                requestToast.postValue("등록된 방을 찾을 수 없습니다!")
            }
            405 -> {
                requestToast.postValue("방 생성 코드가 적합하지 않습니다.")
            }
            409 -> {
                requestToast.postValue("이미 존재하는 방 제목 입니다.")
            }
            else -> {
                requestToast.postValue("알 수 없는 오류! ($code)")
            }
        }

        return code / 100 == 2
    }

}
