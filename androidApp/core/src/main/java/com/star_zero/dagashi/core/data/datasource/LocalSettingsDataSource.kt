package com.star_zero.dagashi.core.data.datasource

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.dataStore
import com.star_zero.dagashi.core.data.datasource.datastore.Settings
import com.star_zero.dagashi.core.data.datasource.datastore.SettingsSerializer
import com.star_zero.dagashi.shared.local.LocalSettings
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

    override val flowOpenLinkInApp: Flow<Boolean> = dataStore.data
        .catch { e ->
            if (e is IOException) {
                Napier.e("Error setting data store", e)
                emit(Settings.getDefaultInstance())
            } else {
                throw e
            }
        }.map {
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

    companion object {
        private val Context.settingsDataStore: DataStore<Settings> by dataStore(
            fileName = "settings.pb",
            serializer = SettingsSerializer
        )

        fun create(context: Context): LocalSettings {
            return LocalSettingsDataSource(context.settingsDataStore)
        }
    }
}
