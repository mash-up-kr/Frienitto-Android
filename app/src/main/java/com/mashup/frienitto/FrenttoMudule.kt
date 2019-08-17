package com.mashup.frienitto

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.widget.TextView
import androidx.appcompat.app.AppCompatDialog
import com.airbnb.lottie.LottieAnimationView
import com.airbnb.lottie.LottieDrawable
import com.mashup.frienitto.login.LoginViewModel
import com.mashup.frienitto.matching.home.MatchingHomeViewModel
import com.mashup.frienitto.register.RegisterFragmentViewModel
import com.mashup.frienitto.register.RegisterViewModel
import com.mashup.frienitto.repository.room.RoomRepository
import com.mashup.frienitto.room.close.RoomCloseViewModel
import com.mashup.frienitto.room.creation.RoomCreationViewModel
import com.mashup.frienitto.room.join.RoomJoinViewModel

import com.mashup.frienitto.room.home.RoomHomeViewModel
import com.mashup.frienitto.select.SelectViewModel
import io.reactivex.Completable
import kotlinx.android.synthetic.main.dialog_loading.*
import org.koin.core.module.Module
import org.koin.dsl.module
import org.koin.android.viewmodel.dsl.viewModel

val module: Module = module {
    viewModel { LoginViewModel(get()) }
    viewModel { SelectViewModel() }
    viewModel { RegisterViewModel() }
    viewModel { RoomCreationViewModel(get(), get()) }
    viewModel { RegisterFragmentViewModel() }
    viewModel { RoomJoinViewModel(get(), get()) }
    viewModel { RoomHomeViewModel(get()) }
    viewModel { MatchingHomeViewModel(get()) }
    viewModel { RoomCloseViewModel() }
    single { RoomRepository() }
    factory { (context: Context) ->
        AppCompatDialog(context)
            .apply {
                setCancelable(false)
                window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
                setContentView(R.layout.dialog_loading)
                val animationView = this.iv_frame_loading
                animationView.setAnimation("loading.json")
                animationView.repeatCount = LottieDrawable.INFINITE
                animationView.playAnimation()
            }
    }
}