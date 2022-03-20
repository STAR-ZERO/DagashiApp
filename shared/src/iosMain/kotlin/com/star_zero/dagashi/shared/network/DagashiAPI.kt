package com.star_zero.dagashi.shared.network

import io.ktor.client.HttpClient
import io.ktor.client.engine.ios.Ios
import io.ktor.client.features.json.JsonFeature
import io.ktor.client.features.json.serializer.KotlinxSerializer
import kotlinx.serialization.json.Json

fun httpClient() = HttpClient(Ios) {
    install(JsonFeature) {
        val json = Json { ignoreUnknownKeys = true }
        serializer = KotlinxSerializer(json)
    }
}

fun DagashiAPI.Companion.create(): DagashiAPI {
    return DagashiAPI(httpClient())
}
