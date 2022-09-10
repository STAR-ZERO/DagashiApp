package com.star_zero.dagashi.ui.navigation

import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.star_zero.dagashi.features.favorite.FavoriteNavDestination
import com.star_zero.dagashi.features.favorite.FavoriteRoute
import com.star_zero.dagashi.features.issue.IssueNavDestination
import com.star_zero.dagashi.features.issue.IssueRoute
import com.star_zero.dagashi.features.milestone.MilestoneNavDestination
import com.star_zero.dagashi.features.milestone.MilestoneRoute
import com.star_zero.dagashi.features.setting.SettingNavDestination
import com.star_zero.dagashi.features.setting.SettingRoute

@Composable
fun DagashiNavHost(
    navController: NavHostController,
    windowSizeClass: WindowSizeClass,
    modifier: Modifier = Modifier
) {
    NavHost(
        navController = navController,
        startDestination = MilestoneNavDestination.route,
        modifier = modifier
    ) {
        composable(MilestoneNavDestination.route) {
            MilestoneRoute(
                navigateIssue = { milestone ->
                    IssueNavDestination.navigate(navController, milestone)
                }
            )
        }

        composable(
            IssueNavDestination.route,
            arguments = listOf(
                navArgument(IssueNavDestination.ARG_MILESTONE_ID) { type = NavType.StringType },
                navArgument(IssueNavDestination.ARG_MILESTONE_TITLE) { type = NavType.StringType }
            )
        ) {
            IssueRoute(navigateBack = navController::popBackStack)
        }

        composable(FavoriteNavDestination.route) {
            FavoriteRoute()
        }

        composable(SettingNavDestination.route) {
            SettingRoute(windowSizeClass = windowSizeClass)
        }
    }
}
