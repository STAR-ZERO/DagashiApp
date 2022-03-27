package com.star_zero.dagashi.features.milestone

interface MilestoneNavigator {
    fun navigateMilestoneToSetting()
    fun navigateMilestoneToIssue(path: String, title: String)
}
