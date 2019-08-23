package com.mashup.frienitto.login

import android.app.Application
import android.util.Log
import android.view.View
import androidx.databinding.ObservableField
import androidx.lifecycle.MutableLiveData
import com.mashup.frienitto.base.BaseAndroidViewModel
import com.mashup.frienitto.data.RequestSignIn
import com.mashup.frienitto.repository.user.UserRepository
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.Observables
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.BehaviorSubject
import retrofit2.HttpException

class LoginViewModel(private val userRepository: UserRepository, application: Application) :
    BaseAndroidViewModel(application) {
    private val context = getApplication<Application>().applicationContext
    val requestToast = MutableLiveData<String>()

    val email = BehaviorSubject.createDefault<String>("")
    val password = BehaviorSubject.createDefault<String>("")

    val isLogin = MutableLiveData<Boolean>()

    val isWriteAllData = ObservableField<Boolean>().apply { set(false) }

    init {
        addDisposable(
            Observables.combineLatest(email, password).subscribe {
                val email = it.first
                val password = it.second
                if (email.isNotBlank() && password.isNotBlank()) {
                    //버튼을 활성화하고 버튼 색상을 변경
                    isWriteAllData.set(true)
                } else {
                    isWriteAllData.set(false)
                }

            }
        )
    }

    fun onEmailTextChange(email: CharSequence) {
        this.email.onNext(email.toString())
    }

    fun onPasswordTextChange(password: CharSequence) {
        this.password.onNext(password.toString())
    }

    fun onClickLoginButton(view: View) {
        showLoadingDialog()
        addDisposable(
            userRepository.signIn(RequestSignIn(email.value!!, password.value!!))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ response ->
                    Log.d("Success", response.msg)
                    if (showLoginToast(response.code)) {
                        userRepository.setUserInfo(context, response.data)
                        isLogin.value = true
                    }

                    dismissLoadingDialog()
                }, { except ->
                    Log.d("Error", except.message.toString())
                    if (except is HttpException)
                        showLoginToast(except.code())
                    dismissLoadingDialog()
                })
        )
    }

    fun showLoginToast(code: Int) : Boolean {
        when (code) {
            200 -> {
                requestToast.postValue("완료")
            }
            201 -> {
                requestToast.postValue("Created")
            }
            401 -> {
                requestToast.postValue("비밀번호가 틀렸습니다!")
            }
            403 -> {
                requestToast.postValue("Forbidden")
            }
            404 -> {
                requestToast.postValue("이메일을 찾을 수 없습니다!")
            }

        }
        return code / 100 == 2
    }
}