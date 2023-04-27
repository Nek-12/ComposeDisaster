package com.nek12.composedisaster

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.navigation.NavHostController
import androidx.navigation.plusAssign
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import com.google.accompanist.navigation.material.BottomSheetNavigator
import com.google.accompanist.navigation.material.ExperimentalMaterialNavigationApi
import com.google.accompanist.navigation.material.ModalBottomSheetLayout
import com.google.accompanist.navigation.material.rememberBottomSheetNavigator
import com.nek12.composedisaster.ui.screens.NavGraphs
import com.nek12.composedisaster.ui.theme.ComposeDisasterTheme
import com.ramcosta.composedestinations.DestinationsNavHost
import com.ramcosta.composedestinations.animations.rememberAnimatedNavHostEngine
import com.ramcosta.composedestinations.spec.NavHostEngine

@OptIn(ExperimentalMaterialNavigationApi::class, ExperimentalAnimationApi::class)
@Composable
fun CDContent() {
    val navHostEngine = rememberAnimatedNavHostEngine(navHostContentAlignment = Alignment.Center)
    val bottomSheetNavigator = rememberBottomSheetNavigator()
    val navController = rememberAnimatedNavController().apply {
        navigatorProvider += bottomSheetNavigator
    }
    ComposeDisasterTheme {
        CDNavigation(
            navigator = bottomSheetNavigator,
            engine = navHostEngine,
            navController = navController,
        )
    }
}

@OptIn(ExperimentalMaterialNavigationApi::class)
@Composable
private fun CDNavigation(
    navigator: BottomSheetNavigator,
    engine: NavHostEngine,
    navController: NavHostController,
) {
    ModalBottomSheetLayout(bottomSheetNavigator = navigator) {
        DestinationsNavHost(
            navGraph = NavGraphs.root,
            engine = engine,
            navController = navController,
            dependenciesContainerBuilder = {
            }
        )
    }
}
