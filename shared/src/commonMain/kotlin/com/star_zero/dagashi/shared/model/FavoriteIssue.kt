package com.star_zero.dagashi.shared.model

data class FavoriteIssue(
    val url: String,
    val title: String,
    val body: String,
    val milestone_id: String,
    val created_at: Long
)
