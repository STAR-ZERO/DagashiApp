package com.star_zero.dagashi.ui.main

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.navigation.NavGraphBuilder
import androidx.navigation.navigation
import com.google.accompanist.navigation.material.ExperimentalMaterialNavigationApi
import com.ramcosta.composedestinations.animations.rememberAnimatedNavHostEngine
import com.ramcosta.composedestinations.spec.NavGraphSpec
import com.ramcosta.composedestinations.spec.NavHostEngine

// TODO: Workaround: compose-destinations is not supported Compose 1.2.0
@ExperimentalAnimationApi
@ExperimentalMaterialNavigationApi
@Composable
fun rememberCustomAnimatedNavHostEngine(): NavHostEngine {
    val origNavHostEngine = rememberAnimatedNavHostEngine()
    return remember {
        CustomAnimatedNavHostEngine(origNavHostEngine)
    }
}

class CustomAnimatedNavHostEngine(
    private val navHostEngine: NavHostEngine,
) : NavHostEngine by navHostEngine {

    override fun NavGraphBuilder.navigation(
        navGraph: NavGraphSpec,
        builder: NavGraphBuilder.() -> Unit
    ) {
        navigation(
            startDestination = navGraph.startRoute.route,
            route = navGraph.route,
            builder = builder,
        )
    }
}
