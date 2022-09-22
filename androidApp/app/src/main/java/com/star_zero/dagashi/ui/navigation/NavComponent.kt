package com.star_zero.dagashi.ui.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationRail
import androidx.compose.material3.NavigationRailItem
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavGraph
import com.star_zero.dagashi.R
import com.star_zero.dagashi.features.favorite.FavoriteNavDestination
import com.star_zero.dagashi.features.milestone.MilestoneNavDestination
import com.star_zero.dagashi.features.setting.SettingNavDestination

@Composable
fun AppBottomBar(
    topLevelNavigation: TopLevelNavigation,
    selectedIndex: Int,
    onSelectedItem: (Int) -> Unit
) {
    Surface(
        color = MaterialTheme.colorScheme.surface,
        tonalElevation = 4.dp
    ) {
        NavigationBar(
            tonalElevation = 0.dp,
        ) {
            TOP_LEVEL_DESTINATIONS.forEachIndexed { index, destination ->
                val label = stringResource(id = destination.label)
                NavigationBarItem(
                    selected = selectedIndex == index,
                    onClick = {
                        topLevelNavigation.navigate(
                            index = index,
                            selectedIndex = selectedIndex,
                            onSelectedItem = onSelectedItem
                        )
                    },
                    icon = {
                        Icon(imageVector = destination.icon, contentDescription = label)
                    },
                    label = {
                        Text(text = label)
                    },
                )
            }
        }
    }
}

@Composable
fun AppNavRail(
    topLevelNavigation: TopLevelNavigation,
    selectedIndex: Int,
    onSelectedItem: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    NavigationRail(
        modifier = modifier
    ) {
        TOP_LEVEL_DESTINATIONS.forEachIndexed { index, destination ->
            val label = stringResource(id = destination.label)
            NavigationRailItem(
                selected = selectedIndex == index,
                onClick = {
                    topLevelNavigation.navigate(
                        index = index,
                        selectedIndex = selectedIndex,
                        onSelectedItem = onSelectedItem
                    )
                },
                icon = {
                    Icon(imageVector = destination.icon, contentDescription = label)
                },
                label = {
                    Text(text = label)
                },
            )
        }
    }
}

private val TOP_LEVEL_DESTINATIONS = listOf(
    TopLevelDestination.Home,
    TopLevelDestination.Favorite,
    TopLevelDestination.Setting,
)

enum class TopLevelDestination(
    val route: String,
    val icon: ImageVector,
    val label: Int,
) {
    Home(
        route = MilestoneNavDestination.route,
        icon = Icons.Filled.Home,
        label = R.string.top_home
    ),
    Favorite(
        route = FavoriteNavDestination.route,
        icon = Icons.Filled.Favorite,
        label = R.string.top_favorite
    ),
    Setting(
        route = SettingNavDestination.route,
        icon = Icons.Filled.Settings,
        label = R.string.top_setting
    )
}

class TopLevelNavigation(
    private val navController: NavController
) {
    fun navigate(
        index: Int,
        selectedIndex: Int,
        onSelectedItem: (Int) -> Unit
    ) {
        val route = TOP_LEVEL_DESTINATIONS[index].route

        if (selectedIndex == index) {
            // re-select
            (navController.graph.findNode(route) as? NavGraph)?.let {
                // Pop up to the start destination of NavGraph
                navController.popBackStack(it.startDestinationId, false)
            }
        } else {
            navController.navigate(route) {
                popUpTo(navController.graph.id) {
                    saveState = true
                }
                launchSingleTop = true
                restoreState = true
            }
            onSelectedItem(index)
        }
    }
}
