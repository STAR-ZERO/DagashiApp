package com.star_zero.dagashi.shared

import com.star_zero.dagashi.shared.entity.IssueRootEntity
import com.star_zero.dagashi.shared.entity.MilestoneRootEntity
import io.ktor.client.HttpClient
import io.ktor.client.features.json.JsonFeature
import io.ktor.client.features.json.serializer.KotlinxSerializer
import io.ktor.client.request.get
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

    suspend fun issue(path: String): IssueRootEntity = withContext(DispatchersIO) {
        client.get("$ENDPOINT/api/issue/$path.json")
    }

    companion object {
        private const val ENDPOINT = "https://androiddagashi.github.io"
    }
}
