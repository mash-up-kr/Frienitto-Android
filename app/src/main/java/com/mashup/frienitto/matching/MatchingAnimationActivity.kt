package com.mashup.frienitto.matching

import android.content.Intent
import android.graphics.drawable.Drawable
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.vectordrawable.graphics.drawable.Animatable2Compat
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.load.resource.gif.GifDrawable
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.mashup.frienitto.room.home.RoomHomeActivity
import kotlinx.android.synthetic.main.activity_matching_animation.*
import org.jetbrains.anko.AnkoLogger


class MatchingAnimationActivity : AppCompatActivity(), AnkoLogger {

    private val mGlideRequestManager by lazy {
        Glide.with(this)
    }

    private val listener: RequestListener<GifDrawable> by lazy {
        object : RequestListener<GifDrawable> {
            override fun onLoadFailed(
                e: GlideException?, model: Any,
                target: Target<GifDrawable>,
                isFirstResource: Boolean
            ): Boolean {
                return false
            }

            override fun onResourceReady(
                resource: GifDrawable,
                model: Any,
                target: Target<GifDrawable>,
                dataSource: DataSource,
                isFirstResource: Boolean
            ): Boolean {
                resource.setLoopCount(1)
                val callback = object : Animatable2Compat.AnimationCallback() {
                    override fun onAnimationEnd(drawable: Drawable?) {
                        startActivity(Intent(this@MatchingAnimationActivity, RoomHomeActivity::class.java))
                        super.onAnimationEnd(drawable)
                    }
                }
                resource.registerAnimationCallback(callback)

                return false
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(com.mashup.frienitto.R.layout.activity_matching_animation)
        mGlideRequestManager
            .asGif()
            .load(com.mashup.frienitto.R.drawable.frentto_ani)
            .listener(listener).into(img_gif)

    }
}





