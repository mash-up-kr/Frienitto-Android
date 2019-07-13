package com.mashup.frienitto.matching

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.activity_matching_animation.*


class MatchingAnimationActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(com.mashup.frienitto.R.layout.activity_matching_animation)

        Glide.with(this).load(com.mashup.frienitto.R.drawable.frentto_ani).into(img_gif)


    }
}
