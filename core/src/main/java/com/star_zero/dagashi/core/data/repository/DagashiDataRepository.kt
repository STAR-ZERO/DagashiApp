package com.star_zero.dagashi.core.data.repository

import com.star_zero.dagashi.core.data.api.DagashiService
import com.star_zero.dagashi.core.data.model.Issue
import com.star_zero.dagashi.core.data.model.Milestone
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class DagashiDataRepository(
    private val service: DagashiService,
) : DagashiRepository {

    override suspend fun milestones(): List<Milestone> = withContext(Dispatchers.IO) {
        val milestoneRoot = service.milestone()
        milestoneRoot.milestones.nodes.map {
            Milestone(
                it.id,
                it.title,
                it.description,
                it.path
            )
        }
    }

    override suspend fun issues(path: String): List<Issue> = withContext(Dispatchers.IO) {
        val issueRoot = service.issue(path)
        issueRoot.issues.nodes.map {
            Issue(
                it.url,
                it.title,
                it.body
            )
        }
    }
}
