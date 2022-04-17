package com.star_zero.dagashi.features.setting

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.star_zero.dagashi.shared.repoitory.SettingRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingViewModel @Inject constructor(
    private val settingRepository: SettingRepository
) : ViewModel() {

    var uiState by mutableStateOf(SettingUiState())
        private set

    init {
        viewModelScope.launch {
            settingRepository.flowOpenLinkInApp.collect { isOpenLinkInApp ->
                uiState = uiState.copy(
                    isOpenLinkInApp = isOpenLinkInApp
                )
            }
        }
    }

    fun updateOpenLinkInApp(enabled: Boolean) {
        viewModelScope.launch {
            settingRepository.updateOpenLinkInApp(enabled)
        }
    }
}
