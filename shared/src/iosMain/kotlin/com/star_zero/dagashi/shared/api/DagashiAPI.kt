package com.star_zero.dagashi.shared.api

import io.github.aakira.napier.DebugAntilog
import io.github.aakira.napier.Napier
import io.ktor.client.HttpClient
import io.ktor.client.engine.darwin.Darwin
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json

fun httpClient(debug: Boolean) = HttpClient(Darwin) {
    install(ContentNegotiation) {
        json(
            Json {
                ignoreUnknownKeys = true
            }
        )
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
