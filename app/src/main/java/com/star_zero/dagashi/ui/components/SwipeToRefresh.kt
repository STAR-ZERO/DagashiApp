package com.star_zero.dagashi.ui.components

import androidx.compose.foundation.Box
import androidx.compose.foundation.layout.Stack
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offsetPx
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.FractionalThreshold
import androidx.compose.material.rememberSwipeableState
import androidx.compose.material.swipeable
import androidx.compose.runtime.Composable
import androidx.compose.runtime.onCommit
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.gesture.scrollorientationlocking.Orientation
import androidx.compose.ui.platform.DensityAmbient
import androidx.compose.ui.unit.dp

// Copy from: https://github.com/android/compose-samples/blob/e5e45ee2adfa9b050309aae1d25aad76b5452aca/JetNews/app/src/main/java/com/example/jetnews/ui/SwipeToRefresh.kt

private val RefreshDistance = 80.dp

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun SwipeToRefreshLayout(
    refreshingState: Boolean,
    onRefresh: () -> Unit,
    refreshIndicator: @Composable () -> Unit,
    content: @Composable () -> Unit
) {
    val refreshDistance = with(DensityAmbient.current) { RefreshDistance.toPx() }
    val state = rememberSwipeableState(refreshingState)
    // TODO (https://issuetracker.google.com/issues/164113834): This state->event trampoline is a
    //  workaround for a bug in the SwipableState API. It should be replaced with a correct solution
    //  when that bug closes.
    onCommit(refreshingState) {
        state.animateTo(refreshingState)
    }
    // TODO (https://issuetracker.google.com/issues/164113834): Hoist state changes when bug is
    //  fixed and do this logic in the ViewModel. Currently, state.value is a duplicated source of
    //  truth of refreshingState
    onCommit(state.value) {
        if (state.value) {
            onRefresh()
        }
    }

    Stack(
        modifier = Modifier
            .fillMaxWidth() // This line is added by @STAR-ZERO.
            .swipeable(
            state = state,
            enabled = !state.value,
            anchors = mapOf(
                -refreshDistance to false,
                refreshDistance to true
            ),
            thresholds = { _, _ -> FractionalThreshold(0.5f) },
            orientation = Orientation.Vertical
        )
    ) {
        content()
        Box(Modifier.gravity(Alignment.TopCenter).offsetPx(y = state.offset)) {
            if (state.offset.value != -refreshDistance) {
                refreshIndicator()
            }
        }
    }
}
