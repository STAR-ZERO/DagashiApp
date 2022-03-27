package com.star_zero.dagashi.ui

import com.ramcosta.composedestinations.spec.DestinationSpec
import com.ramcosta.composedestinations.spec.NavGraphSpec
import com.ramcosta.composedestinations.spec.Route
import com.star_zero.dagashi.features.issue.destinations.IssueScreenDestination
import com.star_zero.dagashi.features.milestone.destinations.MilestoneScreenDestination
import com.star_zero.dagashi.features.setting.destinations.SettingScreenDestination

object NavGraphs {
    val root = object : NavGraphSpec {
        override val route: String = "root"
        override val startRoute: Route = MilestoneScreenDestination
        override val destinationsByRoute: Map<String, DestinationSpec<*>> = listOf(
            MilestoneScreenDestination,
            IssueScreenDestination,
            SettingScreenDestination
        ).associateBy { it.route }
    }
}
