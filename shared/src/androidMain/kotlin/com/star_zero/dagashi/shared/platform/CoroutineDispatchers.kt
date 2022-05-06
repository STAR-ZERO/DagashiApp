package com.star_zero.dagashi.shared.platform

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

actual object CoroutineDispatchers {
    actual val main: CoroutineDispatcher = Dispatchers.Main
    actual val io: CoroutineDispatcher = Dispatchers.IO
}
