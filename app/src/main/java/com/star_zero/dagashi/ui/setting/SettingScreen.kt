package com.star_zero.dagashi.ui.setting

import androidx.compose.foundation.ScrollableColumn
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.viewModel
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import com.star_zero.dagashi.R
import com.star_zero.dagashi.ui.theme.DagashiAppTheme
import kotlinx.coroutines.launch

@Composable
fun SettingScreen(navController: NavController, viewModelFactory: ViewModelProvider.Factory) {
    val viewModel: SettingViewModel = viewModel(factory = viewModelFactory)
    Surface(color = MaterialTheme.colors.background) {
        Scaffold(
            topBar = {
                AppBar(
                    navigateBack = {
                        navController.popBackStack()
                    }
                )
            }
        ) {
            SettingContent(viewModel)
        }
    }
}

@Composable
private fun AppBar(navigateBack: () -> Unit) {
    TopAppBar(
        title = {
            Text(text = stringResource(id = R.string.setting_title))
        },
        navigationIcon = {
            IconButton(onClick = {
                navigateBack()
            }) {
                Icon(Icons.Filled.ArrowBack)
            }
        }
    )
}

@Composable
private fun SettingContent(viewModel: SettingViewModel) {
    val isOpenLinkInApp by viewModel.isOpenLinkInApp.collectAsState(initial = false)

    val coroutineScope = rememberCoroutineScope()

    ScrollableColumn {
        OpenLinkSetting(
            isOpenLinkInApp = isOpenLinkInApp,
            updateOpenLinkInApp = { enabled ->
                coroutineScope.launch {
                    viewModel.updateOpenLinkInApp(enabled)
                }
            }
        )
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

        Spacer(modifier = Modifier.preferredWidth(8.dp))

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
