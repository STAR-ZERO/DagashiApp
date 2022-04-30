package com.star_zero.dagashi.ui.main

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Settings
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.currentBackStackEntryAsState
import com.google.accompanist.navigation.material.ExperimentalMaterialNavigationApi
import com.star_zero.dagashi.R
import com.star_zero.dagashi.ui.NavGraphs

@OptIn(
    ExperimentalAnimationApi::class,
    ExperimentalMaterialNavigationApi::class
)
@Composable
fun AppBottomBar(
    navController: NavController,
    content: @Composable (PaddingValues) -> Unit
) {
    Scaffold(
        bottomBar = {
            BottomNavigation {
                val navBackStackEntry by navController.currentBackStackEntryAsState()
                val currentDestination = navBackStackEntry?.destination

                tabs.forEach { tab ->
                    val label = stringResource(id = tab.label)
                    BottomNavigationItem(
                        selected = currentDestination?.hierarchy?.any { it.route == tab.route } == true,
                        onClick = {
                            navController.navigate(tab.route) {
                                popUpTo(navController.graph.findStartDestination().id) {
                                    saveState = true
                                }
                                launchSingleTop = true
                                restoreState = true
                            }
                        },
                        icon = {
                            Icon(imageVector = tab.icon, contentDescription = label)
                        },
                        label = {
                            Text(text = label)
                        },
                    )
                }
            }
        },
        content = content
    )
}

private val tabs = listOf(
    BottomTab.Home,
    BottomTab.Favorite,
    BottomTab.Setting,
)


sealed class BottomTab(
    val route: String,
    val icon: ImageVector,
    val label: Int,
) {
    object Home : BottomTab(
        route = NavGraphs.tabHome.route,
        icon = Icons.Filled.Home,
        label = R.string.tab_home
    )

    object Favorite : BottomTab(
        route = NavGraphs.tabFavorite.route,
        icon = Icons.Filled.Favorite,
        label = R.string.tab_favorite
    )

    object Setting : BottomTab(
        route = NavGraphs.tabSetting.route,
        icon = Icons.Filled.Settings,
        label = R.string.tab_setting
    )
}
