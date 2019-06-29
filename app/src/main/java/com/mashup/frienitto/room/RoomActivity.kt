package com.mashup.frienitto.room

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.mashup.frienitto.R
import com.mashup.frienitto.room.creation.RoomCreationActivity
import kotlinx.android.synthetic.main.activity_room.*

class RoomActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_room)

        btn_room_creation.setOnClickListener{
            startActivity(Intent(this, RoomCreationActivity::class.java))
        }
    }
}
