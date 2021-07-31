package com.star_zero.dagashi.ui.milestone

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.star_zero.dagashi.core.data.repository.DagashiRepository
import com.star_zero.dagashi.ui.util.update
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MilestoneViewModel @Inject constructor(
    private val dagashiRepository: DagashiRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(MilestoneUiState())
    val uiState = _uiState.asStateFlow()

    fun getMilestones(forceReload: Boolean) {
        if (_uiState.value.loading) {
            return
        }

        viewModelScope.launch {
            try {
                _uiState.update {
                    copy(loading = true, error = false)
                }
                val milestones = dagashiRepository.milestones(forceReload)
                _uiState.update { copy(milestones = milestones) }
            } catch (e: Exception) {
                e.printStackTrace()
                _uiState.update { copy(error = true) }
            } finally {
                _uiState.update { copy(loading = false) }
            }
        }
    }

    fun refresh() {
        getMilestones(true)
    }
}
