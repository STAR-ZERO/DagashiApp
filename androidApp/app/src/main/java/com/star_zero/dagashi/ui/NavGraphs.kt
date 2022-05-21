package com.star_zero.dagashi.ui

import com.ramcosta.composedestinations.spec.DestinationSpec
import com.ramcosta.composedestinations.spec.NavGraphSpec
import com.ramcosta.composedestinations.spec.Route
import com.star_zero.dagashi.features.favorite.destinations.FavoriteScreenDestination
import com.star_zero.dagashi.features.issue.destinations.IssueScreenDestination
import com.star_zero.dagashi.features.milestone.destinations.MilestoneScreenDestination
import com.star_zero.dagashi.features.setting.destinations.SettingScreenDestination

object NavGraphs {
    val topHome = object : NavGraphSpec {
        override val route: String = "top_home"
        override val startRoute: Route = MilestoneScreenDestination
        override val destinationsByRoute: Map<String, DestinationSpec<*>> = listOf(
            MilestoneScreenDestination,
            IssueScreenDestination,
        ).associateBy { it.route }
    }

    val topFavorite = object : NavGraphSpec {
        override val route: String = "top_favorite"
        override val startRoute: Route = FavoriteScreenDestination
        override val destinationsByRoute: Map<String, DestinationSpec<*>> = listOf(
            FavoriteScreenDestination,
        ).associateBy { it.route }
    }

    val topSetting = object : NavGraphSpec {
        override val route: String = "top_setting"
        override val startRoute: Route = SettingScreenDestination
        override val destinationsByRoute: Map<String, DestinationSpec<*>> = listOf(
            SettingScreenDestination,
        ).associateBy { it.route }
    }

    val root = object : NavGraphSpec {
        override val route: String = "root"
        override val startRoute: Route = topHome
        override val destinationsByRoute: Map<String, DestinationSpec<*>> = emptyMap()
        override val nestedNavGraphs: List<NavGraphSpec> = listOf(
            topHome,
            topFavorite,
            topSetting
        )
    }
}
