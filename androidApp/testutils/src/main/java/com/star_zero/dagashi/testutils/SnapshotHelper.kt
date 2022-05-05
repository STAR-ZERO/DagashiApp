package com.star_zero.dagashi.testutils

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.snapshots.Snapshot

/**
 * The change history of MutableState is returned as a List by the processing in the argument block.
 *
 * limitation: Not supported multiple MutableState
 */
inline fun <reified T> stateValues(initialValue: T? = null, block: () -> Unit): List<T> {
    val result = mutableListOf<T>()
    if (initialValue != null) {
        result.add(initialValue)
    }
    val observer: (Any) -> Unit = { state ->
        if (state is MutableState<*> && state.value is T) {
            result.add(state.value as T)
        }
    }

    Snapshot.takeMutableSnapshot(writeObserver = observer).apply {
        enter {
            block()
        }
        dispose()
    }

    return result
}
