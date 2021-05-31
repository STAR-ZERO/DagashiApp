package com.star_zero.dagashi.ui.issue

import com.star_zero.dagashi.shared.model.Issue

data class IssueUiState(
    val issues: List<Issue> = listOf(),
    val loading: Boolean = false,
    val error: Boolean = false
)