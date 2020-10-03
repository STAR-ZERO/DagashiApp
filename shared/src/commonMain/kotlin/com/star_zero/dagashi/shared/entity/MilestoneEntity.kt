package com.star_zero.dagashi.shared.entity

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MilestoneRootEntity(
    @SerialName("milestones")
    val milestones: MilestoneEntity,
)

@Serializable
data class MilestoneEntity(
    @SerialName("nodes")
    val nodes: List<MilestoneNodeEntity>,
)

@Serializable
data class MilestoneNodeEntity(
    @SerialName("id")
    val id: String,
    @SerialName("title")
    val title: String,
    @SerialName("description")
    val description: String,
    @SerialName("path")
    val path: String,
)
