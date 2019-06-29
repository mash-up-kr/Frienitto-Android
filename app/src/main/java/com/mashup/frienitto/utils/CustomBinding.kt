package com.mashup.frienitto.utils

import android.widget.ImageButton
import androidx.databinding.BindingAdapter
import com.mashup.frienitto.R

@BindingAdapter("customBackground")
fun setCustomBackground(view:ImageButton,submitName:String){
    if(submitName=="확인"){
        view.setBackgroundResource(R.color.orange)
    }else{
        view.setBackgroundResource(R.color.gray)
    }
}