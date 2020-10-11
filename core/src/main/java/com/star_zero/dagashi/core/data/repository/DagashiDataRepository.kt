package com.star_zero.dagashi.core.data.repository

import com.star_zero.dagashi.core.data.model.Author
import com.star_zero.dagashi.core.data.model.Comment
import com.star_zero.dagashi.core.data.model.Issue
import com.star_zero.dagashi.core.data.model.Label
import com.star_zero.dagashi.core.data.model.Milestone
import com.star_zero.dagashi.shared.DagashiAPI
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class DagashiDataRepository(
    private val api: DagashiAPI,
) : DagashiRepository {

    override suspend fun milestones(): List<Milestone> = withContext(Dispatchers.IO) {
        val milestoneRoot = api.milestone()
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
        val issueRoot = api.issue(path)
        issueRoot.issues.nodes.map { issueNode ->
            Issue(
                issueNode.url,
                issueNode.title,
                issueNode.body,
                issueNode.labels.nodes.map { labelNode ->
                    Label(
                        labelNode.name,
                        // Append alpha and convert hex string into long
                        "FF${labelNode.color}".toLong(radix = 16)
                    )
                },
                issueNode.comments.nodes.map { commentNode ->
                    Comment(
                        commentNode.body,
                        Author(
                            commentNode.author.login,
                            commentNode.author.url,
                            commentNode.author.avatarUrl
                        )
                    )
                }
            )
        }
    }
}
