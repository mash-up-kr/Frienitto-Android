package com.mashup.frienitto.matching

import android.animation.Animator
import android.content.Intent
import android.graphics.drawable.Drawable
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.vectordrawable.graphics.drawable.Animatable2Compat
import com.airbnb.lottie.LottieAnimationView
import com.airbnb.lottie.LottieDrawable
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.load.resource.gif.GifDrawable
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.mashup.frienitto.room.home.RoomHomeActivity
import com.mashup.frienitto.room.list.RoomListActivity
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
                        startActivity(Intent(this@MatchingAnimationActivity, RoomListActivity::class.java))
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

        setUpAnimation(lottieAnimView)
    }

    private fun setUpAnimation(animationView: LottieAnimationView) {
        animationView.imageAssetsFolder = "images/"
        animationView.setAnimation("data.json")
        animationView.repeatCount = 0
        animationView.playAnimation()

        animationView.addAnimatorListener(object : Animator.AnimatorListener {
            override fun onAnimationStart(animation: Animator) {
            }

            override fun onAnimationEnd(animation: Animator) {
                try {
                    startActivity(Intent(this@MatchingAnimationActivity, RoomListActivity::class.java))
                } catch (ex: Exception) {
                    ex.toString()
                }
            }

            override fun onAnimationCancel(animation: Animator) {
            }

            override fun onAnimationRepeat(animation: Animator) {
            }
        })
    }
}
