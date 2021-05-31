package com.star_zero.dagashi.ui.setting

import androidx.lifecycle.AbstractSavedStateViewModelFactory
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.savedstate.SavedStateRegistryOwner
import com.star_zero.dagashi.core.data.repository.SettingRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.map
import javax.inject.Inject

@HiltViewModel
class SettingViewModel @Inject constructor(
    private val settingRepository: SettingRepository
) : ViewModel() {
    val isOpenLinkInApp = settingRepository.settingsFlow.map { it.openLinkInApp }

    suspend fun updateOpenLinkInApp(enabled: Boolean) {
        settingRepository.updateOpenLinkInApp(enabled)
    }

    class Factory(
        owner: SavedStateRegistryOwner,
        private val settingRepository: SettingRepository
    ) : AbstractSavedStateViewModelFactory(owner, null) {

        override fun <T : ViewModel?> create(
            key: String,
            modelClass: Class<T>,
            handle: SavedStateHandle
        ): T {
            @Suppress("UNCHECKED_CAST")
            return SettingViewModel(settingRepository) as T
        }

    }
}
