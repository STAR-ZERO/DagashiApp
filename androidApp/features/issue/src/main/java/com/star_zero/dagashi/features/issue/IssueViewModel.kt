package com.star_zero.dagashi.features.issue

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
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
import kotlinx.coroutines.flow.combine
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

    private var _uiState by mutableStateOf(IssueUiState())

    val uiState: Flow<IssueUiState> = combine(
        snapshotFlow { _uiState },
        getIssueWithFavoriteUseCase.issueItems,
        settingRepository.flowOpenLinkInApp
    ) { uiState, issueItems, isOpenLinkInApp ->
        uiState.copy(
            issues = issueItems,
            isOpenLinkInApp = isOpenLinkInApp
        )
    }

    init {
        getIssues()
    }

    fun getIssues() {
        if (_uiState.loading) {
            return
        }

        viewModelScope.launch {
            try {
                _uiState = _uiState.copy(loading = true, error = false)
                getIssueWithFavoriteUseCase()
            } catch (e: Exception) {
                e.printStackTrace()
                _uiState = _uiState.copy(error = true)
            } finally {
                _uiState = _uiState.copy(loading = false)
            }
        }
    }

    fun toggleFavorite(issue: Issue, isFavorite: Boolean) {
        viewModelScope.launch {
            if (isFavorite) {
                favoriteIssueRepository.deleteFavorite(issue)
            } else {
                favoriteIssueRepository.addFavorite(milestone.id, issue)
            }
        }
    }
}
