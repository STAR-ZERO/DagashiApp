package com.star_zero.dagashi.shared.model

import com.star_zero.dagashi.shared.platform.ComposeImmutable

@ComposeImmutable
data class Comment(
    val body: String,
    val author: Author,
)
