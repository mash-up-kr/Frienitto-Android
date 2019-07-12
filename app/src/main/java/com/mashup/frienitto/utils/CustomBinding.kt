package com.mashup.frienitto.utils

import android.view.View
import android.widget.ImageButton
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.mashup.frienitto.R



@BindingAdapter("customBackground")
fun setCustomBackground(view: ImageButton, submitName: String) {
    if (submitName == "확인") {
        view.setBackgroundResource(R.color.orange)
    } else {
        view.setBackgroundResource(R.color.gray)
    }
}

@BindingAdapter("bind:userImage")
fun ImageView.setUserImage(imageCode: Int?) {
    when (imageCode) {
        1 -> setImageResource(R.drawable.small_1)
        2 -> setImageResource(R.drawable.small_2)
        3 -> setImageResource(R.drawable.small_3)
        4 -> setImageResource(R.drawable.small_4)
        5 -> setImageResource(R.drawable.small_5)
        6 -> setImageResource(R.drawable.small_6)
    }
}
