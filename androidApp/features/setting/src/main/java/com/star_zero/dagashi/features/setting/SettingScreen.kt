package com.star_zero.dagashi.features.setting

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.WindowInsetsSides
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.only
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.Checkbox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.ramcosta.composedestinations.annotation.Destination
import com.star_zero.dagashi.core.tools.DagashiPreview
import com.star_zero.dagashi.core.ui.theme.DagashiAppTheme

@Destination
@Composable
fun SettingRoute(windowSizeClass: WindowSizeClass) {
    val viewModel: SettingViewModel = hiltViewModel()
    val uiState by viewModel.uiState.collectAsState(SettingUiState())

    SettingScreen(
        uiState = uiState,
        windowWidthSizeClass = windowSizeClass.widthSizeClass,
        updateOpenLinkInApp = {
            viewModel.updateOpenLinkInApp(it)
        },
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun SettingScreen(
    uiState: SettingUiState,
    windowWidthSizeClass: WindowWidthSizeClass,
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
                windowWidthSizeClass = windowWidthSizeClass,
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
        modifier = Modifier.windowInsetsPadding(
            WindowInsets.safeDrawing.only(WindowInsetsSides.Top)
        )
    )
}

@Composable
private fun SettingContent(
    uiState: SettingUiState,
    windowWidthSizeClass: WindowWidthSizeClass,
    updateOpenLinkInApp: (Boolean) -> Unit,
    modifier: Modifier = Modifier
) {
    val contentPadding = if (windowWidthSizeClass == WindowWidthSizeClass.Compact) {
        PaddingValues(16.dp)
    } else {
        PaddingValues(vertical = 16.dp, horizontal = 64.dp)
    }

    LazyColumn(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(8.dp),
        contentPadding = contentPadding,
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
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .clickable(
                onClick = {
                    updateOpenLinkInApp(!isOpenLinkInApp)
                }
            )
            .fillMaxWidth()
    ) {
        Box(
            modifier = Modifier
                .weight(1f)
        ) {
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

@DagashiPreview
@Composable
private fun PreviewSettingScreen() {
    DagashiAppTheme {
        SettingScreen(
            uiState = SettingUiState(),
            windowWidthSizeClass = WindowWidthSizeClass.Compact,
            updateOpenLinkInApp = {}
        )
    }
}
