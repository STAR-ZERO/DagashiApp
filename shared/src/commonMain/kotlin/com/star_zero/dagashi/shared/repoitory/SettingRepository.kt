package com.star_zero.dagashi.shared.repoitory

import com.star_zero.dagashi.shared.local.LocalSettings
import com.star_zero.dagashi.shared.model.DarkThemeType
import com.star_zero.dagashi.shared.model.Setting
import kotlinx.coroutines.flow.Flow

class SettingRepository(
    private val localSettings: LocalSettings
) {
    val settingFlow: Flow<Setting> = localSettings.settingFlow

    suspend fun isOpenLinkInApp(): Boolean {
        return localSettings.isOpenLinkInApp()
    }

    suspend fun updateOpenLinkInApp(enabled: Boolean) {
        localSettings.updateOpenLinkInApp(enabled)
    }

    suspend fun updateDarkThemeType(type: DarkThemeType) {
        localSettings.updateDarkThemeType(type)
    }

    suspend fun updateDynamicThemeEnabled(enabled: Boolean) {
        localSettings.updateDynamicThemeEnabled(enabled)
    }
}
