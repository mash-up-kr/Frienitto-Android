package com.mashup.frienitto.register

import android.util.Log
import androidx.databinding.ObservableField
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.mashup.frienitto.base.BaseViewModel
import com.mashup.frienitto.data.RequestAuth
import com.mashup.frienitto.data.RequestEmailCode
import com.mashup.frienitto.data.RequestSignUp
import com.mashup.frienitto.repository.user.UserRepository
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class RegisterFragmentViewModel() : BaseViewModel() {
    val registerStepCnt = MutableLiveData<Int>().apply { postValue(0) }
    val signinComplete = MutableLiveData<Boolean>()
    val emailText: LiveData<String>
        get() = email

    val isPasswordSame = ObservableField<Boolean>().apply { set(false) }

    val isWriteEamil = ObservableField<Boolean>().apply { set(false) }

    val isWriteCode = ObservableField<Boolean>().apply { set(false) }

    val isWriteAllData = ObservableField<Boolean>().apply { set(false) }

    var email = MutableLiveData<String>()

    var name: String = ""

    var password: String = ""

    var repassword: String = ""

    var code: String = ""

    var info: String = ""

    fun goNextStep(state: Int) {
        when (state) {
            1 -> {
                sendConfirmEmail()
            }
            2 -> {
                confirmCode()
            }
            else -> registerStepCnt.value = state
        }
    }

    private fun confirmCode() {
        addDisposable(
                UserRepository.requestAuth(RequestAuth(email.value.toString(), "EMAIL", code))
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe({ response ->
                            Log.d("csh Success", response.toString())
                            UserRepository.setTokenizer(response.data.registerToken)
                            registerStepCnt.value = 2
                        }, { except ->
                            Log.d("csh Error", except.message)
                        })
        )
    }

    fun sendConfirmEmail() {
        addDisposable(
                 UserRepository.requestEmail(RequestEmailCode(email.value.toString(), "EMAIL"))
                         .subscribeOn(Schedulers.io())
                         .observeOn(AndroidSchedulers.mainThread())
                         .subscribe({ response ->
                             Log.d("csh Success", response?.msg)
                             registerStepCnt.value = 1
                         }, { except ->
                             Log.d("csh Error", except.message?.toString())
                         })
         )
    }

    fun signIn() {
        addDisposable(
                UserRepository.signUp(UserRepository.getTokenizer(), RequestSignUp(name, info, 1, email.value.toString(), password))
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe({ response ->
                            Log.d("csh Success", response.msg)
                            signinComplete.value = true
                        }, { except ->
                            Log.d("csh Error", except.message?.toString())
                        })
        )
    }

    fun onEmailTextChange(email: CharSequence) {
        this.email.value = email.toString()
        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            isWriteEamil.set(false)
        } else {
            isWriteEamil.set(true)
        }
    }

    fun onNameTextChange(name: CharSequence) {
        this.name = name.toString()
        if (info.isNotEmpty()) {
            isWriteAllData.set(true)
        } else {
            isWriteAllData.set(false)
        }
    }

    fun onInfoTextChange(info: CharSequence) {
        this.info = info.toString()
        if (name.isNotEmpty()) {
            isWriteAllData.set(true)
        } else {
            isWriteAllData.set(false)
        }
    }

    fun onPasswordTextChange(password: CharSequence) {
        this.password = password.toString()
    }

    fun onRepasswordTextChange(password: CharSequence) {
        this.repassword = password.toString()
        if (password.isNotEmpty() && this.password.equals(repassword)) {
            isPasswordSame.set(true)
        } else if (isPasswordSame.get()!!) {
            isPasswordSame.set(false)
        }
    }

    fun onCodeTextChange(code: CharSequence) {
        this.code = code.toString()
        if (code.isNotEmpty()) {
            isWriteCode.set(true)
        } else {
            isWriteCode.set(false)
        }
    }
}