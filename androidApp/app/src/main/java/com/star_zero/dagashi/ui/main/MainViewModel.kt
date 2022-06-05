package com.star_zero.dagashi.ui.main

import androidx.lifecycle.ViewModel
import com.star_zero.dagashi.shared.repoitory.SettingRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.map
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    settingRepository: SettingRepository
) : ViewModel() {
    val darkThemeFlow = settingRepository.settingFlow.map {
        it.darkThemeType
    }
    val dynamicThemeEnabledFlow = settingRepository.settingFlow.map {
        it.isDynamicThemeEnabled
    }
}
