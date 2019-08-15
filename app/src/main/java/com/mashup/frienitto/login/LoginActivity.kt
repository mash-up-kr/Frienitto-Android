package com.mashup.frienitto.login

import android.content.Intent
import android.os.Bundle
import androidx.lifecycle.Observer
import com.mashup.frienitto.R
import com.mashup.frienitto.base.BaseActivity
import com.mashup.frienitto.databinding.ActivityLoginBinding
import com.mashup.frienitto.register.RegisterActivity
import com.mashup.frienitto.room.RoomActivity
import org.jetbrains.anko.toast
import org.koin.android.viewmodel.ext.android.viewModel

class LoginActivity : BaseActivity<ActivityLoginBinding>() {
    override val layoutResourceId: Int
        get() = R.layout.activity_login

    private val viewModel: LoginViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(viewDataBinding.root)
        viewDataBinding.viewModel = viewModel

        viewModel.requestToast.observe(this, Observer {
            toast(it)
        })

        viewModel.isLogin.observe(this, Observer {
            val intent = Intent(this, RoomActivity::class.java)
            startActivity(intent)
            finishAffinity()
        })

        viewModel.showLoadingDialog.observe(this, Observer {
            if(it) showProgress()
            else dismissProgress()
        })
    }
}
