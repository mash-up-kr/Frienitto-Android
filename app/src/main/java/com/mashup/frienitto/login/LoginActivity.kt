package com.mashup.frienitto.login

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.mashup.frienitto.R
import org.koin.android.viewmodel.ext.android.viewModel

class LoginActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
    }
}
