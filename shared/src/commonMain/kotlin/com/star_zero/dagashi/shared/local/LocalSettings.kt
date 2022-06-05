package com.star_zero.dagashi.shared.local

import com.star_zero.dagashi.shared.model.DarkThemeType
import com.star_zero.dagashi.shared.model.Setting
import kotlinx.coroutines.flow.Flow

interface LocalSettings {
    val settingFlow: Flow<Setting>

    suspend fun isOpenLinkInApp(): Boolean
    suspend fun updateOpenLinkInApp(enabled: Boolean)

    suspend fun updateDarkThemeType(type: DarkThemeType)

    suspend fun updateDynamicThemeEnabled(enabled: Boolean)
}
