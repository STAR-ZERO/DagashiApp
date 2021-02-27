package com.star_zero.dagashi.ui.components

import androidx.compose.foundation.layout.size
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun LoadingProgress(modifier: Modifier = Modifier) {
    CircularProgressIndicator(
        modifier = modifier
            .size(48.dp),
        strokeWidth = 4.dp
    )
}
