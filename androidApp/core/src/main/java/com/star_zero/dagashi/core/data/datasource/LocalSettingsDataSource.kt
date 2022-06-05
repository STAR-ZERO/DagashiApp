package com.star_zero.dagashi.core.data.datasource

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.dataStore
import com.google.protobuf.BoolValue
import com.star_zero.dagashi.core.data.datasource.datastore.Settings
import com.star_zero.dagashi.core.data.datasource.datastore.Settings.DarkTheme
import com.star_zero.dagashi.core.data.datasource.datastore.SettingsSerializer
import com.star_zero.dagashi.shared.local.LocalSettings
import com.star_zero.dagashi.shared.model.DarkThemeType
import com.star_zero.dagashi.shared.model.Setting
import io.github.aakira.napier.Napier
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import java.io.IOException

class LocalSettingsDataSource(
    private val dataStore: DataStore<Settings>
) : LocalSettings {

    override val settingFlow = dataStore.data
        .map {
            it.toModel()
        }
        .catch { e ->
            if (e is IOException) {
                Napier.e("Error setting data store", e)
                emit(
                    Settings.getDefaultInstance().toModel()
                )
            } else {
                throw e
            }
        }

    override suspend fun isOpenLinkInApp(): Boolean {
        return dataStore.data.first().openLinkInApp
    }

    override suspend fun updateOpenLinkInApp(enabled: Boolean) {
        dataStore.updateData { settings ->
            settings.toBuilder().setOpenLinkInApp(enabled).build()
        }
    }

    override suspend fun updateDarkThemeType(type: DarkThemeType) {
        dataStore.updateData { settings ->
            settings.toBuilder().setDarkTheme(type.toDataStore()).build()
        }
    }

    override suspend fun updateDynamicThemeEnabled(enabled: Boolean) {
        dataStore.updateData { settings ->
            settings.toBuilder().setDynamicTheme(BoolValue.of(enabled)).build()
        }
    }

    companion object {
        private val Context.settingsDataStore: DataStore<Settings> by dataStore(
            fileName = "settings.pb",
            serializer = SettingsSerializer
        )

        fun create(context: Context): LocalSettings {
            return LocalSettingsDataSource(context.settingsDataStore)
        }
    }

    private fun DarkTheme.toType(): DarkThemeType {
        return when (this) {
            DarkTheme.DEVICE -> DarkThemeType.DEVICE
            DarkTheme.ON -> DarkThemeType.ON
            DarkTheme.OFF -> DarkThemeType.OFF
            else -> throw RuntimeException("Unknown value: $this")
        }
    }

    private fun DarkThemeType.toDataStore(): DarkTheme {
        return when (this) {
            DarkThemeType.DEVICE -> DarkTheme.DEVICE
            DarkThemeType.ON -> DarkTheme.ON
            DarkThemeType.OFF -> DarkTheme.OFF
        }
    }

    private fun Settings.toModel(): Setting {
        return Setting(
            isOpenLinkInApp = openLinkInApp,
            darkThemeType = darkTheme.toType(),
            isDynamicThemeEnabled = if (hasDynamicTheme()) {
                dynamicTheme.value
            } else {
                true
            }
        )
    }
}
