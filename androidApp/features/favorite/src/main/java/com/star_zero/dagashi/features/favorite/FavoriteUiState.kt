package com.star_zero.dagashi.features.favorite

import androidx.compose.runtime.Immutable
import com.star_zero.dagashi.shared.model.Issue

@Immutable
data class FavoriteUiState(
    val favorites: List<FavoriteItemUiState> = emptyList(),
    val isOpenLinkInApp: Boolean = false
)

@Immutable
data class FavoriteItemUiState(
    val issue: Issue,
    val milestoneId: String,
    val isFavorite: Boolean
)
