package com.star_zero.dagashi.ui.main

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.consumedWindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
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
import androidx.navigation.compose.rememberNavController
import com.star_zero.dagashi.core.ui.theme.DagashiAppTheme
import com.star_zero.dagashi.shared.model.DarkThemeType
import com.star_zero.dagashi.ui.navigation.AppBottomBar
import com.star_zero.dagashi.ui.navigation.AppNavRail
import com.star_zero.dagashi.ui.navigation.DagashiNavHost
import com.star_zero.dagashi.ui.navigation.TopLevelNavigation
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val viewModel: MainViewModel by viewModels()

    @OptIn(
        ExperimentalMaterial3WindowSizeClassApi::class,
        ExperimentalLayoutApi::class,
        ExperimentalMaterial3Api::class
    )
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, false)

        setContent {
            val windowSizeClass = calculateWindowSizeClass(activity = this)

            val darkTheme by viewModel.darkThemeFlow.collectAsState(initial = DarkThemeType.DEVICE)
            val isDynamic by viewModel.dynamicThemeEnabledFlow.collectAsState(initial = true)

            val isDarkTheme = when (darkTheme) {
                DarkThemeType.DEVICE -> isSystemInDarkTheme()
                DarkThemeType.ON -> true
                DarkThemeType.OFF -> false
            }

            DagashiAppTheme(
                isDarkTheme = isDarkTheme,
                isDynamic = isDynamic
            ) {
                val navController = rememberNavController()

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
                    Row(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(padding)
                            .consumedWindowInsets(padding)
                    ) {
                        if (windowSizeClass.widthSizeClass != WindowWidthSizeClass.Compact) {
                            AppNavRail(
                                topLevelNavigation = topLevelNavigation,
                                selectedIndex = selectedIndex,
                                onSelectedItem = { selectedIndex = it },
                            )
                        }

                        DagashiNavHost(
                            navController = navController,
                            windowSizeClass = windowSizeClass,
                        )
                    }
                }
            }
        }
    }
}
