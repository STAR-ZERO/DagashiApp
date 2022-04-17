package com.star_zero.dagashi.shared.local

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
}
