package com.star_zero.dagashi.ui

import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.star_zero.dagashi.features.issue.IssueNavigator
import com.star_zero.dagashi.features.issue.destinations.IssueRouteDestination
import com.star_zero.dagashi.features.milestone.MilestoneNavigator
import com.star_zero.dagashi.shared.model.Milestone

class AppNavigator(
    private val navigator: DestinationsNavigator
) : MilestoneNavigator, IssueNavigator {

    override fun navigateBack() {
        navigator.popBackStack()
    }

    override fun navigateMilestoneToIssue(milestone: Milestone) {
        navigator.navigate(IssueRouteDestination(milestone))
    }
}
