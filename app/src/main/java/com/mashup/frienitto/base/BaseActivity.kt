package com.mashup.frienitto.base

import android.content.pm.ActivityInfo
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDialog
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import com.airbnb.lottie.LottieAnimationView
import com.airbnb.lottie.LottieDrawable
import com.mashup.frienitto.R
import io.reactivex.Completable
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import kotlinx.android.synthetic.main.dialog_loading.*
import org.koin.android.ext.android.inject
import org.koin.core.parameter.parametersOf

abstract class BaseActivity<T : ViewDataBinding> : AppCompatActivity() {
    abstract val layoutResourceId: Int
    lateinit var viewDataBinding: T
    private val compositeDisposable = CompositeDisposable()
    val progressDialog: AppCompatDialog by inject { parametersOf(this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        overridePendingTransition(R.anim.anim_slide_in_right, R.anim.anim_slide_out_left)
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        viewDataBinding = DataBindingUtil.inflate(layoutInflater, layoutResourceId, null, false)
    }

    fun addDisposable(disposable: Disposable) {
        compositeDisposable.add(disposable)
    }


    override fun onDestroy() {
        compositeDisposable.dispose()
        super.onDestroy()
    }

    override fun onBackPressed() {
        super.onBackPressed()
        overridePendingTransition(R.anim.anim_slide_in_left, R.anim.anim_slide_out_right)
    }

    fun showProgress(message: String = resources.getString(R.string.progress_bar_message)) {
        if (this.isFinishing) {
            return
        }
        if (progressDialog.isShowing) {
            setProgress(message)
        }
        progressDialog.tv_pregress_message.text = message
        progressDialog.show()
    }

    private fun setProgress(message: String?) {
        if (progressDialog.isShowing) {
            return
        }
        progressDialog.tv_pregress_message.text = message ?: " "
    }

    fun dismissProgress() {
        if (progressDialog.isShowing) {
            progressDialog.dismiss()
        }
    }

}