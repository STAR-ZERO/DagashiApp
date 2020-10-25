package com.star_zero.dagashi.core.data.repository

import com.star_zero.dagashi.shared.DagashiAPI
import com.star_zero.dagashi.shared.model.Issue
import com.star_zero.dagashi.shared.model.Milestone
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class DagashiDataRepository(
    private val api: DagashiAPI,
) : DagashiRepository {

    override suspend fun milestones(): List<Milestone> = withContext(Dispatchers.IO) {
        api.milestones()
    }

    override suspend fun issues(path: String): List<Issue> = withContext(Dispatchers.IO) {
        api.issues(path)
    }
}
