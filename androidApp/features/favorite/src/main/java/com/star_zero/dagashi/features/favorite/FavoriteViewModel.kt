package com.star_zero.dagashi.features.favorite

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.star_zero.dagashi.shared.model.Issue
import com.star_zero.dagashi.shared.repoitory.FavoriteIssueRepository
import com.star_zero.dagashi.shared.repoitory.SettingRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoriteViewModel @Inject constructor(
    private val favoriteIssueRepository: FavoriteIssueRepository,
    settingRepository: SettingRepository
) : ViewModel() {

    private var _uiState by mutableStateOf(FavoriteUiState())

    val uiState = snapshotFlow {
        _uiState
    }.combine(settingRepository.settingFlow) { uiState, setting ->
        uiState.copy(isOpenLinkInApp = setting.isOpenLinkInApp)
    }

    fun getFavorites() {
        viewModelScope.launch {
            val favorites = favoriteIssueRepository.getFavorites().map { favoriteIssue ->
                FavoriteItemUiState(
                    issue = favoriteIssue.issue,
                    milestoneId = favoriteIssue.milestoneId,
                    isFavorite = true
                )
            }
            _uiState = _uiState.copy(favorites = favorites)
        }
    }

    fun toggleFavorite(issue: Issue, milestoneId: String, isFavorite: Boolean) {
        viewModelScope.launch {
            if (isFavorite) {
                favoriteIssueRepository.deleteFavorite(issue)
            } else {
                favoriteIssueRepository.addFavorite(milestoneId, issue)
            }

            // Update ui state
            val newFavorites = _uiState.favorites.toMutableList()
            val index = newFavorites.indexOfFirst { it.issue.url == issue.url }
            newFavorites[index] = newFavorites[index].copy(
                isFavorite = !isFavorite
            )
            _uiState = _uiState.copy(favorites = newFavorites.toList())
        }
    }

    fun deleteAllFavorites() {
        viewModelScope.launch {
            favoriteIssueRepository.deleteAllFavorites()
            getFavorites()
        }
    }
}
