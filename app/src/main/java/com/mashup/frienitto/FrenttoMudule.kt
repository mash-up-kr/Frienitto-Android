package com.mashup.frienitto

import com.mashup.frienitto.login.LoginViewModel
import com.mashup.frienitto.register.RegisterFragmentViewModel
import com.mashup.frienitto.register.RegisterViewModel
import com.mashup.frienitto.room.creation.RoomCreationViewModel
import com.mashup.frienitto.select.SelectViewModel
import org.koin.core.module.Module
import org.koin.dsl.module
import org.koin.android.viewmodel.dsl.viewModel

val module: Module = module {
    viewModel { LoginViewModel() }
    viewModel { SelectViewModel() }
    viewModel { RegisterViewModel() }
    viewModel { RoomCreationViewModel() }
    viewModel { RegisterFragmentViewModel() }
}