package com.mashup.frienitto.register

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.mashup.frienitto.base.BaseViewModel

class RegisterFragmentViewModel : BaseViewModel() {
    val registerStepCnt = MutableLiveData<Int>().apply { postValue(0) }
    val signinComplete = MutableLiveData<Boolean>()
    val emailText :LiveData<String>
        get() = email

    var email = MutableLiveData<String>()

    var name: String = ""

    var password: String = ""

    var repassword: String = ""

    var code: String = ""

    var info: String = ""

    fun goNextStep(state: Int) {
        when(state){
            1 -> {
                sendConfirmEmail()
            }
            2 -> {
                confirmCode()
            }
        }
        registerStepCnt.value = state
    }

    private fun confirmCode(){

    }

    fun sendConfirmEmail() {

    }

    fun signIn() {
        signinComplete.value = true
    }

    fun onEmailTextChange(email: CharSequence) {
        this.email.value = email.toString()
    }

    fun onNameTextChange(name: CharSequence) {
        this.name = name.toString()
    }

    fun onInfoTextChange(info: CharSequence) {
        this.info = info.toString()
    }

    fun onPasswordTextChange(password: CharSequence) {
        this.password = password.toString()
    }

    fun onRepasswordTextChange(password: CharSequence) {
        this.repassword = password.toString()
    }

    fun onCodeTextChange(code: CharSequence) {
        this.code = code.toString()
    }
}