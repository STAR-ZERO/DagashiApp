package com.star_zero.dagashi.ui.setting

import androidx.compose.animation.AnimatedContentScope
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.navigation.NavBackStackEntry
import com.ramcosta.composedestinations.spec.DestinationStyle
import com.star_zero.dagashi.ui.destinations.MilestoneScreenDestination
import com.star_zero.dagashi.ui.navDestination

@OptIn(ExperimentalAnimationApi::class)
object SettingTransitions : DestinationStyle.Animated {

    override fun AnimatedContentScope<NavBackStackEntry>.enterTransition(): EnterTransition? {
        return when (initialState.navDestination) {
            MilestoneScreenDestination -> {
                slideInVertically(
                    initialOffsetY = { it },
                    animationSpec = tween(
                        durationMillis = 200,
                        easing = LinearEasing
                    )
                )
            }
            else -> null
        }
    }

    override fun AnimatedContentScope<NavBackStackEntry>.popExitTransition(): ExitTransition? {
        return when (targetState.navDestination) {
            MilestoneScreenDestination -> {
                slideOutVertically(
                    targetOffsetY = { it },
                    animationSpec = tween(
                        durationMillis = 200,
                        easing = LinearEasing
                    )
                )
            }
            else -> null
        }
    }
}
