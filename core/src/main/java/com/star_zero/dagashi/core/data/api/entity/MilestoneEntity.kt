package com.star_zero.dagashi.core.data.api.entity

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class MilestoneRootEntity(
    @Json(name = "milestones")
    val milestones: MilestoneEntity,
)

@JsonClass(generateAdapter = true)
data class MilestoneEntity(
    @Json(name = "nodes")
    val nodes: List<MilestoneNodeEntity>,
)

@JsonClass(generateAdapter = true)
data class MilestoneNodeEntity(
    @Json(name = "id")
    val id: String,
    @Json(name = "title")
    val title: String,
    @Json(name = "description")
    val description: String,
    @Json(name = "path")
    val path: String,
)
