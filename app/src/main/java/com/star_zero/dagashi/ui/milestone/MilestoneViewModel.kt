package com.star_zero.dagashi.ui.milestone

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import com.star_zero.dagashi.core.data.repository.DagashiRepository
import com.star_zero.dagashi.shared.model.Milestone

class MilestoneViewModel @ViewModelInject constructor(
    private val dagashiRepository: DagashiRepository
) : ViewModel() {

    var milestones: List<Milestone> by mutableStateOf(listOf())
        private set

    var loading: Boolean by mutableStateOf(false)
        private set

    var hasError  by mutableStateOf(false)
        private set

    suspend fun getMilestones() {
        if (milestones.isNotEmpty() || loading) {
            return
        }

        try {
            hasError = false
            loading = true
            milestones = dagashiRepository.milestones()
        } catch (e: Exception) {
            e.printStackTrace()
            hasError = true
        }

        loading = false
    }

    suspend fun refresh() {
        milestones = listOf()
        getMilestones()
    }
}
