package com.star_zero.dagashi.shared.repoitory

import com.star_zero.dagashi.shared.local.LocalSettings
import kotlinx.coroutines.flow.Flow

class SettingRepository(
    private val localSettings: LocalSettings
) {
    val flowOpenLinkInApp: Flow<Boolean> = localSettings.flowOpenLinkInApp

    suspend fun isOpenLinkInApp(): Boolean {
        return localSettings.isOpenLinkInApp()
    }

    suspend fun updateOpenLinkInApp(enabled: Boolean) {
        localSettings.updateOpenLinkInApp(enabled)
    }
}
