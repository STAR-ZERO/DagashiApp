package com.star_zero.dagashi.shared.repoitory

import com.squareup.sqldelight.runtime.coroutines.asFlow
import com.squareup.sqldelight.runtime.coroutines.mapToList
import com.star_zero.dagashi.shared.db.DagashiDatabase
import com.star_zero.dagashi.shared.model.Author
import com.star_zero.dagashi.shared.model.Comment
import com.star_zero.dagashi.shared.model.FavoriteIssue
import com.star_zero.dagashi.shared.model.Issue
import com.star_zero.dagashi.shared.model.Label
import com.star_zero.dagashi.shared.model.Milestone
import com.star_zero.dagashi.shared.platform.CoroutineDispatchers
import io.ktor.util.date.getTimeMillis
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext

class FavoriteIssueRepository(
    private val dagashiDb: DagashiDatabase,
    private val ioDispatcher: CoroutineDispatcher = CoroutineDispatchers.io
) {
    suspend fun addFavorite(milestoneId: String, issue: Issue) = withContext(ioDispatcher) {
        with(dagashiDb) {
            transaction {
                favoriteIssueQueries.insertFavoriteIssue(
                    url = issue.url,
                    title = issue.title,
                    body = issue.body,
                    milestone_id = milestoneId,
                    created_at = getTimeMillis()
                )

                // Comment
                issue.comments.forEach { comment ->
                    commentQueries.insertComment(
                        body = comment.body,
                        author_name = comment.author.name,
                        issue_url = issue.url
                    )
                    // Author
                    authorQueries.upsertAuthor(
                        name = comment.author.name,
                        url = comment.author.url,
                        avatar_url = comment.author.avatarUrl
                    )
                }

                // Label
                issue.labels.forEach { label ->
                    labelQueries.upsertLabel(
                        name = label.name,
                        color = label.color
                    )

                    // IssueLabels
                    issueLabelQueries.insertIssueLabel(
                        issue_url = issue.url,
                        label_name = label.name
                    )
                }
            }
        }
    }

    fun flowFavoriteUrlsByMilestone(milestone: Milestone): Flow<List<String>> {
        return dagashiDb.favoriteIssueQueries.selectByMilestone(milestone_id = milestone.id)
            .asFlow()
            .mapToList()
            .map { favoriteIssues ->
                favoriteIssues.map { it.url }
            }
    }

    suspend fun getFavorites(): List<FavoriteIssue> = withContext(ioDispatcher) {
        with(dagashiDb) {
            favoriteIssueQueries.selectAll(
                mapper = { issueUrl, issueTitle, issueBody, milestoneId, createdAt ->
                    // Comment
                    val comments = commentQueries.selectByIssueUrl(
                        issue_url = issueUrl,
                        mapper = { commentBody, authorName, authorUrl, authorAvatarUrl ->
                            Comment(
                                body = commentBody,
                                Author(
                                    name = authorName,
                                    url = authorUrl,
                                    avatarUrl = authorAvatarUrl
                                )
                            )
                        }
                    ).executeAsList()

                    // Label
                    val labels = issueLabelQueries.selectLablesByIssurUrl(
                        issue_url = issueUrl,
                        mapper = { labelName, labelColor ->
                            Label(
                                name = labelName,
                                color = labelColor
                            )
                        }
                    ).executeAsList()

                    FavoriteIssue(
                        issue = Issue(
                            url = issueUrl,
                            title = issueTitle,
                            body = issueBody,
                            labels = labels,
                            comments = comments
                        ),
                        milestoneId = milestoneId,
                        createdAt = createdAt
                    )
                }
            ).executeAsList()
        }
    }

    suspend fun deleteFavorite(issue: Issue) = withContext(ioDispatcher) {
        with(dagashiDb) {
            transaction {
                favoriteIssueQueries.deleteFavoriteIssue(issue.url)
                commentQueries.deleteCommentByIssueUrl(issue.url)
                issueLabelQueries.deleteIssueLableByIssueUrl(issue.url)
            }
        }
    }

    suspend fun deleteAllFavorites() = withContext(ioDispatcher) {
        with(dagashiDb) {
            transaction {
                favoriteIssueQueries.deleteFavoriteIssues()
                commentQueries.deleteComments()
                authorQueries.deleteAuthors()
                issueLabelQueries.deleteIssueLabels()
                labelQueries.deleteLabels()
            }
        }
    }
}
