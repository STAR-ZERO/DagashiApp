package com.star_zero.dagashi.shared.model

import com.star_zero.dagashi.shared.platform.ComposeImmutable

@ComposeImmutable
data class Label(
    val name: String,
    val color: Long, // 0xFF0000FF -> Blue
)
