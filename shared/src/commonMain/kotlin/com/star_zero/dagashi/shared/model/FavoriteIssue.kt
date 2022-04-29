package com.star_zero.dagashi.shared.model

data class FavoriteIssue(
    val issue: Issue,
    val milestoneId: String,
    val createdAt: Long
)
