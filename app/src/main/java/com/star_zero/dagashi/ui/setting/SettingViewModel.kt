package com.star_zero.dagashi.ui.setting

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.star_zero.dagashi.core.data.repository.SettingRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingViewModel @Inject constructor(
    private val settingRepository: SettingRepository
) : ViewModel() {

    private var _uiState by mutableStateOf(SettingUiState())

    val uiState = snapshotFlow {
        _uiState
    }.combine(settingRepository.settingsFlow) { uiState, settings ->
        uiState.copy(isOpenLinkInApp = settings.openLinkInApp)
    }

    fun updateOpenLinkInApp(enabled: Boolean) {
        viewModelScope.launch {
            settingRepository.updateOpenLinkInApp(enabled)
        }
    }
}
