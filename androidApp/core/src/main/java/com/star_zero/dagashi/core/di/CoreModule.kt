package com.star_zero.dagashi.core.di

import android.content.Context
import com.star_zero.dagashi.core.AppConfig
import com.star_zero.dagashi.core.AppDispatchers
import com.star_zero.dagashi.core.data.datasource.LocalSettingsDataSource
import com.star_zero.dagashi.shared.api.DagashiAPI
import com.star_zero.dagashi.shared.api.create
import com.star_zero.dagashi.shared.db.DagashiDatabase
import com.star_zero.dagashi.shared.db.DatabaseDriverFactory
import com.star_zero.dagashi.shared.db.DatabaseFactory
import com.star_zero.dagashi.shared.local.LocalSettings
import com.star_zero.dagashi.shared.repoitory.IssueRepository
import com.star_zero.dagashi.shared.repoitory.MilestoneRepository
import com.star_zero.dagashi.shared.repoitory.SettingRepository
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
        fun provideDagashiAPI(appConfig: AppConfig): DagashiAPI {
            return DagashiAPI.create(appConfig.debug)
        }

        @Singleton
        @Provides
        fun provideDagashiDb(@ApplicationContext context: Context): DagashiDatabase {
            val databaseDriverFactory = DatabaseDriverFactory(context)
            return DatabaseFactory.createDatabase(databaseDriverFactory)
        }

        @Singleton
        @Provides
        fun provideLocalSettings(@ApplicationContext context: Context): LocalSettings {
            return LocalSettingsDataSource.create(context)
        }

        @Singleton
        @Provides
        fun provideMilestoneRepository(
            dagashiAPI: DagashiAPI,
            dagashiDb: DagashiDatabase
        ): MilestoneRepository {
            return MilestoneRepository(dagashiAPI, dagashiDb)
        }

        @Singleton
        @Provides
        fun provideIssueRepository(dagashiAPI: DagashiAPI): IssueRepository {
            return IssueRepository(dagashiAPI)
        }

        @Singleton
        @Provides
        fun provideSettingRepository(localSettings: LocalSettings): SettingRepository {
            return SettingRepository(localSettings)
        }
    }
}
