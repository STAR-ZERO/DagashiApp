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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import com.google.accompanist.navigation.material.ExperimentalMaterialNavigationApi
import com.ramcosta.composedestinations.navigation.navigateTo
import com.ramcosta.composedestinations.spec.DirectionDestinationSpec
import com.star_zero.dagashi.R
import com.star_zero.dagashi.features.favorite.destinations.FavoriteScreenDestination
import com.star_zero.dagashi.features.milestone.destinations.MilestoneScreenDestination
import com.star_zero.dagashi.features.setting.destinations.SettingScreenDestination

@OptIn(
    ExperimentalAnimationApi::class,
    ExperimentalMaterialNavigationApi::class
)
@Composable
fun AppBottomBar(
    navController: NavController,
    content: @Composable (PaddingValues) -> Unit
) {
    var selectedItem by remember { mutableStateOf(0) }

    val tabs = listOf(
        BottomTab.Home,
        BottomTab.Favorite,
        BottomTab.Setting,
    )

    Scaffold(
        bottomBar = {
            BottomNavigation {
                tabs.forEachIndexed { index, tab ->
                    val label = stringResource(id = tab.label)
                    BottomNavigationItem(
                        selected = selectedItem == index,
                        onClick = {
                            navController.navigateTo(tab.destination) {
                                popUpTo(navController.graph.findStartDestination().id) {
                                    saveState = true
                                }
                                launchSingleTop = true
                                restoreState = true
                            }
                            selectedItem = index
                        },
                        icon = {
                            Icon(imageVector = tab.icon, contentDescription = label)
                        },
                        label = {
                            Text(text = label)
                        }
                    )
                }
            }
        },
        content = content
    )
}

sealed class BottomTab(
    val destination: DirectionDestinationSpec,
    val icon: ImageVector,
    val label: Int,
) {
    object Home : BottomTab(
        destination = MilestoneScreenDestination,
        icon = Icons.Filled.Home,
        label = R.string.tab_home
    )

    object Favorite : BottomTab(
        destination = FavoriteScreenDestination,
        icon = Icons.Filled.Favorite,
        label = R.string.tab_favorite
    )

    object Setting : BottomTab(
        destination = SettingScreenDestination,
        icon = Icons.Filled.Settings,
        label = R.string.tab_setting
    )
}
