package com.star_zero.dagashi.ui.util

import kotlinx.coroutines.flow.MutableStateFlow

fun <T> MutableStateFlow<T>.update(action: T.() -> T) {
    value = value.action()
}