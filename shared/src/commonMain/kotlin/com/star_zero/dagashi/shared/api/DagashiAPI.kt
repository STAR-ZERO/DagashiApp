package com.star_zero.dagashi.shared.api

import com.star_zero.dagashi.shared.api.entity.IssueRootEntity
import com.star_zero.dagashi.shared.api.entity.MilestoneRootEntity
import com.star_zero.dagashi.shared.model.Author
import com.star_zero.dagashi.shared.model.Comment
import com.star_zero.dagashi.shared.model.Issue
import com.star_zero.dagashi.shared.model.Label
import com.star_zero.dagashi.shared.model.Milestone
import com.star_zero.dagashi.shared.platform.CoroutineDispatchers
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext

class DagashiAPI(
    private val client: HttpClient,
    private val ioDispatcher: CoroutineDispatcher = CoroutineDispatchers.io
) {

    @Throws(Exception::class)
    suspend fun milestones(): List<Milestone> = withContext(ioDispatcher) {
        val entity: MilestoneRootEntity = client.get("$ENDPOINT/api/index.json").body()
        entity.milestones.nodes.map {
            Milestone(
                it.id,
                it.title,
                it.description,
                it.path
            )
        }
    }

    @Throws(Exception::class)
    suspend fun issues(path: String): List<Issue> = withContext(ioDispatcher) {
        val entity: IssueRootEntity = client.get("$ENDPOINT/api/issue/$path.json").body()
        entity.issues.nodes.map { issueNode ->
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

    companion object {
        private const val ENDPOINT = "https://androiddagashi.github.io"
    }
}
