package com.star_zero.dagashi.ui.setting

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.compose.foundation.Icon
import androidx.compose.foundation.ScrollableColumn
import androidx.compose.foundation.Text
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.preferredWidth
import androidx.compose.material.Checkbox
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Recomposer
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.setContent
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.ui.tooling.preview.Preview
import com.star_zero.dagashi.R
import com.star_zero.dagashi.ui.theme.DagashiAppTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SettingFragment : Fragment() {

    private val viewModel: SettingViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        return FrameLayout(requireContext()).apply {
            id = R.id.setting_fragment

            layoutParams = ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT
            )

            setContent(Recomposer.current()) {
                DagashiAppTheme {
                    Surface(color = MaterialTheme.colors.background) {
                        Scaffold(
                            topBar = {
                                AppBar(
                                    onBack = ::onBack
                                )
                            }
                        ) {
                            SettingContent(viewModel)
                        }
                    }
                }
            }
        }
    }

    private fun onBack() {
        findNavController().popBackStack()
    }
}

@Composable
private fun AppBar(onBack: () -> Unit) {
    TopAppBar(
        title = {
            Text(text = stringResource(id = R.string.setting_title))
        },
        navigationIcon = {
            IconButton(onClick = onBack) {
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
        Text(
            text = stringResource(id = R.string.setting_open_link),
            style = MaterialTheme.typography.subtitle1,
            modifier = Modifier.weight(1f)
        )

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
