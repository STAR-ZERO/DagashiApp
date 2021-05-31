package com.star_zero.dagashi.ui.issue

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.AbstractSavedStateViewModelFactory
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.savedstate.SavedStateRegistryOwner
import com.star_zero.dagashi.core.data.repository.DagashiRepository
import com.star_zero.dagashi.core.data.repository.SettingRepository
import com.star_zero.dagashi.shared.model.Issue
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.map
import javax.inject.Inject

@HiltViewModel
class IssueViewModel @Inject constructor(
    private val dagashiDataRepository: DagashiRepository,
    private val settingRepository: SettingRepository
) : ViewModel() {

    val isOpenLinkInApp = settingRepository.settingsFlow.map { it.openLinkInApp }

    var issues: List<Issue> by mutableStateOf(listOf())
        private set

    var loading: Boolean by mutableStateOf(false)
        private set

    var hasError by mutableStateOf(false)
        private set

    suspend fun getIssues(path: String) {
        if (issues.isNotEmpty() || loading) {
            return
        }

        try {
            hasError = false
            loading = true
            issues = dagashiDataRepository.issues(path)
        } catch (e: Exception) {
            e.printStackTrace()
            hasError = true
        }

        loading = false
    }

    suspend fun refresh(path: String) {
        issues = listOf()
        getIssues(path)
    }

    class Factory(
        owner: SavedStateRegistryOwner,
        private val dagashiRepository: DagashiRepository,
        private val settingRepository: SettingRepository
    ) : AbstractSavedStateViewModelFactory(owner, null) {

        override fun <T : ViewModel?> create(
            key: String,
            modelClass: Class<T>,
            handle: SavedStateHandle
        ): T {
            @Suppress("UNCHECKED_CAST")
            return IssueViewModel(
                dagashiRepository,
                settingRepository
            ) as T
        }

    }
}
