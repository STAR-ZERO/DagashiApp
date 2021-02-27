package com.star_zero.dagashi.ui.components

import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.layout.Placeable
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.star_zero.dagashi.ui.theme.DagashiAppTheme
import kotlin.math.max

@Composable
fun FlowRow(
    modifier: Modifier = Modifier,
    verticalSpacing: Dp = 0.dp,
    horizontalSpacing: Dp = 0.dp,
    content: @Composable () -> Unit
) {
    Layout(modifier = modifier, content = content) { measurables, constraints ->
        val placeables = measurables.map { measurable ->
            measurable.measure(constraints)
        }

        data class FlowRowContent(
            val placeable: Placeable,
            val x: Int,
            val y: Int
        )

        var y = 0
        var x = 0
        var rowMaxY = 0
        val flowRowContents = mutableListOf<FlowRowContent>()

        val verticalSpacingPx = verticalSpacing.roundToPx()
        val horizontalSpacingPx = horizontalSpacing.roundToPx()

        placeables.forEach { placeable ->
            if (placeable.width + horizontalSpacingPx + x > constraints.maxWidth) {
                x = 0
                y += rowMaxY
                rowMaxY = 0
            }
            rowMaxY = max(placeable.height + verticalSpacingPx, rowMaxY)

            flowRowContents.add(FlowRowContent(placeable, x, y))
            x += placeable.width + horizontalSpacingPx
        }
        y += rowMaxY

        layout(constraints.maxWidth, y) {
            flowRowContents.forEach {
                it.placeable.place(it.x, it.y)
            }
        }
    }
}

@Preview
@Composable
fun PreviewFlowRow() {
    DagashiAppTheme {
        Surface {
            FlowRow(
                verticalSpacing = 8.dp,
                horizontalSpacing = 8.dp,
            ) {
                Text(text = "TEST1")
                Text(text = "TEST2")
                Text(text = "TEST3")
                Text(text = "TEST4")
                Text(text = "TEST5")
                Text(text = "TEST6")
                Text(text = "TEST7")
                Text(text = "TEST8")
                Text(text = "TEST9")
                Text(text = "TEST10")
                Text(text = "TEST11")
                Text(text = "TEST12")
            }
        }
    }
}