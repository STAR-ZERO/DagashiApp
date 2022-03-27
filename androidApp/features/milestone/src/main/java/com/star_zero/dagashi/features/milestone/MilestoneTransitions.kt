package com.star_zero.dagashi.features.milestone

import androidx.compose.animation.ExperimentalAnimationApi
import com.ramcosta.composedestinations.spec.DestinationStyle

@OptIn(ExperimentalAnimationApi::class)
object MilestoneTransitions : DestinationStyle.Animated {

//    override fun AnimatedContentScope<NavBackStackEntry>.exitTransition(): ExitTransition? {
//        return when (targetState.navDestination) {
//            IssueScreenDestination -> {
//                slideOutHorizontally(
//                    targetOffsetX = { -it },
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
//    override fun AnimatedContentScope<NavBackStackEntry>.popEnterTransition(): EnterTransition? {
//        return when (initialState.navDestination) {
//            IssueScreenDestination -> {
//                slideInHorizontally(
//                    initialOffsetX = { -it },
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
