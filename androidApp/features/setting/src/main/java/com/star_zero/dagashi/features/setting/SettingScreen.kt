package com.star_zero.dagashi.features.setting

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.WindowInsetsSides
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.only
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.ramcosta.composedestinations.annotation.Destination
import com.star_zero.dagashi.core.CoreString
import com.star_zero.dagashi.core.tools.DagashiPreview
import com.star_zero.dagashi.core.ui.theme.DagashiAppTheme
import com.star_zero.dagashi.shared.model.DarkThemeType

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
        updateDarkTheme = {
            viewModel.updateDarkTheme(it)
        },
        updateDynamicTheme = {
            viewModel.updateDynamicTheme(it)
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun SettingScreen(
    uiState: SettingUiState,
    windowWidthSizeClass: WindowWidthSizeClass,
    updateOpenLinkInApp: (Boolean) -> Unit,
    updateDarkTheme: (DarkThemeType) -> Unit,
    updateDynamicTheme: (Boolean) -> Unit,
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
                updateDarkTheme = updateDarkTheme,
                updateDynamicTheme = updateDynamicTheme,
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
    updateDarkTheme: (DarkThemeType) -> Unit,
    updateDynamicTheme: (Boolean) -> Unit,
    modifier: Modifier = Modifier
) {
    val itemContentPadding = if (windowWidthSizeClass == WindowWidthSizeClass.Compact) {
        PaddingValues(horizontal = 16.dp)
    } else {
        PaddingValues(horizontal = 64.dp)
    }

    LazyColumn(
        modifier = modifier,
        contentPadding = PaddingValues(vertical = 8.dp),
    ) {
        item {
            OpenLinkSetting(
                isOpenLinkInApp = uiState.isOpenLinkInApp,
                updateOpenLinkInApp = { enabled ->
                    updateOpenLinkInApp(enabled)
                },
                contentPadding = itemContentPadding
            )
        }

        item {
            DarkThemeSetting(
                darkThemeType = uiState.darkThemeType,
                updateDarkTheme = updateDarkTheme,
                contentPadding = itemContentPadding,
            )
        }

        if (uiState.isSupportDynamic) {
            item {
                DynamicThemeSetting(
                    enableDynamicTheme = uiState.dynamicTheme,
                    updateDynamicTheme = updateDynamicTheme,
                    contentPadding = itemContentPadding
                )
            }
        }
    }
}

@Composable
private fun OpenLinkSetting(
    isOpenLinkInApp: Boolean,
    updateOpenLinkInApp: (Boolean) -> Unit,
    contentPadding: PaddingValues
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .heightIn(min = 80.dp)
            .fillMaxWidth()
            .selectable(
                selected = isOpenLinkInApp,
                onClick = { updateOpenLinkInApp(!isOpenLinkInApp) },
                role = Role.Switch
            )
            .padding(contentPadding)
    ) {
        Text(
            text = stringResource(id = R.string.setting_open_link),
            style = MaterialTheme.typography.titleLarge,
            modifier = Modifier.weight(1f)
        )

        Spacer(modifier = Modifier.width(8.dp))

        Switch(
            checked = isOpenLinkInApp,
            onCheckedChange = null,
            modifier = Modifier.align(Alignment.CenterVertically)
        )
    }
}

@Composable
private fun DarkThemeSetting(
    darkThemeType: DarkThemeType,
    updateDarkTheme: (DarkThemeType) -> Unit,
    contentPadding: PaddingValues
) {
    var openDialog by rememberSaveable { mutableStateOf(false) }

    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .heightIn(min = 80.dp)
            .fillMaxWidth()
            .clickable(
                onClick = {
                    openDialog = true
                }
            )
            .padding(contentPadding)
    ) {
        Column {
            Text(
                text = stringResource(id = R.string.setting_dark_theme),
                style = MaterialTheme.typography.titleLarge,
            )
            Text(
                text = stringResource(id = darkThemeOptions.first { it.type == darkThemeType }.name),
                style = MaterialTheme.typography.bodyLarge,
            )
        }
    }

    if (openDialog) {
        DarkThemeOptionDialog(
            darkThemeType = darkThemeType,
            updateDarkTheme = updateDarkTheme,
            onDismiss = { openDialog = false }
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun DarkThemeOptionDialog(
    darkThemeType: DarkThemeType,
    updateDarkTheme: (DarkThemeType) -> Unit,
    onDismiss: () -> Unit
) {
    var selectedType by rememberSaveable {
        mutableStateOf(
            darkThemeOptions.first { it.type == darkThemeType }.type
        )
    }

    AlertDialog(
        onDismissRequest = onDismiss,
        confirmButton = {
            TextButton(
                onClick = {
                    updateDarkTheme(selectedType)
                    onDismiss()
                }
            ) {
                Text(text = stringResource(id = CoreString.ok))
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text(text = stringResource(id = CoreString.cancel))
            }
        },
        title = {
            Text(text = stringResource(id = R.string.setting_dark_theme))
        },
        text = {
            Column(Modifier.selectableGroup()) {
                darkThemeOptions.forEach { darkThemeOption ->
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .fillMaxWidth()
                            .selectable(
                                selected = selectedType == darkThemeOption.type,
                                onClick = { selectedType = darkThemeOption.type },
                                role = Role.RadioButton
                            )
                            .padding(16.dp),
                    ) {
                        RadioButton(
                            selected = selectedType == darkThemeOption.type,
                            onClick = null
                        )
                        Text(
                            text = stringResource(id = darkThemeOption.name),
                            style = MaterialTheme.typography.bodyLarge,
                            modifier = Modifier.padding(start = 16.dp)
                        )
                    }
                }
            }
        }
    )
}

@Composable
private fun DynamicThemeSetting(
    enableDynamicTheme: Boolean,
    updateDynamicTheme: (Boolean) -> Unit,
    contentPadding: PaddingValues
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .heightIn(min = 80.dp)
            .fillMaxWidth()
            .selectable(
                selected = enableDynamicTheme,
                onClick = { updateDynamicTheme(!enableDynamicTheme) },
                role = Role.Switch
            )
            .padding(contentPadding)
    ) {
        Text(
            text = stringResource(id = R.string.setting_dynamic_theme),
            style = MaterialTheme.typography.titleLarge,
            modifier = Modifier.weight(1f)
        )

        Spacer(modifier = Modifier.width(8.dp))

        Switch(
            checked = enableDynamicTheme,
            onCheckedChange = null,
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
            updateOpenLinkInApp = {},
            updateDarkTheme = {},
            updateDynamicTheme = {}
        )
    }
}
