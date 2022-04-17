package com.star_zero.dagashi.core.data.repository

import com.star_zero.dagashi.core.AppDispatchers
import com.star_zero.dagashi.shared.DagashiSDK
import com.star_zero.dagashi.shared.model.Milestone
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DagashiDataRepository @Inject constructor(
    private val appDispatchers: AppDispatchers,
    private val dagashiSDK: DagashiSDK,
) : DagashiRepository {

    override suspend fun milestones(forceReload: Boolean): List<Milestone> =
        withContext(appDispatchers.io) {
            dagashiSDK.getMilestone(forceReload)
        }
}
