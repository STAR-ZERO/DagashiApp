package com.star_zero.dagashi.theme

import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable

private val DarkColorPalette = darkColors(
    primary = purple200,
    primaryVariant = purple700,
    secondary = teal200
)

private val LightColorPalette = lightColors(
    primary = purple500,
    primaryVariant = purple700,
    secondary = teal200,

    /* Other default colors to override
    background = Color.White,
    surface = Color.White,
    onPrimary = Color.White,
    onSecondary = Color.Black,
    onBackground = Color.Black,
    onSurface = Color.Black,
    */
)

@Composable
fun DagashiAppTheme(content: @Composable() () -> Unit) {
    // TODO: Dark theme

    MaterialTheme(
        colors = LightColorPalette,
        typography = typography,
        shapes = shapes,
        content = content
    )
}
