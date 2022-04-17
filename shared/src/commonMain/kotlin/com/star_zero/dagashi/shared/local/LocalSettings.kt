package com.star_zero.dagashi.shared.local

import kotlinx.coroutines.flow.Flow

interface LocalSettings {
    val flowOpenLinkInApp: Flow<Boolean>

    suspend fun isOpenLinkInApp(): Boolean
    suspend fun updateOpenLinkInApp(enabled: Boolean)
}
