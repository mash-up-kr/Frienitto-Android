package com.mashup.frienitto

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AppCompatDialog
import com.airbnb.lottie.LottieDrawable
import com.mashup.frienitto.login.LoginViewModel
import com.mashup.frienitto.matching.home.MatchingHomeViewModel
import com.mashup.frienitto.register.RegisterFragmentViewModel
import com.mashup.frienitto.register.RegisterViewModel
import com.mashup.frienitto.repository.room.RoomRepository
import com.mashup.frienitto.repository.user.UserRepository
import com.mashup.frienitto.room.list.RoomListViewModel
import com.mashup.frienitto.matching.finish.MatchingFinishViewModel
import com.mashup.frienitto.room.creation.RoomCreationViewModel
import com.mashup.frienitto.room.join.RoomJoinViewModel

import com.mashup.frienitto.room.home.RoomHomeViewModel
import com.mashup.frienitto.select.SelectViewModel
import kotlinx.android.synthetic.main.dialog_loading.*
import org.koin.android.ext.koin.androidApplication
import org.koin.core.module.Module
import org.koin.dsl.module
import org.koin.android.viewmodel.dsl.viewModel

val module: Module = module {
    viewModel { LoginViewModel(get(), get()) }
    viewModel { SelectViewModel() }
    viewModel { RegisterViewModel() }
    viewModel { RoomCreationViewModel(get(), get(), get()) }
    viewModel { RegisterFragmentViewModel(get()) }
    viewModel { RoomJoinViewModel(get(), get(), get()) }
    viewModel {RoomHomeViewModel(get(), get()) }
    viewModel {MatchingHomeViewModel(get(), get()) }
    viewModel { MatchingFinishViewModel(get(), get()) }
    viewModel { RoomListViewModel(androidApplication(), get(), get()) }
    single { RoomRepository() }
    single { UserRepository() }
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