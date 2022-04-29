package com.star_zero.dagashi.features.issue

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.star_zero.dagashi.shared.model.Issue
import com.star_zero.dagashi.shared.model.Milestone
import com.star_zero.dagashi.shared.repoitory.FavoriteIssueRepository
import com.star_zero.dagashi.shared.repoitory.IssueRepository
import com.star_zero.dagashi.shared.repoitory.SettingRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class IssueViewModel @Inject constructor(
    issueRepository: IssueRepository,
    private val favoriteIssueRepository: FavoriteIssueRepository,
    settingRepository: SettingRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    // from screen args
    private val milestone: Milestone = savedStateHandle.get("milestone")!!

    private val getIssueWithFavoriteUseCase = GetIssueWithFavoriteUseCase(
        issueRepository,
        favoriteIssueRepository,
        milestone
    )

    private val issueItemUiState: Flow<List<IssueItemUiState>> =
        getIssueWithFavoriteUseCase.issueItems

    var uiState by mutableStateOf(IssueUiState())
        private set

    init {
        getIssues()

        viewModelScope.launch {
            uiState = uiState.copy(
                isOpenLinkInApp = settingRepository.isOpenLinkInApp()
            )

            issueItemUiState.collect { issueItems ->
                uiState = uiState.copy(
                    issues = issueItems
                )
            }
        }
    }

    fun getIssues() {
        if (uiState.loading) {
            return
        }

        viewModelScope.launch {
            try {
                uiState = uiState.copy(loading = true, error = false)
                getIssueWithFavoriteUseCase()
            } catch (e: Exception) {
                e.printStackTrace()
                uiState = uiState.copy(error = true)
            } finally {
                uiState = uiState.copy(loading = false)
            }
        }
    }

    fun toggleFavorite(issue: Issue, isFavorite: Boolean) {
        if (isFavorite) {
            favoriteIssueRepository.deleteFavorite(issue)
        } else {
            favoriteIssueRepository.addFavorite(milestone.id, issue)
        }
    }
}
