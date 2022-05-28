package com.star_zero.dagashi.ui

import com.ramcosta.composedestinations.spec.DestinationSpec
import com.ramcosta.composedestinations.spec.NavGraphSpec
import com.ramcosta.composedestinations.spec.Route
import com.star_zero.dagashi.features.favorite.destinations.FavoriteRouteDestination
import com.star_zero.dagashi.features.issue.destinations.IssueRouteDestination
import com.star_zero.dagashi.features.milestone.destinations.MilestoneRouteDestination
import com.star_zero.dagashi.features.setting.destinations.SettingRouteDestination

object NavGraphs {
    val topHome = object : NavGraphSpec {
        override val route: String = "top_home"
        override val startRoute: Route = MilestoneRouteDestination
        override val destinationsByRoute: Map<String, DestinationSpec<*>> = listOf(
            MilestoneRouteDestination,
            IssueRouteDestination,
        ).associateBy { it.route }
    }

    val topFavorite = object : NavGraphSpec {
        override val route: String = "top_favorite"
        override val startRoute: Route = FavoriteRouteDestination
        override val destinationsByRoute: Map<String, DestinationSpec<*>> = listOf(
            FavoriteRouteDestination,
        ).associateBy { it.route }
    }

    val topSetting = object : NavGraphSpec {
        override val route: String = "top_setting"
        override val startRoute: Route = SettingRouteDestination
        override val destinationsByRoute: Map<String, DestinationSpec<*>> = listOf(
            SettingRouteDestination,
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
