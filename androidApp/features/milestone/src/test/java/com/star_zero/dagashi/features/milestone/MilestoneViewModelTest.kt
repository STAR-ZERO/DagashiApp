package com.star_zero.dagashi.features.milestone

import com.google.common.truth.Truth.assertThat
import com.star_zero.dagashi.shared.repoitory.MilestoneRepository
import com.star_zero.dagashi.testutils.MainDispatcherRule
import com.star_zero.dagashi.testutils.fixtures.MilestoneFixtures
import com.star_zero.dagashi.testutils.stateValues
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.RelaxedMockK
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class MilestoneViewModelTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    @RelaxedMockK
    lateinit var milestoneRepository: MilestoneRepository

    @Before
    fun setUp() {
        MockKAnnotations.init(this, relaxUnitFun = true)
    }

    @Test
    fun initialize_success() {
        val milestones = MilestoneFixtures.milestones()
        coEvery { milestoneRepository.getMilestone(false) } returns milestones

        val result = stateValues(MilestoneUiState()) {
            MilestoneViewModel(milestoneRepository)
        }

        assertThat(result).hasSize(4)
        assertThat(result[0]).isEqualTo(
            MilestoneUiState(
                milestones = emptyList(),
                loading = false,
                error = false,
                events = emptyList()
            )
        )
        assertThat(result[1]).isEqualTo(
            MilestoneUiState(
                milestones = emptyList(),
                loading = true,
                error = false,
                events = emptyList()
            )
        )
        assertThat(result[2]).isEqualTo(
            MilestoneUiState(
                milestones = milestones,
                loading = true,
                error = false,
                events = emptyList()
            )
        )
        assertThat(result[3]).isEqualTo(
            MilestoneUiState(
                milestones = milestones,
                loading = false,
                error = false,
                events = emptyList()
            )
        )
    }

    @Test
    fun initialize_error() {
        coEvery { milestoneRepository.getMilestone(false) } throws Exception()

        val result = stateValues(MilestoneUiState()) {
            MilestoneViewModel(milestoneRepository)
        }

        assertThat(result).hasSize(4)
        assertThat(result[0]).isEqualTo(
            MilestoneUiState(
                milestones = emptyList(),
                loading = false,
                error = false,
                events = emptyList()
            )
        )
        assertThat(result[1]).isEqualTo(
            MilestoneUiState(
                milestones = emptyList(),
                loading = true,
                error = false,
                events = emptyList()
            )
        )
        assertThat(result[2]).isEqualTo(
            MilestoneUiState(
                milestones = emptyList(),
                loading = true,
                error = true,
                events = emptyList()
            )
        )
        assertThat(result[3]).isEqualTo(
            MilestoneUiState(
                milestones = emptyList(),
                loading = false,
                error = true,
                events = emptyList()
            )
        )
    }

    @Test
    fun refresh_success() {
        val milestonesInitial = listOf(MilestoneFixtures.single())
        val milestones = MilestoneFixtures.milestones()
        coEvery { milestoneRepository.getMilestone(false) } returns milestonesInitial
        coEvery { milestoneRepository.getMilestone(true) } returns milestones

        val viewModel = MilestoneViewModel(milestoneRepository)

        val result = stateValues(viewModel.uiState) {
            viewModel.refresh()
        }

        assertThat(result).hasSize(4)
        assertThat(result[0]).isEqualTo(
            MilestoneUiState(
                milestones = milestonesInitial,
                loading = false,
                error = false,
                events = emptyList()
            )
        )
        assertThat(result[1]).isEqualTo(
            MilestoneUiState(
                milestones = milestonesInitial,
                loading = true,
                error = false,
                events = emptyList()
            )
        )
        assertThat(result[2]).isEqualTo(
            MilestoneUiState(
                milestones = milestones,
                loading = true,
                error = false,
                events = emptyList()
            )
        )
        assertThat(result[3]).isEqualTo(
            MilestoneUiState(
                milestones = milestones,
                loading = false,
                error = false,
                events = emptyList()
            )
        )
    }

    @Test
    fun refresh_error() {
        val milestones = MilestoneFixtures.milestones()
        coEvery { milestoneRepository.getMilestone(false) } returns milestones
        coEvery { milestoneRepository.getMilestone(true) } throws Exception()

        val viewModel = MilestoneViewModel(milestoneRepository)

        val result = stateValues(viewModel.uiState) {
            viewModel.refresh()
        }

        assertThat(result).hasSize(4)
        assertThat(result[0]).isEqualTo(
            MilestoneUiState(
                milestones = milestones,
                loading = false,
                error = false,
                events = emptyList()
            )
        )
        assertThat(result[1]).isEqualTo(
            MilestoneUiState(
                milestones = milestones,
                loading = true,
                error = false,
                events = emptyList()
            )
        )
        assertThat(result[2]).isEqualTo(
            MilestoneUiState(
                milestones = milestones,
                loading = true,
                error = false,
                events = listOf(MilestoneEvent.ErrorGetMilestone)
            )
        )
        assertThat(result[3]).isEqualTo(
            MilestoneUiState(
                milestones = milestones,
                loading = false,
                error = false,
                events = listOf(MilestoneEvent.ErrorGetMilestone)
            )
        )
    }
}
