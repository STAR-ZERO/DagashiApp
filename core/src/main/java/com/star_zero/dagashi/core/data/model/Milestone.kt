package com.star_zero.dagashi.core.data.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Milestone(
    val id: String,
    val title: String,
    val description: String,
    val path: String,
) : Parcelable
