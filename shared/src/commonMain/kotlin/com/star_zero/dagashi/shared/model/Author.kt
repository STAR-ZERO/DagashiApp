package com.star_zero.dagashi.shared.model

import com.star_zero.dagashi.shared.platform.ComposeImmutable

@ComposeImmutable
data class Author(
    val name: String,
    val url: String,
    val avatarUrl: String,
)
