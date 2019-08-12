package com.mashup.frienitto

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
}