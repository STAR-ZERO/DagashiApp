package com.star_zero.dagashi.features.setting

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.Checkbox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.ramcosta.composedestinations.annotation.Destination

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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun SettingContainer(
    uiState: SettingUiState,
    updateOpenLinkInApp: (Boolean) -> Unit,
) {
    Surface {
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
    CenterAlignedTopAppBar(
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

@OptIn(ExperimentalMaterial3Api::class)
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
                style = MaterialTheme.typography.titleMedium,
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
