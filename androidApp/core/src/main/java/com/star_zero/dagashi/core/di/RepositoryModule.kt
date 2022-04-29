package com.star_zero.dagashi.core.di

import com.star_zero.dagashi.shared.api.DagashiAPI
import com.star_zero.dagashi.shared.db.DagashiDatabase
import com.star_zero.dagashi.shared.local.LocalSettings
import com.star_zero.dagashi.shared.repoitory.FavoriteIssueRepository
import com.star_zero.dagashi.shared.repoitory.IssueRepository
import com.star_zero.dagashi.shared.repoitory.MilestoneRepository
import com.star_zero.dagashi.shared.repoitory.SettingRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    fun provideMilestoneRepository(
        dagashiAPI: DagashiAPI,
        dagashiDb: DagashiDatabase
    ): MilestoneRepository {
        return MilestoneRepository(dagashiAPI, dagashiDb)
    }

    @Provides
    fun provideIssueRepository(dagashiAPI: DagashiAPI): IssueRepository {
        return IssueRepository(dagashiAPI)
    }

    @Provides
    fun provideFavoriteIssueRepository(
        dagashiDb: DagashiDatabase
    ): FavoriteIssueRepository {
        return FavoriteIssueRepository(dagashiDb)
    }

    @Provides
    fun provideSettingRepository(localSettings: LocalSettings): SettingRepository {
        return SettingRepository(localSettings)
    }
}
