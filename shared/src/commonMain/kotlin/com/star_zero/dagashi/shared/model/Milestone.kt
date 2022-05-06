package com.star_zero.dagashi.shared.model

import com.star_zero.dagashi.shared.platform.ComposeImmutable
import com.star_zero.dagashi.shared.platform.PlatformParcelable
import com.star_zero.dagashi.shared.platform.PlatformParcelize

@ComposeImmutable
@PlatformParcelize
data class Milestone(
    val id: String,
    val title: String,
    val body: String,
    val path: String,
) : PlatformParcelable
