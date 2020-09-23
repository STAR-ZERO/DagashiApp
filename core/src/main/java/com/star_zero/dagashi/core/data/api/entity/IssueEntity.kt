package com.star_zero.dagashi.core.data.api.entity

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class IssueRootEntity(
    @Json(name = "issues")
    val issues: IssueEntity,
)

@JsonClass(generateAdapter = true)
data class IssueEntity(
    @Json(name = "nodes")
    val nodes: List<IssueNodeEntity>,
)

@JsonClass(generateAdapter = true)
data class IssueNodeEntity(
    @Json(name = "url")
    val url: String,
    @Json(name = "title")
    val title: String,
    @Json(name = "body")
    val body: String,
    @Json(name = "labels")
    val labels: LabelEntity,
)

@JsonClass(generateAdapter = true)
data class LabelEntity(
    @Json(name = "nodes")
    val nodes: List<LabelNodeEntity>,
)

@JsonClass(generateAdapter = true)
data class LabelNodeEntity(
    @Json(name = "name")
    val name: String,
    @Json(name = "color")
    val color: String,
)
