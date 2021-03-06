package com.star_zero.dagashi.ui.milestone

import com.star_zero.dagashi.shared.model.Milestone

data class MilestoneUiState(
    val milestones: List<Milestone> = listOf(),
    val loading: Boolean = false,
    val error: Boolean = false
)