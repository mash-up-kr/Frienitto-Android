package com.mashup.frienitto.login

import android.view.View
import androidx.lifecycle.MutableLiveData
import com.mashup.frienitto.base.BaseViewModel

class LoginViewModel : BaseViewModel() {
    val requestToast = MutableLiveData<String>()

    private var email: String = ""

    private var password: String = ""

    fun onEmailTextChange(email: CharSequence) {
        this.email = email.toString()
    }

    fun onPasswordTextChange(password: CharSequence) {
        this.password = password.toString()
    }

    fun onClickLoginButton(view: View) {
        if (email.isEmpty() || password.isEmpty()) {
            requestToast.value = "이메일 또는 패스워드를 적어주세요."
        }
        //TODO 로그인 요청
    }
}