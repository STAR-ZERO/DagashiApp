package com.star_zero.dagashi.ui.setting

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Checkbox
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.star_zero.dagashi.R
import com.star_zero.dagashi.ui.theme.DagashiAppTheme
import com.star_zero.dagashi.ui.util.LocalNavigator

@Composable
fun SettingScreen(isOpenLinkInApp: Boolean, updateOpenLinkInApp: (Boolean) -> Unit) {
    val navigator = LocalNavigator.current
    Surface(color = MaterialTheme.colors.background) {
        Scaffold(
            topBar = {
                AppBar(
                    navigateBack = {
                        navigator.navigateBack()
                    }
                )
            }
        ) {
            SettingContent(isOpenLinkInApp, updateOpenLinkInApp)
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
                Icon(Icons.Filled.ArrowBack, "Back")
            }
        }
    )
}

@Composable
private fun SettingContent(isOpenLinkInApp: Boolean, updateOpenLinkInApp: (Boolean) -> Unit) {
    LazyColumn {
        item {
            OpenLinkSetting(
                isOpenLinkInApp = isOpenLinkInApp,
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
