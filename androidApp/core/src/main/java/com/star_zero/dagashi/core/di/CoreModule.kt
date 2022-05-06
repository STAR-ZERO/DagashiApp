package com.star_zero.dagashi.core.di

import android.content.Context
import com.star_zero.dagashi.core.AppConfig
import com.star_zero.dagashi.core.data.datasource.LocalSettingsDataSource
import com.star_zero.dagashi.shared.api.DagashiAPI
import com.star_zero.dagashi.shared.api.create
import com.star_zero.dagashi.shared.db.DagashiDatabase
import com.star_zero.dagashi.shared.db.DatabaseDriverFactory
import com.star_zero.dagashi.shared.db.DatabaseFactory
import com.star_zero.dagashi.shared.local.LocalSettings
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object CoreModule {

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
}
