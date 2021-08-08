package com.star_zero.dagashi.ui.main

import android.os.Bundle
import android.util.Log
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.slideOutVertically
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavType
import androidx.navigation.compose.navArgument
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.composable
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import com.star_zero.dagashi.core.data.repository.DagashiRepository
import com.star_zero.dagashi.core.data.repository.SettingRepository
import com.star_zero.dagashi.ui.issue.IssueScreen
import com.star_zero.dagashi.ui.issue.IssueViewModel
import com.star_zero.dagashi.ui.milestone.MilestoneScreen
import com.star_zero.dagashi.ui.milestone.MilestoneViewModel
import com.star_zero.dagashi.ui.setting.SettingScreen
import com.star_zero.dagashi.ui.setting.SettingViewModel
import com.star_zero.dagashi.ui.theme.DagashiAppTheme
import com.star_zero.dagashi.ui.util.AppNavigator
import com.star_zero.dagashi.ui.util.LocalNavigator
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var dagashiRepository: DagashiRepository

    @Inject
    lateinit var settingRepository: SettingRepository

    @ExperimentalAnimationApi
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
                            exitTransition = { _, target ->
                                when (target.destination.route) {
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
                            popEnterTransition = { initial, _ ->
                                when (initial.destination.route) {
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
                            MilestoneNav()
                        }
                        composable(
                            "issue/{path}/{title}",
                            arguments = listOf(
                                navArgument("path") { type = NavType.StringType },
                                navArgument("title") { type = NavType.StringType },
                            ),
                            enterTransition = { initial, _ ->
                                when (initial.destination.route) {
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
                            popExitTransition = { _, target ->
                                when (target.destination.route) {
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
                            IssueNav(path, title)
                        }
                        composable(
                            "setting",
                            enterTransition = { initial, _ ->
                                when (initial.destination.route) {
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
                            popExitTransition = { _, target ->
                                Log.d("HOGE", "v ${target.destination.route}")
                                when (target.destination.route) {
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
                            SettingNav()
                        }
                    }
                }
            }
        }
    }

    @Composable
    private fun MilestoneNav() {
        val viewModel: MilestoneViewModel = hiltViewModel()
        val uiState by viewModel.uiState.collectAsState()

        LaunchedEffect(null) {
            viewModel.getMilestones(false)
        }

        MilestoneScreen(
            uiState = uiState,
            onRefresh = {
                viewModel.refresh()
            }
        )
    }

    @Composable
    private fun IssueNav(path: String, title: String) {
        val viewModel: IssueViewModel = hiltViewModel()
        val uiState by viewModel.uiState.collectAsState()
        val isOpenLinkInApp by viewModel.isOpenLinkInApp.collectAsState(initial = false)

        LaunchedEffect(null) {
            viewModel.getIssues(path)
        }

        IssueScreen(
            uiState = uiState,
            title = title,
            isOpenLinkInApp = isOpenLinkInApp,
            onRefresh = {
                viewModel.refresh(path)
            }
        )
    }

    @Composable
    private fun SettingNav() {
        val viewModel: SettingViewModel = hiltViewModel()
        val isOpenLinkInApp by viewModel.isOpenLinkInApp.collectAsState(initial = false)
        SettingScreen(
            isOpenLinkInApp = isOpenLinkInApp,
            updateOpenLinkInApp = {
                viewModel.updateOpenLinkInApp(it)
            }
        )
    }

    companion object {
        private const val NAV_ANIMATION_DURATION = 300
    }
}
