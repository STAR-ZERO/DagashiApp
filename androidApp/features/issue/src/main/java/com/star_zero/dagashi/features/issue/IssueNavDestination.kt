package com.star_zero.dagashi.features.issue

import androidx.navigation.NavController
import com.star_zero.dagashi.core.ui.DagashiNavDestination
import com.star_zero.dagashi.shared.model.Milestone

object IssueNavDestination : DagashiNavDestination {
    const val ARG_MILESTONE_ID = "milestoneId"
    const val ARG_MILESTONE_PATH = "milestonePath"
    const val ARG_MILESTONE_TITLE = "milestoneTitle"

    private const val ROUTE_PATH = "issue"

    override val route: String =
        "$ROUTE_PATH/{$ARG_MILESTONE_ID}/{$ARG_MILESTONE_PATH}/{$ARG_MILESTONE_TITLE}"

    fun navigate(
        navController: NavController,
        milestone: Milestone
    ) {
        navController.navigate(
            "$ROUTE_PATH/${milestone.id}/${milestone.path}/${milestone.title}"
        )
    }
}
