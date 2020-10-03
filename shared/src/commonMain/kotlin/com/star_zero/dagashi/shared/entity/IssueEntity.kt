package com.star_zero.dagashi.shared.entity

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class IssueRootEntity(
    @SerialName("issues")
    val issues: IssueEntity,
)

@Serializable
data class IssueEntity(
    @SerialName("nodes")
    val nodes: List<IssueNodeEntity>,
)

@Serializable
data class IssueNodeEntity(
    @SerialName("url")
    val url: String,
    @SerialName("title")
    val title: String,
    @SerialName("body")
    val body: String,
    @SerialName("labels")
    val labels: LabelEntity,
)

@Serializable
data class LabelEntity(
    @SerialName("nodes")
    val nodes: List<LabelNodeEntity>,
)

@Serializable
data class LabelNodeEntity(
    @SerialName("name")
    val name: String,
    @SerialName("color")
    val color: String,
)
