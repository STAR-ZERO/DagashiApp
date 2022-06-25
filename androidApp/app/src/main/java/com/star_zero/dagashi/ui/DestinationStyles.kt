package com.star_zero.dagashi.ui

import androidx.compose.animation.AnimatedContentScope
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.navigation.NavBackStackEntry
import com.ramcosta.composedestinations.spec.DestinationSpec
import com.ramcosta.composedestinations.spec.DestinationStyle
import com.ramcosta.composedestinations.utils.destination
import com.star_zero.dagashi.features.issue.destinations.IssueRouteDestination
import com.star_zero.dagashi.features.milestone.destinations.MilestoneRouteDestination

private const val ANIMATION_DURATION = 300

@OptIn(ExperimentalAnimationApi::class)
object MilestoneTransitions : DestinationStyle.Animated {

    override fun AnimatedContentScope<NavBackStackEntry>.exitTransition(): ExitTransition? {
        return when (targetState.destination()) {
            IssueRouteDestination -> {
                slideOutOfContainer(
                    AnimatedContentScope.SlideDirection.Left,
                    animationSpec = tween(
                        durationMillis = ANIMATION_DURATION,
                        easing = LinearEasing
                    )
                )
            }
            else -> null
        }
    }

    override fun AnimatedContentScope<NavBackStackEntry>.popEnterTransition(): EnterTransition? {
        return when (initialState.destination()) {
            IssueRouteDestination -> {
                slideIntoContainer(
                    AnimatedContentScope.SlideDirection.Right,
                    animationSpec = tween(
                        durationMillis = ANIMATION_DURATION,
                        easing = LinearEasing
                    )
                )
            }
            else -> null
        }
    }
}

@OptIn(ExperimentalAnimationApi::class)
object IssueTransitions : DestinationStyle.Animated {

    override fun AnimatedContentScope<NavBackStackEntry>.enterTransition(): EnterTransition? {
        return when (initialState.destination()) {
            MilestoneRouteDestination -> {
                slideIntoContainer(
                    AnimatedContentScope.SlideDirection.Left,
                    animationSpec = tween(
                        durationMillis = ANIMATION_DURATION,
                        easing = LinearEasing
                    )
                )
            }
            else -> null
        }
    }

    override fun AnimatedContentScope<NavBackStackEntry>.popExitTransition(): ExitTransition? {
        return when (targetState.destination()) {
            MilestoneRouteDestination -> {
                slideOutOfContainer(
                    AnimatedContentScope.SlideDirection.Right,
                    animationSpec = tween(
                        durationMillis = ANIMATION_DURATION,
                        easing = LinearEasing
                    )
                )
            }
            else -> null
        }
    }
}
