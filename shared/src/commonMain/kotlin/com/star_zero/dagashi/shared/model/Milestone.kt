package com.star_zero.dagashi.shared.model

import com.star_zero.dagashi.shared.utils.Parcelable
import com.star_zero.dagashi.shared.utils.Parcelize

@Parcelize
data class Milestone(
    val id: String,
    val title: String,
    val body: String,
    val path: String,
): Parcelable
