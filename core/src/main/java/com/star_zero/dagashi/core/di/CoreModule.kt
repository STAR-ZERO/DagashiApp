package com.star_zero.dagashi.core.di

import android.app.Application
import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.dataStore
import com.star_zero.dagashi.core.data.datastore.Settings
import com.star_zero.dagashi.core.data.datastore.SettingsSerializer
import com.star_zero.dagashi.core.data.repository.DagashiDataRepository
import com.star_zero.dagashi.core.data.repository.DagashiRepository
import com.star_zero.dagashi.core.data.repository.SettingDataRepository
import com.star_zero.dagashi.core.data.repository.SettingRepository
import com.star_zero.dagashi.shared.DagashiSDK
import com.star_zero.dagashi.shared.db.DatabaseDriverFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object CoreModule {

    val Context.settingsDataStore: DataStore<Settings> by dataStore(
        fileName = "settings.pb",
        serializer = SettingsSerializer
    )

    @Provides
    fun provideDagashiRepository(application: Application): DagashiRepository {
        val databaseDriverFactory = DatabaseDriverFactory(application)
        val dagashiSDK = DagashiSDK(databaseDriverFactory)
        return DagashiDataRepository(dagashiSDK)
    }

    @Provides
    fun provideSettingRepository(application: Application): SettingRepository {
        return SettingDataRepository(application.settingsDataStore)
    }
}
