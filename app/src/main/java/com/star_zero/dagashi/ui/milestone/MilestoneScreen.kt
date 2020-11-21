package com.star_zero.dagashi.ui.milestone

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumnFor
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Settings
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.viewModel
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.compose.navigate
import androidx.ui.tooling.preview.Preview
import com.star_zero.dagashi.R
import com.star_zero.dagashi.shared.model.Milestone
import com.star_zero.dagashi.ui.components.ErrorRetry
import com.star_zero.dagashi.ui.components.LoadingProgress
import com.star_zero.dagashi.ui.theme.DagashiAppTheme
import kotlinx.coroutines.launch

@Composable
fun MilestoneScreen(navController: NavController, viewModelFactory: ViewModelProvider.Factory) {
    val viewModel: MilestoneViewModel = viewModel(factory = viewModelFactory)
    Surface(color = MaterialTheme.colors.background) {
        Scaffold(
            topBar = {
                AppBar(
                    viewModel = viewModel,
                    navigateToSetting = {
                        navController.navigate("setting")
                    }
                )
            }
        ) {
            MilestoneContent(
                viewModel = viewModel,
                navigateToIssue = { milestone ->
                    navController.navigate("issue/${milestone.path}/${milestone.title}")
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
                Icon(Icons.Filled.Settings)
            }

            IconButton(onClick = {
                coroutineScope.launch {
                    viewModel.refresh()
                }
            }) {
                Icon(Icons.Filled.Refresh)
            }
        }
    )
}

@Composable
private fun MilestoneContent(viewModel: MilestoneViewModel, navigateToIssue: (Milestone) -> Unit) {
    LaunchedEffect(Unit) {
        viewModel.getMilestones()
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
    LazyColumnFor(
        items = milestones,
        contentPadding = PaddingValues(top = 8.dp, bottom = 8.dp)
    ) { milestone ->
        MilestoneCard(milestone, navigateToIssue)
    }
}

@Composable
private fun MilestoneCard(milestone: Milestone, navigateToIssue: (Milestone) -> Unit) {
    Card(
        modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
            .fillMaxWidth()
            .clickable(onClick = {
                navigateToIssue(milestone)
            })
    ) {
        Column(
            modifier = Modifier.padding(8.dp)
        ) {
            ProvideEmphasis(emphasis = AmbientEmphasisLevels.current.high) {
                Text(
                    text = milestone.title,
                    style = MaterialTheme.typography.subtitle1,
                )
            }
            Spacer(modifier = Modifier.preferredHeight(8.dp))
            ProvideEmphasis(emphasis = AmbientEmphasisLevels.current.medium) {
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
