package com.star_zero.dagashi.ui.main

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.slideOutVertically
import androidx.compose.runtime.CompositionLocalProvider
import androidx.navigation.NavType
import androidx.navigation.navArgument
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.composable
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import com.star_zero.dagashi.ui.issue.IssueScreen
import com.star_zero.dagashi.ui.milestone.MilestoneScreen
import com.star_zero.dagashi.ui.setting.SettingScreen
import com.star_zero.dagashi.ui.theme.DagashiAppTheme
import com.star_zero.dagashi.ui.util.AppNavigator
import com.star_zero.dagashi.ui.util.LocalNavigator
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    @OptIn(ExperimentalAnimationApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            DagashiAppTheme {
                val navController = rememberAnimatedNavController()
                CompositionLocalProvider(LocalNavigator provides AppNavigator(navController)) {
                    AnimatedNavHost(
                        navController = navController,
                        startDestination = "milestone",
                    ) {
                        composable(
                            "milestone",
                            exitTransition = {
                                when (targetState.destination.route) {
                                    "issue/{path}/{title}" -> {
                                        slideOutHorizontally(
                                            targetOffsetX = { -it },
                                            animationSpec = tween(
                                                durationMillis = NAV_ANIMATION_DURATION,
                                                easing = LinearEasing
                                            )
                                        )
                                    }
                                    else -> null
                                }
                            },
                            popEnterTransition = {
                                when (initialState.destination.route) {
                                    "issue/{path}/{title}" -> {
                                        slideInHorizontally(
                                            initialOffsetX = { -it },
                                            animationSpec = tween(
                                                durationMillis = NAV_ANIMATION_DURATION,
                                                easing = LinearEasing
                                            )
                                        )
                                    }
                                    else -> null
                                }
                            },
                        ) {
                            MilestoneScreen()
                        }
                        composable(
                            "issue/{path}/{title}",
                            arguments = listOf(
                                navArgument("path") { type = NavType.StringType },
                                navArgument("title") { type = NavType.StringType },
                            ),
                            enterTransition = {
                                when (initialState.destination.route) {
                                    "milestone" -> {
                                        slideInHorizontally(
                                            initialOffsetX = { it },
                                            animationSpec = tween(
                                                durationMillis = NAV_ANIMATION_DURATION,
                                                easing = LinearEasing
                                            )
                                        )
                                    }
                                    else -> null
                                }
                            },
                            popExitTransition = {
                                when (targetState.destination.route) {
                                    "milestone" -> {
                                        slideOutHorizontally(
                                            targetOffsetX = { it },
                                            animationSpec = tween(
                                                durationMillis = NAV_ANIMATION_DURATION,
                                                easing = LinearEasing
                                            )
                                        )
                                    }
                                    else -> null
                                }
                            }
                        ) { backStackEntry ->
                            val path = backStackEntry.arguments!!.getString("path")!!
                            val title = backStackEntry.arguments!!.getString("title")!!
                            IssueScreen(path, title)
                        }
                        composable(
                            "setting",
                            enterTransition = {
                                when (initialState.destination.route) {
                                    "milestone" -> {
                                        slideInVertically(
                                            initialOffsetY = { it },
                                            animationSpec = tween(
                                                durationMillis = NAV_ANIMATION_DURATION,
                                                easing = LinearEasing
                                            )
                                        )
                                    }
                                    else -> null
                                }
                            },
                            popExitTransition = {
                                when (targetState.destination.route) {
                                    "milestone" -> {
                                        slideOutVertically(
                                            targetOffsetY = { it },
                                            animationSpec = tween(
                                                durationMillis = NAV_ANIMATION_DURATION,
                                                easing = LinearEasing
                                            )
                                        )
                                    }
                                    else -> null
                                }
                            },
                        ) {
                            SettingScreen()
                        }
                    }
                }
            }
        }
    }

    companion object {
        private const val NAV_ANIMATION_DURATION = 200
    }
}
