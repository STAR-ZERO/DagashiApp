package com.star_zero.dagashi.features.setting

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Checkbox
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.ramcosta.composedestinations.annotation.Destination
import com.star_zero.dagashi.core.ui.theme.DagashiAppTheme

@Destination
@Composable
fun SettingScreen() {
    val viewModel: SettingViewModel = hiltViewModel()
    val uiState by viewModel.uiState.collectAsState(SettingUiState())

    SettingContainer(
        uiState = uiState,
        updateOpenLinkInApp = {
            viewModel.updateOpenLinkInApp(it)
        },
    )
}

@Composable
private fun SettingContainer(
    uiState: SettingUiState,
    updateOpenLinkInApp: (Boolean) -> Unit,
) {
    Surface(color = MaterialTheme.colors.background) {
        Scaffold(
            topBar = {
                AppBar()
            }
        ) { innerPadding ->
            SettingContent(
                uiState = uiState,
                updateOpenLinkInApp = updateOpenLinkInApp,
                modifier = Modifier.padding(innerPadding)
            )
        }
    }
}

@Composable
private fun AppBar() {
    TopAppBar(
        title = {
            Text(text = stringResource(id = R.string.setting_title))
        },
    )
}

@Composable
private fun SettingContent(
    uiState: SettingUiState,
    updateOpenLinkInApp: (Boolean) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier
    ) {
        item {
            OpenLinkSetting(
                isOpenLinkInApp = uiState.isOpenLinkInApp,
                updateOpenLinkInApp = { enabled ->
                    updateOpenLinkInApp(enabled)
                }
            )
        }
    }
}

@Composable
private fun OpenLinkSetting(isOpenLinkInApp: Boolean, updateOpenLinkInApp: (Boolean) -> Unit) {
    Row(
        modifier = Modifier
            .clickable(
                onClick = {
                    updateOpenLinkInApp(!isOpenLinkInApp)
                }
            )
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Box(modifier = Modifier.weight(1f)) {
            Text(
                text = stringResource(id = R.string.setting_open_link),
                style = MaterialTheme.typography.subtitle1,
            )
        }

        Spacer(modifier = Modifier.width(8.dp))

        Checkbox(
            checked = isOpenLinkInApp,
            onCheckedChange = updateOpenLinkInApp,
            modifier = Modifier.align(Alignment.CenterVertically)
        )
    }
}

// region Compose Preview
@Preview("OpenLinkSetting")
@Composable
private fun PreviewOpenLinkSettingSetting() {
    DagashiAppTheme {
        Surface(color = MaterialTheme.colors.background) {
            OpenLinkSetting(true) { }
        }
    }
}

@Preview("OpenLinkSetting dark theme")
@Composable
private fun PreviewOpenLinkSettingDark() {
    DagashiAppTheme(darkTheme = true) {
        Surface(color = MaterialTheme.colors.background) {
            OpenLinkSetting(true) { }
        }
    }
}
// endregion
