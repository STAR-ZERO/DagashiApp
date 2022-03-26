package com.star_zero.dagashi.ui.milestone

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
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
import androidx.compose.material.ScaffoldState
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.star_zero.dagashi.R
import com.star_zero.dagashi.shared.model.Milestone
import com.star_zero.dagashi.ui.components.ErrorRetry
import com.star_zero.dagashi.ui.destinations.IssueScreenDestination
import com.star_zero.dagashi.ui.destinations.SettingScreenDestination
import com.star_zero.dagashi.ui.theme.DagashiAppTheme

@Destination(start = true)
@Composable
fun MilestoneScreen(navigator: DestinationsNavigator) {
    val viewModel: MilestoneViewModel = hiltViewModel()

    LaunchedEffect(Unit) {
        viewModel.getMilestones(false)
    }

    MilestoneContainer(
        uiState = viewModel.uiState,
        onRefresh = {
            viewModel.refresh()
        },
        navigateIssue = { milestone ->
            navigator.navigate(
                IssueScreenDestination(
                    path = milestone.path,
                    title = milestone.title
                )
            )
        },
        navigateSetting = {
            navigator.navigate(SettingScreenDestination())
        }
    )
}

@Composable
private fun MilestoneContainer(
    uiState: MilestoneUiState,
    onRefresh: () -> Unit,
    navigateIssue: (Milestone) -> Unit,
    navigateSetting: () -> Unit
) {
    Surface(color = MaterialTheme.colors.background) {
        val scaffoldState = rememberScaffoldState()

        Scaffold(
            scaffoldState = scaffoldState,
            topBar = {
                AppBar(
                    navigateToSetting = {
                        navigateSetting()
                    }
                )
            },
        ) {
            MilestoneContent(
                uiState = uiState,
                scaffoldState = scaffoldState,
                onRefresh = onRefresh,
                navigateToIssue = { milestone ->
                    navigateIssue(milestone)
                }
            )
        }
    }
}

@Composable
private fun AppBar(navigateToSetting: () -> Unit) {
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
        }
    )
}

@Composable
private fun MilestoneContent(
    uiState: MilestoneUiState,
    scaffoldState: ScaffoldState,
    onRefresh: () -> Unit,
    navigateToIssue: (Milestone) -> Unit,
) {
    if (uiState.error && uiState.milestones.isEmpty()) {
        ErrorRetry(
            onRetry = {
                onRefresh()
            }
        )
    } else {
        SwipeRefresh(
            state = rememberSwipeRefreshState(uiState.loading),
            onRefresh = { onRefresh() }
        ) {
            if (uiState.milestones.isEmpty()) {
                Box(modifier = Modifier.fillMaxSize()) // dummy ui for SwipeRefresh
            } else {
                MilestoneList(uiState.milestones, navigateToIssue)
            }
        }

        if (uiState.error) {
            val message = stringResource(id = R.string.text_error)
            LaunchedEffect(scaffoldState.snackbarHostState) {
                scaffoldState.snackbarHostState.showSnackbar(message)
            }
        }
    }
}

@Composable
private fun MilestoneList(milestones: List<Milestone>, navigateToIssue: (Milestone) -> Unit) {
    LazyColumn(contentPadding = PaddingValues(top = 8.dp, bottom = 8.dp)) {
        items(milestones, key = { it.id }) { milestone ->
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
            }),
        elevation = 4.dp,
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
