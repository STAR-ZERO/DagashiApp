package com.star_zero.dagashi.core.di

import com.star_zero.dagashi.core.data.api.DagashiService
import com.star_zero.dagashi.core.data.repository.DagashiDataRepository
import com.star_zero.dagashi.core.data.repository.DagashiRepository
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
}
