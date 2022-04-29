package com.star_zero.dagashi.ui

import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.star_zero.dagashi.features.favorite.FavoriteNavigator
import com.star_zero.dagashi.features.favorite.destinations.FavoriteScreenDestination
import com.star_zero.dagashi.features.issue.IssueNavigator
import com.star_zero.dagashi.features.issue.destinations.IssueScreenDestination
import com.star_zero.dagashi.features.milestone.MilestoneNavigator
import com.star_zero.dagashi.features.setting.SettingNavigator
import com.star_zero.dagashi.features.setting.destinations.SettingScreenDestination
import com.star_zero.dagashi.shared.model.Milestone

class AppNavigator(
    private val navigator: DestinationsNavigator
) : MilestoneNavigator, IssueNavigator, FavoriteNavigator, SettingNavigator {

    override fun navigateBack() {
        navigator.popBackStack()
    }

    override fun navigateMilestoneToFavorite() {
        navigator.navigate(FavoriteScreenDestination())
    }

    override fun navigateMilestoneToSetting() {
        navigator.navigate(SettingScreenDestination())
    }

    override fun navigateMilestoneToIssue(milestone: Milestone) {
        navigator.navigate(IssueScreenDestination(milestone))
    }
}
