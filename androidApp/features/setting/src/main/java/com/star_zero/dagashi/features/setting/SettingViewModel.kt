package com.star_zero.dagashi.features.setting

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.star_zero.dagashi.shared.model.DarkThemeType
import com.star_zero.dagashi.shared.repoitory.SettingRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingViewModel @Inject constructor(
    private val settingRepository: SettingRepository
) : ViewModel() {

    private var _uiState by mutableStateOf(SettingUiState())

    val uiState = combine(
        snapshotFlow { _uiState },
        settingRepository.flowOpenLinkInApp,
        settingRepository.flowDarkTheme,
        settingRepository.flowDynamicTheme
    ) { uiState, isOpenLinkInApp, darkThemeType, dynamicTheme ->
        uiState.copy(
            isOpenLinkInApp = isOpenLinkInApp,
            darkThemeType = darkThemeType,
            dynamicTheme = dynamicTheme
        )
    }

    fun updateOpenLinkInApp(enabled: Boolean) {
        viewModelScope.launch {
            settingRepository.updateOpenLinkInApp(enabled)
        }
    }

    fun updateDarkTheme(type: DarkThemeType) {
        viewModelScope.launch {
            settingRepository.updateDarkTheme(type)
        }
    }

    fun updateDynamicTheme(enabled: Boolean) {
        viewModelScope.launch {
            settingRepository.updateDynamicTheme(enabled)
        }
    }
}
