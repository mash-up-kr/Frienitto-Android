package com.mashup.frienitto.login

import android.util.Log
import android.view.View
import androidx.lifecycle.MutableLiveData
import com.mashup.frienitto.base.BaseViewModel
import com.mashup.frienitto.data.RequestSignIn
import com.mashup.frienitto.repository.user.UserRepository
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class LoginViewModel : BaseViewModel() {
    val requestToast = MutableLiveData<String>()

    private var email: String = ""

    private var password: String = ""

    val isLogin = MutableLiveData<Boolean>()

    fun onEmailTextChange(email: CharSequence) {
        this.email = email.toString()
    }

    fun onPasswordTextChange(password: CharSequence) {
        this.password = password.toString()
    }

    fun onClickLoginButton(view: View) {
        if (email.isEmpty() || password.isEmpty()) {
            requestToast.value = "이메일 또는 패스워드를 적어주세요."
            return
        }
        addDisposable(
                UserRepository.signIn(RequestSignIn(email, password))
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe({ response ->
                            Log.d("csh Success", response.msg)
                            isLogin.value = true
                        }, { except ->
                            Log.d("csh Error", except.message.toString())
                        })
        )
    }
}