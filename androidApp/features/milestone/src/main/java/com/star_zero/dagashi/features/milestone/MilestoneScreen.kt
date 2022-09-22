package com.star_zero.dagashi.features.milestone

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.WindowInsetsSides
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.only
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import com.star_zero.dagashi.core.CoreString
import com.star_zero.dagashi.core.tools.DagashiPreview
import com.star_zero.dagashi.core.ui.components.ErrorRetry
import com.star_zero.dagashi.core.ui.theme.DagashiAppTheme
import com.star_zero.dagashi.shared.model.Milestone

@Composable
fun MilestoneRoute(
    navigateIssue: (Milestone) -> Unit,
    selectedMilestone: Milestone? = null // for 2 Pane Layout
) {
    val viewModel: MilestoneViewModel = hiltViewModel()

    MilestoneScreen(
        uiState = viewModel.uiState,
        selectedMilestone = selectedMilestone,
        onRefresh = viewModel::refresh,
        navigateIssue = navigateIssue,
        consumeEvent = viewModel::consumeEvent,
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun MilestoneScreen(
    uiState: MilestoneUiState,
    selectedMilestone: Milestone?,
    onRefresh: () -> Unit,
    navigateIssue: (Milestone) -> Unit,
    consumeEvent: (MilestoneEvent) -> Unit,
) {
    Surface {
        val snackbarHostState = remember { SnackbarHostState() }

        Scaffold(
            snackbarHost = { SnackbarHost(snackbarHostState) },
            topBar = {
                AppBar()
            },
        ) { innerPadding ->
            MilestoneContent(
                uiState = uiState,
                snackbarHostState = snackbarHostState,
                onRefresh = onRefresh,
                navigateToIssue = { milestone ->
                    navigateIssue(milestone)
                },
                consumeEvent = consumeEvent,
                modifier = Modifier.padding(innerPadding),
                selectedMilestone = selectedMilestone
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun AppBar() {
    CenterAlignedTopAppBar(
        title = {
            Text(text = stringResource(id = R.string.milestone_title))
        },
        modifier = Modifier.windowInsetsPadding(
            WindowInsets.safeDrawing.only(WindowInsetsSides.Top)
        )
    )
}

@Composable
private fun MilestoneContent(
    uiState: MilestoneUiState,
    snackbarHostState: SnackbarHostState,
    onRefresh: () -> Unit,
    navigateToIssue: (Milestone) -> Unit,
    consumeEvent: (MilestoneEvent) -> Unit,
    modifier: Modifier = Modifier,
    selectedMilestone: Milestone?,
) {

    uiState.events.firstOrNull()?.let { event ->
        when (event) {
            is MilestoneEvent.ErrorGetMilestone -> {
                val message = stringResource(id = CoreString.text_error)
                LaunchedEffect(snackbarHostState) {
                    snackbarHostState.showSnackbar(message)
                    consumeEvent(event)
                }
            }
        }
    }

    if (uiState.error) {
        ErrorRetry(
            onRetry = {
                onRefresh()
            },
            modifier = modifier
        )
    } else {
        SwipeRefresh(
            state = rememberSwipeRefreshState(uiState.loading),
            onRefresh = { onRefresh() },
            modifier = modifier
        ) {
            if (uiState.milestones.isEmpty()) {
                Box(modifier = Modifier.fillMaxSize()) // dummy ui for SwipeRefresh
            } else {
                MilestoneList(uiState.milestones, navigateToIssue, selectedMilestone)
            }
        }
    }
}

@Composable
private fun MilestoneList(
    milestones: List<Milestone>,
    navigateToIssue: (Milestone) -> Unit,
    selectedMilestone: Milestone?
) {
    LazyColumn(
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
    ) {
        items(milestones, key = { it.id }) { milestone ->
            MilestoneCard(milestone, navigateToIssue, selectedMilestone)
        }
    }
}

@Composable
private fun MilestoneCard(
    milestone: Milestone,
    navigateToIssue: (Milestone) -> Unit,
    selectedMilestone: Milestone?,
) {
    ElevatedCard(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = {
                navigateToIssue(milestone)
            }),
    ) {
        val selectedColor = MaterialTheme.colorScheme.primaryContainer
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .then(
                    if (selectedMilestone?.id == milestone.id) {
                        Modifier.drawBehind {
                            drawRect(selectedColor)
                        }
                    } else {
                        Modifier
                    }
                )
                .padding(8.dp)
        ) {
            Text(
                text = milestone.title,
                style = MaterialTheme.typography.titleLarge,
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = milestone.body,
                style = MaterialTheme.typography.bodyMedium,
            )
        }
    }
}

@DagashiPreview
@Composable
private fun PreviewMilestoneScreen() {
    val milestones = (1..10).map {
        Milestone(
            id = "$it",
            title = "Title $it",
            body = "Body Body",
            path = "path/$it"
        )
    }

    DagashiAppTheme {
        MilestoneScreen(
            uiState = MilestoneUiState(
                milestones = milestones
            ),
            selectedMilestone = milestones[1],
            onRefresh = {},
            navigateIssue = {},
            consumeEvent = {}
        )
    }
}

@DagashiPreview
@Composable
private fun PreviewMilestoneScreenError() {
    DagashiAppTheme {
        MilestoneScreen(
            uiState = MilestoneUiState(
                error = true
            ),
            selectedMilestone = null,
            onRefresh = {},
            navigateIssue = {},
            consumeEvent = {}
        )
    }
}
