package com.star_zero.dagashi.core.ui.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.star_zero.dagashi.core.R

@Composable
fun ErrorRetry(
    onRetry: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier.fillMaxWidth().fillMaxHeight()
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
