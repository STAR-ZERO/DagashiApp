package com.star_zero.dagashi.shared.model

data class Issue(
    val url: String,
    val title: String,
    val body: String,
    val labels: List<Label>,
    val comments: List<Comment>
)
