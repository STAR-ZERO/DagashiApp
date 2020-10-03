package com.star_zero.dagashi.core.di

import android.app.Application
import androidx.datastore.createDataStore
import com.star_zero.dagashi.core.data.datastore.SettingsSerializer
import com.star_zero.dagashi.core.data.repository.DagashiDataRepository
import com.star_zero.dagashi.core.data.repository.DagashiRepository
import com.star_zero.dagashi.core.data.repository.SettingDataRepository
import com.star_zero.dagashi.core.data.repository.SettingRepository
import com.star_zero.dagashi.shared.DagashiAPI
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent

@Module
@InstallIn(ApplicationComponent::class)
object CoreModule {

    @Provides
    fun provideDagashiRepository(): DagashiRepository {
        return DagashiDataRepository(DagashiAPI())
    }

    @Provides
    fun provideSettingRepository(application: Application): SettingRepository {
        val settingsDataStore = application.createDataStore(
            fileName = "settings.pb",
            serializer = SettingsSerializer
        )
        return SettingDataRepository(settingsDataStore)
    }
}
