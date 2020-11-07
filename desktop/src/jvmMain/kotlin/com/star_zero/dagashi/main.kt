package com.star_zero.dagashi

import androidx.compose.desktop.Window
import androidx.compose.ui.unit.IntSize
import com.star_zero.dagashi.milestone.MilestoneScreen
import com.star_zero.dagashi.theme.DagashiAppTheme

fun main() {
    Window(
        title = "DagashiApp",
        size = IntSize(480, 800)
    ) {
        DagashiAppTheme {
            MilestoneScreen()
        }
    }
}
