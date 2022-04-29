package com.star_zero.dagashi.features.favorite

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.star_zero.dagashi.shared.model.Issue
import com.star_zero.dagashi.shared.repoitory.FavoriteIssueRepository
import com.star_zero.dagashi.shared.repoitory.SettingRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoriteViewModel @Inject constructor(
    private val favoriteIssueRepository: FavoriteIssueRepository,
    settingRepository: SettingRepository
) : ViewModel() {

    var uiState by mutableStateOf(FavoriteUiState())
        private set

    init {
        getFavorites()

        viewModelScope.launch {
            uiState = uiState.copy(
                isOpenLinkInApp = settingRepository.isOpenLinkInApp()
            )
        }
    }

    fun toggleFavorite(issue: Issue, milestoneId: String, isFavorite: Boolean) {
        if (isFavorite) {
            favoriteIssueRepository.deleteFavorite(issue)
        } else {
            favoriteIssueRepository.addFavorite(milestoneId, issue)
        }

        // Update ui state
        val newFavorites = uiState.favorites.toMutableList()
        val index = newFavorites.indexOfFirst { it.issue.url == issue.url }
        newFavorites[index] = newFavorites[index].copy(
            isFavorite = !isFavorite
        )
        uiState = uiState.copy(favorites = newFavorites.toList())
    }

    fun deleteAllFavorites() {
        viewModelScope.launch {
            favoriteIssueRepository.deleteAllFavorites()
            getFavorites()
        }
    }

    private fun getFavorites() {
        viewModelScope.launch {
            val favorites = favoriteIssueRepository.getFavorites().map { favoriteIssue ->
                FavoriteItemUiState(
                    issue = favoriteIssue.issue,
                    milestoneId = favoriteIssue.milestoneId,
                    isFavorite = true
                )
            }
            uiState = uiState.copy(favorites = favorites)
        }
    }
}
