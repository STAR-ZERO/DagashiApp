package com.star_zero.dagashi.ui.issue

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.star_zero.dagashi.core.data.repository.DagashiRepository
import com.star_zero.dagashi.core.data.repository.SettingRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class IssueViewModel @Inject constructor(
    private val dagashiDataRepository: DagashiRepository,
    settingRepository: SettingRepository
) : ViewModel() {

    var uiState by mutableStateOf(IssueUiState())
        private set

    init {
        viewModelScope.launch {
            uiState = uiState.copy(
                isOpenLinkInApp = settingRepository.settingsFlow.first().openLinkInApp
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
                val issues = dagashiDataRepository.issues(path)
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
