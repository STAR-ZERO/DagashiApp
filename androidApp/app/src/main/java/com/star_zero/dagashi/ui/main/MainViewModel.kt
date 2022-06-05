package com.star_zero.dagashi.ui.main

import androidx.lifecycle.ViewModel
import com.star_zero.dagashi.shared.repoitory.SettingRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    settingRepository: SettingRepository
) : ViewModel() {
    val flowDarkTheme = settingRepository.flowDarkTheme
    val flowDynamicTheme = settingRepository.flowDynamicTheme
}
