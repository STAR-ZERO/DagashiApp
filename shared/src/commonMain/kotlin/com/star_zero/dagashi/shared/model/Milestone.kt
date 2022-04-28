package com.star_zero.dagashi.shared.model

import com.star_zero.dagashi.shared.platform.PlatformParcelable
import com.star_zero.dagashi.shared.platform.PlatformParcelize

@PlatformParcelize
data class Milestone(
    val id: String,
    val title: String,
    val body: String,
    val path: String,
) : PlatformParcelable
