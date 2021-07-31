package com.star_zero.dagashi.ui.issue

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.star_zero.dagashi.core.data.repository.DagashiRepository
import com.star_zero.dagashi.core.data.repository.SettingRepository
import com.star_zero.dagashi.ui.util.update
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class IssueViewModel @Inject constructor(
    private val dagashiDataRepository: DagashiRepository,
    settingRepository: SettingRepository
) : ViewModel() {

    val isOpenLinkInApp = settingRepository.settingsFlow.map { it.openLinkInApp }

    private val _uiState = MutableStateFlow(IssueUiState())
    val uiState = _uiState.asStateFlow()

    fun getIssues(path: String) {
        if (_uiState.value.loading) {
            return
        }

        viewModelScope.launch {
            try {
                _uiState.update { copy(loading = true, error = false) }
                val issues = dagashiDataRepository.issues(path)
                _uiState.update { copy(issues = issues) }
            } catch (e: Exception) {
                e.printStackTrace()
                _uiState.update { copy(error = true) }
            } finally {
                _uiState.update { copy(loading = false) }
            }
        }
    }

    fun refresh(path: String) {
        getIssues(path)
    }
}
