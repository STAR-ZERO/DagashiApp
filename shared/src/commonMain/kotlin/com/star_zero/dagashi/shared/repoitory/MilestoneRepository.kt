package com.star_zero.dagashi.shared.repoitory

import com.star_zero.dagashi.shared.api.DagashiAPI
import com.star_zero.dagashi.shared.db.DagashiDatabase
import com.star_zero.dagashi.shared.model.Milestone

class MilestoneRepository(
    private val dagashiAPI: DagashiAPI,
    private val dagashiDb: DagashiDatabase
) {
    suspend fun getMilestone(forceReload: Boolean): List<Milestone> {
        return if (forceReload) {
            fetchAndCacheMilestones()
        } else {
            val cachedMilestones = dagashiDb.milestoneQueries.selectAll(
                mapper = { id, title, body, path, _ ->
                    Milestone(
                        id,
                        title,
                        body,
                        path
                    )
                }
            ).executeAsList()

            cachedMilestones.ifEmpty {
                fetchAndCacheMilestones()
            }
        }
    }

    private suspend fun fetchAndCacheMilestones(): List<Milestone> {
        return dagashiAPI.milestones().also { milestones ->
            dagashiDb.milestoneQueries.transaction {
                dagashiDb.milestoneQueries.deleteMilestones()
                milestones.forEachIndexed { index, milestone ->
                    dagashiDb.milestoneQueries.insertMilestone(
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
