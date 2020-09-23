package com.star_zero.dagashi.core.data.model

data class Issue(
    val url: String,
    val title: String,
    val body: String,
    val labels: List<Label>
)
