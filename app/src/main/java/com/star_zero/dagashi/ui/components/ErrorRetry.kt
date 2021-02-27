package com.star_zero.dagashi.ui.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.star_zero.dagashi.R

@Composable
fun ErrorRetry(onRetry: () -> Unit) {
    Box(
        modifier = Modifier.fillMaxWidth().fillMaxHeight()
    ) {
        Column(
            modifier = Modifier.align(Alignment.Center),
        ) {
            Text(
                text = stringResource(id = R.string.text_error)
            )

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                modifier = Modifier.align(Alignment.CenterHorizontally),
                onClick = onRetry
            ) {
                Text(text = stringResource(id = R.string.button_retry))
            }
        }
    }
}
