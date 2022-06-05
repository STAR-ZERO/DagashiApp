package com.star_zero.dagashi.shared.local

import com.star_zero.dagashi.shared.model.DarkThemeType
import kotlinx.coroutines.flow.Flow

interface LocalSettings {
    val flowOpenLinkInApp: Flow<Boolean>
    suspend fun isOpenLinkInApp(): Boolean
    suspend fun updateOpenLinkInApp(enabled: Boolean)

    val flowDarkTheme: Flow<DarkThemeType>
    suspend fun updateDarkTheme(type: DarkThemeType)

    val flowDynamicTheme: Flow<Boolean>
    suspend fun updateDynamicTheme(enable: Boolean)
}
