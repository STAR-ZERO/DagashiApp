package com.star_zero.dagashi.ui.milestone

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Card
import androidx.compose.material.ContentAlpha
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.LocalContentAlpha
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Settings
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import com.star_zero.dagashi.R
import com.star_zero.dagashi.shared.model.Milestone
import com.star_zero.dagashi.ui.components.ErrorRetry
import com.star_zero.dagashi.ui.components.LoadingProgress
import com.star_zero.dagashi.ui.theme.DagashiAppTheme
import com.star_zero.dagashi.ui.util.LocalNavigator
import kotlinx.coroutines.launch

@Composable
fun MilestoneScreen(viewModelFactory: ViewModelProvider.Factory) {
    val viewModel: MilestoneViewModel = viewModel(factory = viewModelFactory)

    val navigator = LocalNavigator.current

    Surface(color = MaterialTheme.colors.background) {
        Scaffold(
            topBar = {
                AppBar(
                    viewModel = viewModel,
                    navigateToSetting = {
                        navigator.navigateSetting()
                    }
                )
            }
        ) {
            MilestoneContent(
                viewModel = viewModel,
                navigateToIssue = { milestone ->
                    navigator.navigateIssue(milestone.path, milestone.title)
                }
            )
        }
    }
}

@Composable
private fun AppBar(viewModel: MilestoneViewModel, navigateToSetting: () -> Unit) {
    val coroutineScope = rememberCoroutineScope()

    TopAppBar(
        title = {
            Text(text = stringResource(id = R.string.milestone_title))
        },
        actions = {
            IconButton(onClick = {
                navigateToSetting()
            }) {

                Icon(Icons.Filled.Settings, "Settings")
            }

            IconButton(onClick = {
                coroutineScope.launch {
                    viewModel.refresh()
                }
            }) {
                Icon(Icons.Filled.Refresh, "Refresh")
            }
        }
    )
}

@Composable
private fun MilestoneContent(viewModel: MilestoneViewModel, navigateToIssue: (Milestone) -> Unit) {
    LaunchedEffect(null) {
        viewModel.getMilestones(false)
    }

    val coroutineScope = rememberCoroutineScope()

    @Suppress("CascadeIf")
    if (viewModel.hasError) {
        ErrorRetry(
            onRetry = {
                coroutineScope.launch {
                    viewModel.refresh()
                }
            }
        )
    } else if (viewModel.loading) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
        ) {
            LoadingProgress(
                modifier = Modifier.align(Alignment.Center)
            )
        }
    } else {
        MilestoneList(viewModel.milestones, navigateToIssue)
    }
}

@Composable
private fun MilestoneList(milestones: List<Milestone>, navigateToIssue: (Milestone) -> Unit) {
    LazyColumn(contentPadding = PaddingValues(top = 8.dp, bottom = 8.dp)) {
        items(milestones) { milestone ->
            MilestoneCard(milestone, navigateToIssue)
        }
    }
}

@Composable
private fun MilestoneCard(milestone: Milestone, navigateToIssue: (Milestone) -> Unit) {
    Card(
        modifier = Modifier
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .fillMaxWidth()
            .clickable(onClick = {
                navigateToIssue(milestone)
            })
    ) {
        Column(
            modifier = Modifier.padding(8.dp)
        ) {
            CompositionLocalProvider(LocalContentAlpha provides ContentAlpha.high) {
                Text(
                    text = milestone.title,
                    style = MaterialTheme.typography.subtitle1,
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            CompositionLocalProvider(LocalContentAlpha provides ContentAlpha.medium) {
                Text(
                    text = milestone.body,
                    style = MaterialTheme.typography.caption,
                )
            }
        }
    }
}

// region Compose Preview
@Preview("Milestone card")
@Composable
private fun PreviewMilestone() {
    val milestone = Milestone(
        "id1",
        "135 2020-08-30",
        "Sample Sample",
        ""
    )
    DagashiAppTheme {
        MilestoneCard(milestone, {})
    }
}

@Preview("Milestone card dark theme")
@Composable
private fun PreviewMilestoneDark() {
    val milestone = Milestone(
        "id1",
        "135 2020-08-30",
        "Sample Sample",
        ""
    )
    DagashiAppTheme(darkTheme = true) {
        MilestoneCard(milestone, {})
    }
}
// endregion
