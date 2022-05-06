package com.star_zero.dagashi.ui.main

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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import androidx.navigation.NavGraph
import com.star_zero.dagashi.R
import com.star_zero.dagashi.ui.NavGraphs

@Composable
fun AppBottomBar(
    navController: NavController,
    content: @Composable (PaddingValues) -> Unit
) {
    Scaffold(
        bottomBar = {

            var selectedIndex by rememberSaveable { mutableStateOf(0) }

            BottomNavigation {
                tabs.forEachIndexed { index, tab ->
                    val label = stringResource(id = tab.label)
                    BottomNavigationItem(
                        selected = selectedIndex == index,
                        onClick = {
                            if (selectedIndex == index) {
                                // re-select
                                (navController.graph.findNode(tab.route) as? NavGraph)?.let {
                                    // Pop up to the start destination of NavGraph
                                    navController.popBackStack(it.startDestinationId, false)
                                }
                            } else {
                                navController.navigate(tab.route) {
                                    popUpTo(navController.graph.id) {
                                        saveState = true
                                    }
                                    launchSingleTop = true
                                    restoreState = true
                                }
                                selectedIndex = index
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
