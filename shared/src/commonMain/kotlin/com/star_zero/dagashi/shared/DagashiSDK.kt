package com.star_zero.dagashi.shared

import com.star_zero.dagashi.shared.db.DagashiDatabase
import com.star_zero.dagashi.shared.db.DatabaseDriverFactory
import com.star_zero.dagashi.shared.model.Issue
import com.star_zero.dagashi.shared.model.Milestone
import com.star_zero.dagashi.shared.network.DagashiAPI

class DagashiSDK(databaseDriverFactory: DatabaseDriverFactory) {
    private val dagashiAPI = DagashiAPI()
    private val db = DagashiDatabase(databaseDriverFactory.createDriver())

    suspend fun getMilestone(forceReload: Boolean): List<Milestone> {
        return if (forceReload) {
            fetchAndCacheMilestones()
        } else {
            val cachedMilestones = db.milestoneQueries.selectAll(
                mapper = { id, title, body, path, _ ->
                    Milestone(
                        id,
                        title,
                        body,
                        path
                    )
                }
            ).executeAsList()

            if (cachedMilestones.isEmpty()) {
                fetchAndCacheMilestones()
            } else {
                cachedMilestones
            }
        }
    }

    suspend fun getIssue(path: String): List<Issue> {
        return dagashiAPI.issues(path)
    }

    private suspend fun fetchAndCacheMilestones(): List<Milestone> {
        return dagashiAPI.milestones().also { milestones ->
            db.milestoneQueries.transaction {
                db.milestoneQueries.deleteMilestones()
                milestones.forEachIndexed { index, milestone ->
                    db.milestoneQueries.insertMilestone(
                        id = milestone.id,
                        title = milestone.title,
                        body = milestone.body,
                        path = milestone.path,
                        sort = index
                    )
                }
            }
        }
    }
}
