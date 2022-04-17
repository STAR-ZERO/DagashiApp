package com.star_zero.dagashi.core.di

import android.content.Context
import com.star_zero.dagashi.core.AppConfig
import com.star_zero.dagashi.core.AppDispatchers
import com.star_zero.dagashi.core.data.datasource.LocalSettingsDataSource
import com.star_zero.dagashi.core.data.repository.DagashiDataRepository
import com.star_zero.dagashi.core.data.repository.DagashiRepository
import com.star_zero.dagashi.shared.DagashiSDK
import com.star_zero.dagashi.shared.api.DagashiAPI
import com.star_zero.dagashi.shared.api.create
import com.star_zero.dagashi.shared.db.DatabaseDriverFactory
import com.star_zero.dagashi.shared.local.LocalSettings
import com.star_zero.dagashi.shared.repoitory.SettingRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.Dispatchers
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class CoreModule {

    @Singleton
    @Binds
    abstract fun bindDagashiRepository(repository: DagashiDataRepository): DagashiRepository

    companion object {

        @Singleton
        @Provides
        fun provideAppDispatchers(): AppDispatchers {
            return object : AppDispatchers {
                override val main = Dispatchers.Main
                override val io = Dispatchers.IO
                override val default = Dispatchers.Default
            }
        }

        @Singleton
        @Provides
        fun provideLocalSettings(@ApplicationContext context: Context): LocalSettings {
            return LocalSettingsDataSource.create(context)
        }

        @Singleton
        @Provides
        fun provideSettingRepository(localSettings: LocalSettings): SettingRepository {
            return SettingRepository(localSettings)
        }

        @Singleton
        @Provides
        fun provideDagashiSDK(
            @ApplicationContext context: Context,
            appConfig: AppConfig
        ): DagashiSDK {
            val databaseDriverFactory = DatabaseDriverFactory(context)
            return DagashiSDK(
                DagashiAPI.create(appConfig.debug),
                databaseDriverFactory,
            )
        }
    }
}
