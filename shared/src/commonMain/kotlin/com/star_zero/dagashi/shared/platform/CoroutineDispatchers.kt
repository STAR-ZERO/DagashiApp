package com.star_zero.dagashi.shared.platform

import kotlinx.coroutines.CoroutineDispatcher

expect object CoroutineDispatchers  {
    val main: CoroutineDispatcher
    val io: CoroutineDispatcher
}
