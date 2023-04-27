package com.nek12.composedisaster.ui.screens

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlin.time.Duration.Companion.seconds

//@Immutable
data class HomeState(
    var isLoading: Boolean = false, // unstable ❌
    val badItems: MutableList<String> = mutableListOf(), // unstable, dangerous ❌
    val items: List<String> = listOf(), // unstable, dangerous ❌
    val screenOpenedTimes: Int = 0,
)

class HomeViewModel(

) : ViewModel() {

    private val _state = MutableStateFlow(HomeState())
    val state = _state.asStateFlow()


    fun trackHomeOpenedAnalytics() = state {
        copy(screenOpenedTimes = screenOpenedTimes + 1)
    }

    fun badLoadItems() = viewModelScope.launch {
        state.value.isLoading = true // ❌ will NOT notify compose
        delay(2.seconds)
        state.value.apply {
            isLoading = false // ❌ will NOT notify compose
            badItems.add("new item") // ❌ will NOT notify compose
        }
    }

    fun loadItems() = viewModelScope.launch {
        state { copy(isLoading = true) }  // ✅ will notify compose
        delay(2.seconds)
        state { copy(isLoading = false, items = items + listOf("new item")) } // ✅ will notify compose
    }

    private fun state(block: HomeState.() -> HomeState) = _state
        .update(block)
        .also { Log.d("State", state.toString()) }
}
