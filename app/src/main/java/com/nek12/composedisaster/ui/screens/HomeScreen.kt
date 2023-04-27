package com.nek12.composedisaster.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.nek12.composedisaster.ui.PinState
import com.nek12.composedisaster.ui.rememberPinState
import com.nek12.composedisaster.ui.theme.ComposeDisasterTheme
import com.nek12.composedisaster.ui.widgets.BadToggleButton
import com.nek12.composedisaster.ui.widgets.ToggleButton
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootNavGraph
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import org.koin.androidx.compose.getViewModel
import org.koin.compose.rememberKoinInject

@RootNavGraph(start = true)
@Destination
@Composable
fun HomeScreen(nav: DestinationsNavigator) {

    val vm = getViewModel<HomeViewModel>()

    val state by vm.state.collectAsState() // Not lifecycle aware

    val pinState = rememberPinState(loading = state.isLoading, scope = rememberCoroutineScope())

    // will fix the leak
    //    val updatedPinState by rememberUpdatedState(pinState)

    // disposable effect leaked pinState
    DisposableEffect(Unit) {

        pinState.pinEntered()

        onDispose { pinState.lock() }
    }

    BadHomeScreenContent(
        vm = vm, // Unstable  ❌
        clickedStartLoad = { vm.badLoadItems() }, // UNSTABLE ❌
        state = state
    )

//    HomeScreenContent(
//        state = state, // ✅ stable
//        onStartLoad = vm::loadItems, // ✅ stable
//        onOpened = vm::trackHomeOpenedAnalytics, // ✅ stable
//    )
}

@Composable
private fun BadHomeScreenContent(
    vm: HomeViewModel,
    clickedStartLoad: () -> Unit,  // ❌ improper naming
    state: HomeState,
) {
    vm.trackHomeOpenedAnalytics() // will track on every recomposition (infinite loop)

    val pinState = rememberKoinInject<PinState>() // rip previews ❌

    Column {
        Text("Opened times: ${state.screenOpenedTimes}")

        // ❌ abrupt transition
        if (state.isLoading) {
            LinearProgressIndicator(modifier = Modifier.fillMaxWidth())
        }

        BadToggleButton(text = "Start loading", onToggle = { clickedStartLoad() })

        state.items.forEach {
            // ❌ RIP performance
            Text(it)
        }
    }
}

@Composable
fun HomeScreenContent(
    // ✅ stable state
    // ✅ easy previews
    state: HomeState,
    onOpened: () -> Unit,
    // ✅ stable reusable lambdas
    onStartLoad: () -> Unit,
    // ✅ modifier param
    modifier: Modifier = Modifier,
) {

    // ✅ will trigger on FIRST composition only
    LaunchedEffect(Unit) {
        onOpened()
    }

    Column(modifier = modifier) {
        Text("Opened times: ${state.screenOpenedTimes}")

        // ✅ nice transition, 0 lines of code
        AnimatedVisibility(state.isLoading) {
            LinearProgressIndicator(modifier = Modifier.fillMaxWidth())
        }

        // ✅ customizable content
        ToggleButton(toggled = state.isLoading, onClick = onStartLoad) {
            // text uses localContentColor, color is provided transparently an easily
            Text("Load item")
        }

        // ✅ efficient recomposition
        LazyColumn(modifier = Modifier.weight(1f)) {
            items(state.items) {
                Text(it)
            }
        }
    }

}


@Preview
@Composable
// ❌ leaked public visibility
fun BadHomeScreenPreview() = ComposeDisasterTheme {
    BadHomeScreenContent(
        clickedStartLoad = {},
        state = HomeState(),
        vm = TODO(), // ❓❓❓ // rip previews
    )
}


@Preview
@Composable
private fun HomeScreenPreview() = ComposeDisasterTheme {
    HomeScreenContent(
        onOpened = {}, //  ✅
        onStartLoad = {},
        state = HomeState()
    )
}
