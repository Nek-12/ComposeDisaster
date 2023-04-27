package com.nek12.composedisaster.ui

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.random.Random
import kotlin.time.Duration.Companion.seconds

// UNSTABLE ❌
interface PinState {

    fun pinEntered()
    fun lock()
}

class PinStateImpl(private val scope: CoroutineScope) : PinState {

    private val key = Random.nextInt(10000)

    override fun pinEntered() {
        scope.launch {
            delay(1.seconds)
            Log.d("PinState", "Pin verification $key finished")
        }
    }

    override fun lock() {
        Log.d("PinState", "Pin locking $key finished")
    }
}

@Composable
fun rememberPinState(loading: Boolean, scope: CoroutineScope): PinState = remember(scope, loading) {
    PinStateImpl(scope)
}
