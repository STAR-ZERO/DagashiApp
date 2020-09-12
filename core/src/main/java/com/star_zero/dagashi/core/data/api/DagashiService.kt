package com.star_zero.dagashi.core.data.api

import com.star_zero.dagashi.core.data.api.entity.IssueRootEntity
import com.star_zero.dagashi.core.data.api.entity.MilestoneRootEntity
import retrofit2.http.GET
import retrofit2.http.Path

interface DagashiService {

    @GET("api/index.json")
    suspend fun milestone(): MilestoneRootEntity

    @GET("api/issue/{path}.json")
    suspend fun issue(@Path("path") path: String): IssueRootEntity
}
