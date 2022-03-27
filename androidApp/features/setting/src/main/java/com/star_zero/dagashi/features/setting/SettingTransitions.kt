package com.star_zero.dagashi.features.setting

import androidx.compose.animation.ExperimentalAnimationApi
import com.ramcosta.composedestinations.spec.DestinationStyle

@OptIn(ExperimentalAnimationApi::class)
object SettingTransitions : DestinationStyle.Animated {

//    override fun AnimatedContentScope<NavBackStackEntry>.enterTransition(): EnterTransition? {
//        return when (initialState.navDestination) {
//            MilestoneScreenDestination -> {
//                slideInVertically(
//                    initialOffsetY = { it },
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
//                slideOutVertically(
//                    targetOffsetY = { it },
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
