package com.star_zero.dagashi.shared.local

import com.star_zero.dagashi.shared.model.DarkThemeType
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class IosLocalSettings : LocalSettings {

    override val flowOpenLinkInApp: Flow<Boolean>
        get() = flow { }

    override suspend fun isOpenLinkInApp(): Boolean {
        // TODO
        return false
    }

    override suspend fun updateOpenLinkInApp(enabled: Boolean) {
        // TODO
    }

    override val flowDarkTheme: Flow<DarkThemeType>
        get() = flow { }

    override suspend fun updateDarkTheme(type: DarkThemeType) {
        TODO("Not yet implemented")
    }
}
