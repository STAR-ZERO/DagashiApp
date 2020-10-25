package com.star_zero.dagashi.core.data.repository

import com.star_zero.dagashi.shared.model.Issue
import com.star_zero.dagashi.shared.model.Milestone

interface DagashiRepository {
    suspend fun milestones(): List<Milestone>

    suspend fun issues(path: String): List<Issue>
}
