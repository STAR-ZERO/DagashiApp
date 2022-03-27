package com.star_zero.dagashi.features.issue

import androidx.compose.animation.ExperimentalAnimationApi
import com.ramcosta.composedestinations.spec.DestinationStyle

@OptIn(ExperimentalAnimationApi::class)
object IssueTransitions : DestinationStyle.Animated {

//    override fun AnimatedContentScope<NavBackStackEntry>.enterTransition(): EnterTransition? {
//        return when (initialState.navDestination) {
//            MilestoneScreenDestination -> {
//                slideInHorizontally(
//                    initialOffsetX = { it },
//                    animationSpec = tween(
//                        durationMillis = 200,
//                        easing = LinearEasing
//                    )
//                )
//            }
//            else -> null
//        }
//    }
//
//    override fun AnimatedContentScope<NavBackStackEntry>.popExitTransition(): ExitTransition? {
//        return when (targetState.navDestination) {
//            MilestoneScreenDestination -> {
//                slideOutHorizontally(
//                    targetOffsetX = { it },
//                    animationSpec = tween(
//                        durationMillis = 200,
//                        easing = LinearEasing
//                    )
//                )
//            }
//            else -> null
//        }
//    }
}
