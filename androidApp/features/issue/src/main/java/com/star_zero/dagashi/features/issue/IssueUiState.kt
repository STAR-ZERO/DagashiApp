package com.star_zero.dagashi.features.issue

import com.star_zero.dagashi.shared.model.Issue

data class IssueUiState(
    val issues: List<IssueItemUiState> = listOf(),
    val isOpenLinkInApp: Boolean = false,
    val loading: Boolean = false,
    val error: Boolean = false
)

data class IssueItemUiState(
    val issue: Issue,
    val isFavorite: Boolean
)
