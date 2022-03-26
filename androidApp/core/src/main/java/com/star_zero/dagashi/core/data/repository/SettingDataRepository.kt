package com.star_zero.dagashi.core.data.repository

import androidx.datastore.core.DataStore
import com.star_zero.dagashi.core.data.datastore.Settings
import io.github.aakira.napier.Napier
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import java.io.IOException
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SettingDataRepository @Inject constructor(
    private val settingsDataStore: DataStore<Settings>,
) : SettingRepository {

    override val settingsFlow: Flow<Settings> = settingsDataStore.data
        .catch { e ->
            if (e is IOException) {
                Napier.e("Error setting data store", e)
                emit(Settings.getDefaultInstance())
            } else {
                throw e
            }
        }

    override suspend fun updateOpenLinkInApp(enabled: Boolean) {
        settingsDataStore.updateData { settings ->
            settings.toBuilder().setOpenLinkInApp(enabled).build()
        }
    }
}
