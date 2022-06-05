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
import io.github.aakira.napier.Napier
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import java.io.IOException

class LocalSettingsDataSource(
    private val dataStore: DataStore<Settings>
) : LocalSettings {

    private val flowDataStore = dataStore.data
        .catch { e ->
            if (e is IOException) {
                Napier.e("Error setting data store", e)
                emit(Settings.getDefaultInstance())
            } else {
                throw e
            }
        }

    override val flowOpenLinkInApp: Flow<Boolean> = flowDataStore.map {
        it.openLinkInApp
    }.distinctUntilChanged()

    override suspend fun isOpenLinkInApp(): Boolean {
        return dataStore.data.first().openLinkInApp
    }

    override suspend fun updateOpenLinkInApp(enabled: Boolean) {
        dataStore.updateData { settings ->
            settings.toBuilder().setOpenLinkInApp(enabled).build()
        }
    }

    override val flowDarkTheme: Flow<DarkThemeType> = flowDataStore.map {
        it.darkTheme.toType()
    }

    override suspend fun updateDarkTheme(type: DarkThemeType) {
        dataStore.updateData { settings ->
            settings.toBuilder().setDarkTheme(type.toDataStore()).build()
        }
    }

    override val flowDynamicTheme: Flow<Boolean> = flowDataStore.map {
        if (!it.hasDynamicTheme()) {
            true // default true
        } else {
            it.dynamicTheme.value
        }
    }.distinctUntilChanged()

    override suspend fun updateDynamicTheme(enable: Boolean) {
        dataStore.updateData { settings ->
            settings.toBuilder().setDynamicTheme(BoolValue.of(enable)).build()
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
}
