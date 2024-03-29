package com.mashup.frienitto.base

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

open class BaseViewModel : ViewModel() {
    private val compositeDisposable = CompositeDisposable()
    val showLoadingDialog = MutableLiveData<Boolean>()

    fun addDisposable(disposable: Disposable) {
        compositeDisposable.add(disposable)
    }

    fun showLoadingDialog(){
        showLoadingDialog.postValue(true)
    }

    fun dissmissLoadingDialog(){
        showLoadingDialog.postValue(false)
    }

    override fun onCleared() {
        compositeDisposable.clear()
        super.onCleared()
    }
}