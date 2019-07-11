package com.mashup.frienitto

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.mashup.frienitto.data.Token
import com.mashup.frienitto.home.HomeActivity
import com.mashup.frienitto.repository.user.UserRepository
import com.mashup.frienitto.room.RoomActivity
import java.text.SimpleDateFormat
import java.util.*

class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (UserRepository.getUserToken(this) == null) {
            val intent = Intent(this, HomeActivity::class.java)
            startActivity(intent)
        } else {
            val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
            val date = Date()
            val strDate = dateFormat.format(date)
            if ((UserRepository.getUserToken(this) as Token).tokenExpiresDate <= strDate) {
                val intent = Intent(this, HomeActivity::class.java)
                startActivity(intent)
            } else {
                val intent = Intent(this, RoomActivity::class.java)
                startActivity(intent)
            }
        }

        //TODO 방참여 이력이 있으면
        //바로 방으로 이동

        finish()
    }
}
