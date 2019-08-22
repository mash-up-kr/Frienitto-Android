package com.mashup.frienitto

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.mashup.frienitto.data.UserInfo
import com.mashup.frienitto.home.HomeActivity
import com.mashup.frienitto.repository.room.RoomRepository
import com.mashup.frienitto.repository.user.UserRepository
import com.mashup.frienitto.room.list.RoomListActivity
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.info
import org.koin.android.ext.android.inject
import java.text.SimpleDateFormat
import java.util.*

class SplashActivity : AppCompatActivity(),AnkoLogger {
    private val userRepository: UserRepository by inject()
    private val roomRepository: RoomRepository by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (userRepository.getUserInfo(this) == null) {
            val intent = Intent(this, HomeActivity::class.java)
            startActivity(intent)
        } else {
            val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
            val date = Date()
            val strDate = dateFormat.format(date)
            Log.d("loloss", "RoomHomeActivity ${roomRepository.getRoomId(this)}")
            when {
                (userRepository.getUserInfo(this) as UserInfo).tokenExpiresDate <= strDate -> {
                    info { "tag1 first%%%%%%" }
                    val intent = Intent(this, HomeActivity::class.java)
                    startActivity(intent)
                }
                else->{
                    info { "tag1 second%%%%%%" }
                    startActivity(Intent(this,RoomListActivity::class.java))
                }

//                roomRepository.getRoomId(this) != null -> {
//                    val intent = Intent(this, RoomHomeActivity::class.java)
//                    startActivity(intent)
//                }
//                else -> {
//                    val intent = Intent(this, RoomActivity::class.java)
//                    startActivity(intent)
//                }
            }
        }

        //TODO 방참여 이력이 있으면
        //바로 방으로 이동

        finish()
    }
}
