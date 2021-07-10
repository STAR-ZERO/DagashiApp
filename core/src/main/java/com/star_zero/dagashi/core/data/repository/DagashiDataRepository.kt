package com.star_zero.dagashi.core.data.repository

import com.star_zero.dagashi.shared.DagashiSDK
import com.star_zero.dagashi.shared.model.Issue
import com.star_zero.dagashi.shared.model.Milestone
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DagashiDataRepository @Inject constructor(
    private val dagashiSDK: DagashiSDK,
) : DagashiRepository {

    override suspend fun milestones(forceReload: Boolean): List<Milestone> =
        withContext(Dispatchers.IO) {
            dagashiSDK.getMilestone(forceReload)
        }

    override suspend fun issues(path: String): List<Issue> = withContext(Dispatchers.IO) {
        dagashiSDK.getIssue(path)
    }
}
