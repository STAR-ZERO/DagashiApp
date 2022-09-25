package com.star_zero.dagashi.shared.repoitory

import com.star_zero.dagashi.shared.api.DagashiAPI
import com.star_zero.dagashi.shared.model.Issue

class IssueRepository(
    private val dagashiAPI: DagashiAPI
) {

    suspend fun getIssues(path: String): List<Issue> {
            return dagashiAPI.issues(path)
    }
}
