package com.star_zero.dagashi.shared.api.entity

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
internal data class IssueRootEntity(
    @SerialName("issues")
    val issues: IssueEntity,
)

@Serializable
internal data class IssueEntity(
    @SerialName("nodes")
    val nodes: List<IssueNodeEntity>,
)

@Serializable
internal data class IssueNodeEntity(
    @SerialName("url")
    val url: String,
    @SerialName("title")
    val title: String,
    @SerialName("body")
    val body: String,
    @SerialName("labels")
    val labels: LabelEntity,
    @SerialName("comments")
    val comments: CommentEntity,
)

@Serializable
internal data class LabelEntity(
    @SerialName("nodes")
    val nodes: List<LabelNodeEntity>,
)

@Serializable
internal data class LabelNodeEntity(
    @SerialName("name")
    val name: String,
    @SerialName("color")
    val color: String,
)

@Serializable
internal data class CommentEntity(
    @SerialName("nodes")
    val nodes: List<CommentNodeEntity>,
)

@Serializable
internal data class CommentNodeEntity(
    @SerialName("body")
    val body: String,
    @SerialName("author")
    val author: CommentNodeAuthor
)

@Serializable
internal data class CommentNodeAuthor(
    @SerialName("login")
    val login: String,
    @SerialName("url")
    val url: String,
    @SerialName("avatarUrl")
    val avatarUrl: String,
)
