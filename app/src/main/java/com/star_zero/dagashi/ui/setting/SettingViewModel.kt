package com.star_zero.dagashi.ui.setting

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import com.star_zero.dagashi.core.data.repository.SettingRepository
import kotlinx.coroutines.flow.map

class SettingViewModel @ViewModelInject constructor(
    private val settingRepository: SettingRepository
): ViewModel() {
    val isOpenLinkInApp = settingRepository.settingsFlow.map { it.openLinkInApp }

    suspend fun updateOpenLinkInApp(enabled: Boolean) {
        settingRepository.updateOpenLinkInApp(enabled)
    }
}
