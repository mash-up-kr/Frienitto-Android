package com.mashup.frienitto

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.mashup.frienitto.home.HomeActivity

class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val intent = Intent(this, HomeActivity::class.java)
        startActivity(intent)

        //TODO 토큰 검사
        //토근이 있으면 바로 방참여, 만들기로

        //TODO 방참여 이력이 있으면
        //바로 방으로 이동

        finish()
    }
}
