package com.star_zero.dagashi.features.issue

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.star_zero.dagashi.shared.repoitory.IssueRepository
import com.star_zero.dagashi.shared.repoitory.SettingRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class IssueViewModel @Inject constructor(
    private val issueRepository: IssueRepository,
    settingRepository: SettingRepository
) : ViewModel() {

    var uiState by mutableStateOf(IssueUiState())
        private set

    init {
        viewModelScope.launch {
            uiState = uiState.copy(
                isOpenLinkInApp = settingRepository.isOpenLinkInApp()
            )
        }
    }

    fun getIssues(path: String) {
        if (uiState.loading) {
            return
        }

        viewModelScope.launch {
            try {
                uiState = uiState.copy(loading = true, error = false)
                val issues = issueRepository.getIssues(path)
                uiState = uiState.copy(issues = issues)
            } catch (e: Exception) {
                e.printStackTrace()
                uiState = uiState.copy(error = true)
            } finally {
                uiState = uiState.copy(loading = false)
            }
        }
    }

    fun refresh(path: String) {
        getIssues(path)
    }
}
