package com.star_zero.dagashi.features.issue

import com.star_zero.dagashi.shared.model.Issue
import com.star_zero.dagashi.shared.repoitory.FavoriteIssueRepository
import com.star_zero.dagashi.shared.repoitory.IssueRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.combine

class GetIssueWithFavoriteUseCase(
    private val issueRepository: IssueRepository,
    favoriteIssueRepository: FavoriteIssueRepository,
    milestoneId: String,
    private val milestonePath: String
) {
    private val issueFlow = MutableStateFlow<List<Issue>>(emptyList())

    // Combine issue api response and favorite
    val issueItems: Flow<List<IssueItemUiState>> = combine(
        issueFlow,
        favoriteIssueRepository.flowFavoriteUrlsByMilestone(milestoneId)
    ) { issues, favoriteUrls ->
        issues.map { issue ->
            val isFavorite = favoriteUrls.any { it == issue.url }
            IssueItemUiState(
                issue = issue,
                isFavorite = isFavorite
            )
        }
    }

    suspend operator fun invoke() {
        issueFlow.value = issueRepository.getIssues(milestonePath)
    }
}
