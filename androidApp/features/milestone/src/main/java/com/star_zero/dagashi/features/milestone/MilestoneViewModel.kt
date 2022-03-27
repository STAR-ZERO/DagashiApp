package com.star_zero.dagashi.features.milestone

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.star_zero.dagashi.core.data.repository.DagashiRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MilestoneViewModel @Inject constructor(
    private val dagashiRepository: DagashiRepository
) : ViewModel() {

    var uiState by mutableStateOf(MilestoneUiState())
        private set

    fun getMilestones(forceReload: Boolean) {
        if (uiState.loading) {
            return
        }

        viewModelScope.launch {
            try {
                uiState = uiState.copy(loading = true, error = false)
                val milestones = dagashiRepository.milestones(forceReload)
                uiState = uiState.copy(milestones = milestones)
            } catch (e: Exception) {
                e.printStackTrace()
                uiState = uiState.copy(error = true)
            } finally {
                uiState = uiState.copy(loading = false)
            }
        }
    }

    fun refresh() {
        getMilestones(true)
    }
}
