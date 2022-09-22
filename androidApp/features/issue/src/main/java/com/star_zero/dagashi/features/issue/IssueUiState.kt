package com.star_zero.dagashi.features.issue

import androidx.compose.runtime.Immutable
import com.star_zero.dagashi.shared.model.Issue

@Immutable
data class IssueUiState(
    val milestoneTitle: String = "",
    val isTwoPane: Boolean = false,
    val issues: List<IssueItemUiState> = listOf(),
    val isOpenLinkInApp: Boolean = false,
    val loading: Boolean = false,
    val error: Boolean = false
)

@Immutable
data class IssueItemUiState(
    val issue: Issue,
    val isFavorite: Boolean
)
