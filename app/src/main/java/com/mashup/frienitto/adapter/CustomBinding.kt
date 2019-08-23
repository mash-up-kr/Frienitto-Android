package com.mashup.frienitto.adapter

import android.annotation.SuppressLint
import android.os.FileObserver.CREATE
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.mashup.frienitto.R
import com.mashup.frienitto.util.computeExpireTimeDiff


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

@BindingAdapter("bind:roomUserImage")
fun ImageView.setRoomUserImage(imageCode: Int?) {
    when (imageCode) {
        1 -> setImageResource(R.drawable.profile_imagr_roomlist_face_1)
        2 -> setImageResource(R.drawable.profile_imagr_roomlist_face_2)
        3 -> setImageResource(R.drawable.profile_imagr_roomlist_face_3)
        4 -> setImageResource(R.drawable.profile_imagr_roomlist_face_4)
        5 -> setImageResource(R.drawable.profile_imagr_roomlist_face_5)
        6 -> setImageResource(R.drawable.profile_imagr_roomlist_face_6)
    }
}

@BindingAdapter("bind:roomCardImage")
fun ImageView.setRoomCardImage(roomId: Int) {
    when (roomId%6+1) {
        1 -> setImageResource(R.drawable.listface_1)
        2 -> setImageResource(R.drawable.listface_2)
        3 -> setImageResource(R.drawable.listface_3)
        4 -> setImageResource(R.drawable.listface_4)
        5 -> setImageResource(R.drawable.listface_5)
        6 -> setImageResource(R.drawable.listface_6)
    }
}


@SuppressLint("SetTextI18n")
@BindingAdapter("dayText")
fun setDayText(view: TextView, expiresDate: String) {
    val expireDayValue = computeExpireTimeDiff(expiresDate)
    when {
        expireDayValue < 0 -> view.text = "매칭 종료"
        expireDayValue == 0L -> view.text = "D-day"
        else -> view.text = "D-day.$expireDayValue"
    }
}

@BindingAdapter("statusText")
fun setStatusTest(view: TextView, statusText: String) {
    when (statusText) {
        "CREATED" -> view.text = "마니또 모집 중이에요"
        "MATCHED" -> view.text = "마니또 매칭 중이에요"
        "EXPIRED" -> view.text = "마니또가 발표되었어요"
    }
}