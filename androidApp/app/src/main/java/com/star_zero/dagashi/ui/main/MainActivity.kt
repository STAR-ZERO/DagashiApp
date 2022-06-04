package com.star_zero.dagashi.ui.main

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.WindowInsetsSides
import androidx.compose.foundation.layout.consumedWindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.only
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.material3.windowsizeclass.calculateWindowSizeClass
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.core.view.WindowCompat
import com.google.accompanist.navigation.material.ExperimentalMaterialNavigationApi
import com.ramcosta.composedestinations.DestinationsNavHost
import com.ramcosta.composedestinations.navigation.dependency
import com.star_zero.dagashi.core.ui.theme.DagashiAppTheme
import com.star_zero.dagashi.features.issue.destinations.IssueRouteDestination
import com.star_zero.dagashi.features.milestone.destinations.MilestoneRouteDestination
import com.star_zero.dagashi.shared.model.DarkThemeType
import com.star_zero.dagashi.ui.AppNavigator
import com.star_zero.dagashi.ui.IssueTransitions
import com.star_zero.dagashi.ui.MilestoneTransitions
import com.star_zero.dagashi.ui.NavGraphs
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val viewModel: MainViewModel by viewModels()

    @OptIn(
        ExperimentalAnimationApi::class,
        ExperimentalMaterialNavigationApi::class, ExperimentalMaterial3Api::class,
        ExperimentalMaterial3WindowSizeClassApi::class, ExperimentalLayoutApi::class
    )
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, false)

        setContent {

            val windowSizeClass = calculateWindowSizeClass(activity = this)

            val darkTheme by viewModel.flowDarkTheme.collectAsState(initial = DarkThemeType.DEVICE)

            val isDarkTheme = when (darkTheme) {
                DarkThemeType.DEVICE -> isSystemInDarkTheme()
                DarkThemeType.ON -> true
                DarkThemeType.OFF -> false
            }

            DagashiAppTheme(
                isDarkTheme = isDarkTheme
            ) {
                val navHostEngine = rememberCustomAnimatedNavHostEngine()
                val navController = navHostEngine.rememberNavController()

                var selectedIndex by rememberSaveable { mutableStateOf(0) }
                val topLevelNavigation = remember(navController) {
                    TopLevelNavigation(navController)
                }

                Scaffold(
                    bottomBar = {
                        if (windowSizeClass.widthSizeClass == WindowWidthSizeClass.Compact) {
                            AppBottomBar(
                                topLevelNavigation = topLevelNavigation,
                                selectedIndex = selectedIndex,
                                onSelectedItem = { selectedIndex = it }
                            )
                        }
                    }
                ) { padding ->
                    MilestoneRouteDestination.style = MilestoneTransitions
                    IssueRouteDestination.style = IssueTransitions

                    Row(
                        modifier = Modifier
                            .fillMaxSize()
                            .windowInsetsPadding(
                                WindowInsets.safeDrawing.only(
                                    WindowInsetsSides.Horizontal
                                )
                            )
                    ) {
                        if (windowSizeClass.widthSizeClass != WindowWidthSizeClass.Compact) {
                            AppNavRail(
                                topLevelNavigation = topLevelNavigation,
                                selectedIndex = selectedIndex,
                                onSelectedItem = { selectedIndex = it },
                                modifier = Modifier.safeDrawingPadding()
                            )
                        }

                        DestinationsNavHost(
                            navGraph = NavGraphs.root,
                            navController = navController,
                            engine = navHostEngine,
                            dependenciesContainerBuilder = {
                                dependency(AppNavigator(destinationsNavigator))
                                dependency(windowSizeClass)
                            },
                            modifier = Modifier
                                .padding(padding)
                                .consumedWindowInsets(padding)
                        )
                    }
                }
            }
        }
    }
}
