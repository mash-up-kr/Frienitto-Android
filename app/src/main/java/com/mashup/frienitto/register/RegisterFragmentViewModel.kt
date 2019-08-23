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
import retrofit2.HttpException
import kotlin.random.Random

class RegisterFragmentViewModel(private val userRepository: UserRepository) : BaseViewModel() {
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

    val requestToast = MutableLiveData<String>()

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
        showLoadingDialog()
        addDisposable(
            userRepository.requestAuth(RequestAuth(email.value.toString(), "EMAIL", code))
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe({ response ->
                            Log.d("csh Success", response.toString())
                            if (showConfirmToast(response.code)) {
                                userRepository.setTokenizer(response.data.registerToken)
                                registerStepCnt.value = 2
                            }

                            dissmissLoadingDialog()
                        }, { except ->
                            Log.d("csh Error", except.message)
                            if (except is HttpException)
                                showConfirmToast(except.code())
                            dissmissLoadingDialog()
                        })
        )
    }

    fun sendConfirmEmail() {
        showLoadingDialog()
        addDisposable(
            userRepository.requestEmail(RequestEmailCode(email.value.toString(), "EMAIL"))
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe({ response ->
                            if (showRequestEmailToast(response.code)) {
                                registerStepCnt.value = 1
                            }
                            dissmissLoadingDialog()
                        }, { except ->
                            if (except is HttpException)
                                showRequestEmailToast(except.code())

                            dissmissLoadingDialog()
                        })
        )
    }

    fun signUp() {
        showLoadingDialog()
        addDisposable(
            userRepository.signUp(userRepository.getTokenizer(), RequestSignUp(name, info, Random.nextInt(5) + 1, email.value.toString(), password))
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe({ response ->
                            Log.d("csh Success", response.msg)
                            if (showSignInToast(response.code)) {
                                signinComplete.value = true
                            }

                            dissmissLoadingDialog()
                        }, { except ->
                            Log.d("csh Error", except.message?.toString())
                            if (except is HttpException)
                                showSignInToast(except.code())
                            dissmissLoadingDialog()
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

    fun showRequestEmailToast(code: Int) : Boolean {
        when (code) {
            200 -> {
                requestToast.postValue("메일 요청 완료")
            }
            201 -> {
                requestToast.postValue("Created")
            }
            202 -> {
                requestToast.postValue("Accepted")
            }
            401 -> {
                requestToast.postValue("Unauthorized")
            }
            403 -> {
                requestToast.postValue("Forbidden")
            }
            404 -> {
                requestToast.postValue("Not Found")
            }
            409 -> {
                requestToast.postValue("이미 등록된 이메일 입니다.")
            }
            5004 -> {
                requestToast.postValue("메일 전송 실패")
            }
            else -> {
                requestToast.postValue("알 수 없는 오류")
            }
        }

        return code / 100 == 2
    }

    fun showSignInToast(code: Int) : Boolean {
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
            else -> {
                requestToast.postValue("알 수 없는 오류")
            }

        }

        return code / 100 == 2
    }

    fun showConfirmToast(code: Int) : Boolean {
        when (code) {
            200 -> {
                requestToast.postValue("완료")
            }
            201 -> {
                requestToast.postValue("Created")
            }
            401 -> {
                requestToast.postValue("인증 코드가 맞지 않습니다.")
            }
            403 -> {
                requestToast.postValue("Forbidden")
            }
            404 -> {
                requestToast.postValue("Not Found")
            }
            else -> {
                requestToast.postValue("알 수 없는 오류")
            }

        }

        return code / 100 == 2
    }
}