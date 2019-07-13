package com.mashup.frienitto.matching

import android.graphics.Bitmap
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil.setContentView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.GlideDrawable
import com.bumptech.glide.load.resource.gif.GifDrawable
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.ImageViewTarget
import com.bumptech.glide.request.target.SimpleTarget
import com.bumptech.glide.request.target.Target
import kotlinx.android.synthetic.main.activity_matching_animation.*
import java.lang.Exception
import java.util.*
import kotlin.concurrent.schedule


class MatchingAnimationActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(com.mashup.frienitto.R.layout.activity_matching_animation)

        Glide.with(this)
            .load(com.mashup.frienitto.R.drawable.frentto_ani)
            .into(img_gif)

        Timer().schedule(5000){
            //넘아갈 페이지
            //finish()
        }


    }
}
