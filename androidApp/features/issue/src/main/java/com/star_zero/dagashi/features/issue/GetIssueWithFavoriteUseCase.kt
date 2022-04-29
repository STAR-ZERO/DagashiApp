package com.star_zero.dagashi.features.issue

import com.star_zero.dagashi.shared.model.Issue
import com.star_zero.dagashi.shared.model.Milestone
import com.star_zero.dagashi.shared.repoitory.FavoriteIssueRepository
import com.star_zero.dagashi.shared.repoitory.IssueRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.combine

class GetIssueWithFavoriteUseCase(
    private val issueRepository: IssueRepository,
    favoriteIssueRepository: FavoriteIssueRepository,
    private val milestone: Milestone
) {
    private val issueFlow = MutableStateFlow<List<Issue>>(emptyList())

    // Combine issue api response and favorite
    val issueItems: Flow<List<IssueItemUiState>> = combine(
        issueFlow,
        favoriteIssueRepository.flowFavoritesByMilestone(milestone)
    ) { issues, favorites ->
        issues.map { issue ->
            val isFavorite = favorites.any { it.url == issue.url }
            IssueItemUiState(
                issue = issue,
                isFavorite = isFavorite
            )
        }
    }

    suspend operator fun invoke() {
        issueFlow.value = issueRepository.getIssues(milestone.path)
    }
}
