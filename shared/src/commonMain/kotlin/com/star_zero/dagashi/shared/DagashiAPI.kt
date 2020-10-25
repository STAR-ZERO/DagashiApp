package com.star_zero.dagashi.shared

import com.star_zero.dagashi.shared.entity.IssueRootEntity
import com.star_zero.dagashi.shared.entity.MilestoneRootEntity
import com.star_zero.dagashi.shared.model.Milestone
import io.ktor.client.*
import io.ktor.client.features.json.JsonFeature
import io.ktor.client.features.json.serializer.*
import io.ktor.client.request.*
import kotlinx.coroutines.withContext
import kotlinx.serialization.json.Json

class DagashiAPI {

    private val client = HttpClient {
        install(JsonFeature) {
            val json = Json { ignoreUnknownKeys = true }
            serializer = KotlinxSerializer(json)
        }
    }

    suspend fun milestone(): MilestoneRootEntity = withContext(DispatchersIO) {
        client.get("$ENDPOINT/api/index.json")
    }

    @Throws(Exception::class)
    suspend fun milestones(): List<Milestone> = withContext(DispatchersIO) {
        val milestoneRoot = client.get<MilestoneRootEntity>("$ENDPOINT/api/index.json")
        milestoneRoot.milestones.nodes.map {
            Milestone(
                it.id,
                it.title,
                it.description,
                it.path
            )
        }
    }

    suspend fun issue(path: String): IssueRootEntity = withContext(DispatchersIO) {
        client.get("$ENDPOINT/api/issue/$path.json")
    }

    companion object {
        private const val ENDPOINT = "https://androiddagashi.github.io"
    }
}
