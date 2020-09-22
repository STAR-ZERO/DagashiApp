package com.star_zero.dagashi.core.di

import android.app.Application
import androidx.datastore.createDataStore
import com.star_zero.dagashi.core.data.api.DagashiService
import com.star_zero.dagashi.core.data.datastore.SettingsSerializer
import com.star_zero.dagashi.core.data.repository.DagashiDataRepository
import com.star_zero.dagashi.core.data.repository.DagashiRepository
import com.star_zero.dagashi.core.data.repository.SettingDataRepository
import com.star_zero.dagashi.core.data.repository.SettingRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

@Module
@InstallIn(ApplicationComponent::class)
object CoreModule {

    @Provides
    fun provideDagashiRepository(): DagashiRepository {
        val okhttp = OkHttpClient.Builder()
            .build()

        val retrofit = Retrofit.Builder()
            .client(okhttp)
            .addConverterFactory(MoshiConverterFactory.create())
            .baseUrl("https://androiddagashi.github.io/")
            .build()

        val service = retrofit.create(DagashiService::class.java)

        return DagashiDataRepository(service)
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
