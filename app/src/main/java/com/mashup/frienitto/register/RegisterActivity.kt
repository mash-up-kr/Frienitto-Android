package com.mashup.frienitto.register

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatDialog
import com.airbnb.lottie.LottieAnimationView
import com.airbnb.lottie.LottieDrawable
import com.mashup.frienitto.R
import io.reactivex.Completable
import kotlinx.android.synthetic.main.activity_register.*
import kotlinx.android.synthetic.main.dialog_loading.*
import org.koin.android.ext.android.inject
import org.koin.core.parameter.parametersOf

class RegisterActivity : AppCompatActivity() {
    private val progressDialog: AppCompatDialog by inject { parametersOf(this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        overridePendingTransition(R.anim.anim_slide_in_right, R.anim.anim_slide_out_left)
        setContentView(R.layout.activity_register)
        val adapter = RegisterPagerAdapter(supportFragmentManager)
        viewPager.adapter = adapter
    }

    fun setCurrentPage(position: Int) {
        when (position) {
            0 -> tv_signin_info.text = getString(R.string.sign_in_1)
            1 -> tv_signin_info.text = getString(R.string.sign_in_2)
            2 -> tv_signin_info.text = getString(R.string.sign_in_3)
            3 -> tv_signin_info.text = getString(R.string.sign_in_4)
        }

        tv_pager_position.text = (position + 1).toString()
        viewPager.currentItem = position
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
