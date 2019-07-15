package com.mashup.frienitto.matching

import android.content.Intent
import android.graphics.drawable.Animatable2
import android.graphics.drawable.Animatable2.AnimationCallback
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.animation.Animation
import android.view.animation.Animation.AnimationListener
import androidx.appcompat.app.AppCompatActivity
import androidx.vectordrawable.graphics.drawable.Animatable2Compat
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.load.resource.gif.GifDrawable
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.Target
import com.mashup.frienitto.room.home.RoomHomeActivity
import io.reactivex.Single
import io.reactivex.subjects.PublishSubject
import kotlinx.android.synthetic.main.activity_matching_animation.*
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.info
import org.jetbrains.anko.startActivity


class MatchingAnimationActivity : AppCompatActivity(), AnkoLogger {

    lateinit var listener: RequestListener<GifDrawable>
    private val gifEnd = PublishSubject.create<Boolean>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(com.mashup.frienitto.R.layout.activity_matching_animation)

        listener = object : RequestListener<GifDrawable> {
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
                val callback = object : Animatable2Compat.AnimationCallback(){
                    override fun onAnimationEnd(drawable: Drawable?) {
                        startActivity(Intent(this@MatchingAnimationActivity,RoomHomeActivity::class.java))
                        super.onAnimationEnd(drawable)
                    }
                }
                resource.registerAnimationCallback(callback)

                return false
            }
        }
        Glide.with(this).asGif().load(com.mashup.frienitto.R.drawable.frentto_ani)
            .apply(RequestOptions.diskCacheStrategyOf(DiskCacheStrategy.NONE))
            .listener(listener).into(img_gif)

    }
}




