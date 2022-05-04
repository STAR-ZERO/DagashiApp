package com.star_zero.dagashi.features.milestone

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.star_zero.dagashi.shared.repoitory.MilestoneRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import io.github.aakira.napier.Napier
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MilestoneViewModel @Inject constructor(
    private val milestoneRepository: MilestoneRepository
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
                val milestones = milestoneRepository.getMilestone(forceReload)
                uiState = uiState.copy(milestones = milestones)
            } catch (e: Exception) {
                Napier.e("Error get milestone", e)
                uiState = if (uiState.milestones.isEmpty()) {
                    uiState.copy(error = true)
                } else {
                    val newEvents = uiState.events + MilestoneEvent.ErrorGetMilestone
                    uiState.copy(events = newEvents)
                }
            } finally {
                uiState = uiState.copy(loading = false)
            }
        }
    }

    fun refresh() {
        getMilestones(true)
    }

    fun consumeEvents(event: MilestoneEvent) {
        val newEvents = uiState.events.filterNot { it == event }
        uiState = uiState.copy(events = newEvents)
    }
}
