package com.star_zero.dagashi.shared.model

import com.star_zero.dagashi.shared.platform.ComposeImmutable

@ComposeImmutable
data class FavoriteIssue(
    val issue: Issue,
    val milestoneId: String,
    val createdAt: Long
)
