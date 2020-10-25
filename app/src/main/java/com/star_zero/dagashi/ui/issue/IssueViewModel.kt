package com.star_zero.dagashi.ui.issue

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import com.star_zero.dagashi.core.data.model.Issue
import com.star_zero.dagashi.core.data.repository.DagashiRepository
import com.star_zero.dagashi.core.data.repository.SettingRepository
import com.star_zero.dagashi.shared.model.Milestone
import kotlinx.coroutines.flow.map

class IssueViewModel @ViewModelInject constructor(
    private val dagashiDataRepository: DagashiRepository,
    private val settingRepository: SettingRepository
): ViewModel() {

    val isOpenLinkInApp = settingRepository.settingsFlow.map { it.openLinkInApp }

    var issues: List<Issue> by mutableStateOf(listOf())
        private set

    var loading: Boolean by mutableStateOf(false)
        private set

    var hasError  by mutableStateOf(false)
        private set

    suspend fun getIssues(milestone: Milestone) {
        if (issues.isNotEmpty() || loading) {
            return
        }

        try {
            hasError = false
            loading = true
            issues = dagashiDataRepository.issues(milestone.path)
        } catch (e: Exception) {
            e.printStackTrace()
            hasError = true
        }

        loading = false
    }

    suspend fun refresh(milestone: Milestone) {
        issues = listOf()
        getIssues(milestone)
    }
}
