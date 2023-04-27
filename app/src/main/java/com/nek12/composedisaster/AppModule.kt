package com.nek12.composedisaster

import com.nek12.composedisaster.ui.PinState
import com.nek12.composedisaster.ui.PinStateImpl
import com.nek12.composedisaster.ui.screens.HomeViewModel
import kotlinx.coroutines.GlobalScope
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.dsl.bind
import org.koin.dsl.module

val appModule = module {
    viewModelOf(::HomeViewModel)

    // not cool ❌, compose has its own scope
    factory { PinStateImpl(GlobalScope) } bind PinState::class
}
