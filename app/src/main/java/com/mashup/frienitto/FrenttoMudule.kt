package com.mashup.frienitto

import com.mashup.frienitto.login.LoginViewModel
import com.mashup.frienitto.register.RegisterFragmentViewModel
import com.mashup.frienitto.register.RegisterViewModel
import com.mashup.frienitto.repository.room.RoomRepository
import com.mashup.frienitto.room.close.RoomCloseViewModel
import com.mashup.frienitto.room.mypage.RoomMyPageViewModel
import com.mashup.frienitto.room.creation.RoomCreationViewModel
import com.mashup.frienitto.room.entry.RoomEntryViewModel

import com.mashup.frienitto.room.home.RoomHomeViewModel
import com.mashup.frienitto.select.SelectViewModel
import org.koin.core.module.Module
import org.koin.dsl.module
import org.koin.android.viewmodel.dsl.viewModel

val module: Module = module {
    viewModel { LoginViewModel(get()) }
    viewModel { SelectViewModel() }
    viewModel { RegisterViewModel() }
    viewModel { RoomCreationViewModel(get()) }
    viewModel { RegisterFragmentViewModel() }
    viewModel { RoomEntryViewModel(get()) }
    viewModel { RoomHomeViewModel(get()) }
    viewModel { RoomMyPageViewModel() }
    viewModel { RoomCloseViewModel() }
    single { RoomRepository() }
}