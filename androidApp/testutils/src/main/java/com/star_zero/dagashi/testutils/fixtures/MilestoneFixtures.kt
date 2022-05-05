package com.star_zero.dagashi.testutils.fixtures

import com.star_zero.dagashi.shared.model.Milestone

object MilestoneFixtures {

    private val milestone1 = Milestone(
        "milestone1",
        "title1",
        "body1",
        "path1",
    )
    private val milestone2 = Milestone(
        "milestone2",
        "title2",
        "body2",
        "path2",
    )
    private val milestone3 = Milestone(
        "milestone3",
        "title3",
        "body3",
        "path3",
    )

    fun single() = milestone1

    fun milestones() = listOf(
        milestone1,
        milestone2,
        milestone3
    )
}
