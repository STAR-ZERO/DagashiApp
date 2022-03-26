package com.star_zero.dagashi.ui.issue

import androidx.compose.animation.AnimatedContentScope
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.navigation.NavBackStackEntry
import com.ramcosta.composedestinations.spec.DestinationStyle
import com.star_zero.dagashi.ui.destinations.MilestoneScreenDestination
import com.star_zero.dagashi.ui.navDestination

@OptIn(ExperimentalAnimationApi::class)
object IssueTransitions : DestinationStyle.Animated {

    override fun AnimatedContentScope<NavBackStackEntry>.enterTransition(): EnterTransition? {
        return when (initialState.navDestination) {
            MilestoneScreenDestination -> {
                slideInHorizontally(
                    initialOffsetX = { it },
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
                slideOutHorizontally(
                    targetOffsetX = { it },
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
