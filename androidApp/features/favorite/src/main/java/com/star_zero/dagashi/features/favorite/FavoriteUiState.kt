package com.star_zero.dagashi.features.favorite

import com.star_zero.dagashi.shared.model.Issue

data class FavoriteUiState(
    val favorites: List<FavoriteItemUiState> = emptyList(),
    val isOpenLinkInApp: Boolean = false
)

data class FavoriteItemUiState(
    val issue: Issue,
    val milestoneId: String,
    val isFavorite: Boolean
)
