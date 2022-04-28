package com.star_zero.dagashi.features.milestone

import com.star_zero.dagashi.shared.model.Milestone

interface MilestoneNavigator {
    fun navigateMilestoneToSetting()
    fun navigateMilestoneToIssue(milestone: Milestone)
}
