package com.mashup.frienitto.room

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.mashup.frienitto.R
import com.mashup.frienitto.room.close.RoomCloseActivity
import com.mashup.frienitto.room.creation.RoomCreationActivity
import com.mashup.frienitto.room.home.RoomHomeActivity
import com.mashup.frienitto.room.mypage.RoomMyPageActivity
import kotlinx.android.synthetic.main.activity_room.*

class RoomActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_room)

        btn_room_creation.setOnClickListener{
            startActivity(Intent(this, RoomCreationActivity::class.java))
        }

        btn_room_enter.setOnClickListener{
            startActivity(Intent(this, RoomHomeActivity::class.java))
        }
    }
}
