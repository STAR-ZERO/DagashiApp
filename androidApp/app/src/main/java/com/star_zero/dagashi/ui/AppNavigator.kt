package com.star_zero.dagashi.ui

import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.star_zero.dagashi.features.issue.IssueNavigator
import com.star_zero.dagashi.features.issue.destinations.IssueScreenDestination
import com.star_zero.dagashi.features.milestone.MilestoneNavigator
import com.star_zero.dagashi.features.setting.SettingNavigator
import com.star_zero.dagashi.features.setting.destinations.SettingScreenDestination

class AppNavigator(
    private val navigator: DestinationsNavigator
) : MilestoneNavigator, IssueNavigator, SettingNavigator {

    override fun navigateBack() {
        navigator.popBackStack()
    }

    override fun navigateMilestoneToSetting() {
        navigator.navigate(SettingScreenDestination())
    }

    override fun navigateMilestoneToIssue(path: String, title: String) {
        navigator.navigate(
            IssueScreenDestination(
                path = path,
                title = title
            )
        )
    }
}
