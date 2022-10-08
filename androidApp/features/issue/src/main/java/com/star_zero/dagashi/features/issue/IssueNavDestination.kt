package com.star_zero.dagashi.features.issue

import androidx.navigation.NavController
import androidx.navigation.NavType
import androidx.navigation.navArgument
import com.star_zero.dagashi.core.ui.DagashiNavDestination
import com.star_zero.dagashi.shared.model.Milestone

object IssueNavDestination : DagashiNavDestination {
    const val ARG_MILESTONE_ID = "milestoneId"
    const val ARG_MILESTONE_PATH = "milestonePath"
    const val ARG_MILESTONE_TITLE = "milestoneTitle"
    const val ARG_MILESTONE_TWO_PANE = "milestoneTwoPane"

    private const val ROUTE_PATH = "issue"

    // "issue/{milestoneId}/{milestonePath}/{milestoneTitle}?milestoneTwoPane={milestoneTwoPane}
    override val route: String = buildString {
        append("$ROUTE_PATH/{$ARG_MILESTONE_ID}/{$ARG_MILESTONE_PATH}/{$ARG_MILESTONE_TITLE}")
        append("?$ARG_MILESTONE_TWO_PANE={$ARG_MILESTONE_TWO_PANE}")
    }

    val arguments = listOf(
        navArgument(ARG_MILESTONE_ID) { type = NavType.StringType },
        navArgument(ARG_MILESTONE_PATH) { type = NavType.StringType },
        navArgument(ARG_MILESTONE_TITLE) { type = NavType.StringType },
        navArgument(ARG_MILESTONE_TWO_PANE) {
            type = NavType.BoolType
            defaultValue = false
        }
    )

    fun navigate(
        navController: NavController,
        milestone: Milestone,
        isTwoPane: Boolean = false
    ) {
        navController.navigate(
            "$ROUTE_PATH/${milestone.id}/${milestone.path}/${milestone.title}?$ARG_MILESTONE_TWO_PANE=$isTwoPane"
        )
    }
}
