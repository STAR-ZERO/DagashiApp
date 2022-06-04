package com.star_zero.dagashi.shared.repoitory

import com.star_zero.dagashi.shared.local.LocalSettings
import com.star_zero.dagashi.shared.model.DarkThemeType
import kotlinx.coroutines.flow.Flow

class SettingRepository(
    private val localSettings: LocalSettings
) {
    val flowOpenLinkInApp: Flow<Boolean> = localSettings.flowOpenLinkInApp

    val flowDarkTheme: Flow<DarkThemeType> = localSettings.flowDarkTheme

    suspend fun isOpenLinkInApp(): Boolean {
        return localSettings.isOpenLinkInApp()
    }

    suspend fun updateOpenLinkInApp(enabled: Boolean) {
        localSettings.updateOpenLinkInApp(enabled)
    }

    suspend fun updateDarkTheme(darkThemeSetting: DarkThemeType) {
        localSettings.updateDarkTheme(darkThemeSetting)
    }
}
