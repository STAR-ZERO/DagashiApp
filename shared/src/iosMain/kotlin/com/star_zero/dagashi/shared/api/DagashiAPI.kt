package com.star_zero.dagashi.shared.api

import io.github.aakira.napier.DebugAntilog
import io.github.aakira.napier.Napier
import io.ktor.client.HttpClient
import io.ktor.client.engine.ios.Ios
import io.ktor.client.features.json.JsonFeature
import io.ktor.client.features.json.serializer.KotlinxSerializer
import io.ktor.client.features.logging.LogLevel
import io.ktor.client.features.logging.Logger
import io.ktor.client.features.logging.Logging
import kotlinx.serialization.json.Json

fun httpClient(debug: Boolean) = HttpClient(Ios) {
    install(JsonFeature) {
        val json = Json { ignoreUnknownKeys = true }
        serializer = KotlinxSerializer(json)
    }

    if (debug) {
        install(Logging) {
            level = LogLevel.ALL
            logger = object : Logger {
                override fun log(message: String) {
                    Napier.d(tag = "HttpClient", message = message)
                }
            }
        }
    }
}

fun DagashiAPI.Companion.create(debug: Boolean): DagashiAPI {
    if (debug) {
        Napier.base(DebugAntilog())
    }
    return DagashiAPI(httpClient(debug))
}
