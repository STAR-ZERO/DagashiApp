package com.star_zero.dagashi.di

import com.star_zero.dagashi.BuildConfig
import com.star_zero.dagashi.core.AppConfig
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideAppConfig(): AppConfig {
        return AppConfig(BuildConfig.DEBUG)
    }
}
