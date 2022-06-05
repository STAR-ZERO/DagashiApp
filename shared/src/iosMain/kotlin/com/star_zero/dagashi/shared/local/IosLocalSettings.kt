package com.star_zero.dagashi.shared.local

import com.star_zero.dagashi.shared.model.DarkThemeType
import com.star_zero.dagashi.shared.model.Setting
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class IosLocalSettings : LocalSettings {
    override val settingFlow: Flow<Setting>
        get() = flow { }

    override suspend fun isOpenLinkInApp(): Boolean {
        // TODO
        return false
    }

    override suspend fun updateOpenLinkInApp(enabled: Boolean) {
        // TODO
    }

    override suspend fun updateDarkThemeType(type: DarkThemeType) {
        TODO("Not yet implemented")
    }

    override suspend fun updateDynamicThemeEnabled(enabled: Boolean) {
        TODO("Not yet implemented")
    }
}
