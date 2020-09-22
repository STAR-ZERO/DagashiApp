package com.star_zero.dagashi.core.data.repository

import com.star_zero.dagashi.core.data.datastore.Settings
import kotlinx.coroutines.flow.Flow

interface SettingRepository {
    val settingsFlow: Flow<Settings>

    suspend fun updateOpenLinkInApp(enabled: Boolean)
}
