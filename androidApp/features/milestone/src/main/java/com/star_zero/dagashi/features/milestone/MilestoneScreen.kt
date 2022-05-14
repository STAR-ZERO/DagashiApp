package com.star_zero.dagashi.features.milestone

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.spec.DestinationStyle
import com.star_zero.dagashi.core.CoreString
import com.star_zero.dagashi.core.ui.components.ErrorRetry
import com.star_zero.dagashi.shared.model.Milestone

@Destination(style = DestinationStyle.Runtime::class)
@Composable
fun MilestoneScreen(navigator: MilestoneNavigator) {
    val viewModel: MilestoneViewModel = hiltViewModel()

    MilestoneContainer(
        uiState = viewModel.uiState,
        onRefresh = {
            viewModel.refresh()
        },
        navigateIssue = { milestone ->
            navigator.navigateMilestoneToIssue(milestone)
        },
        consumeEvent = { event ->
            viewModel.consumeEvent(event)
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun MilestoneContainer(
    uiState: MilestoneUiState,
    onRefresh: () -> Unit,
    navigateIssue: (Milestone) -> Unit,
    consumeEvent: (MilestoneEvent) -> Unit
) {
    Surface {
        val snacbarHostState = remember { SnackbarHostState() }

        Scaffold(
            snackbarHost = { SnackbarHost(snacbarHostState) },
            topBar = {
                AppBar()
            },
        ) { innerPadding ->
            MilestoneContent(
                uiState = uiState,
                snackbarHostState = snacbarHostState,
                onRefresh = onRefresh,
                navigateToIssue = { milestone ->
                    navigateIssue(milestone)
                },
                consumeEvent = consumeEvent,
                modifier = Modifier.padding(innerPadding)
            )
        }
    }
}

@Composable
private fun AppBar() {
    CenterAlignedTopAppBar(
        title = {
            Text(text = stringResource(id = R.string.milestone_title))
        }
    )
}

@Composable
private fun MilestoneContent(
    uiState: MilestoneUiState,
    snackbarHostState: SnackbarHostState,
    onRefresh: () -> Unit,
    navigateToIssue: (Milestone) -> Unit,
    consumeEvent: (MilestoneEvent) -> Unit,
    modifier: Modifier = Modifier
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
                MilestoneList(uiState.milestones, navigateToIssue)
            }
        }
    }
}

@Composable
private fun MilestoneList(milestones: List<Milestone>, navigateToIssue: (Milestone) -> Unit) {
    LazyColumn(
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        items(milestones, key = { it.id }) { milestone ->
            MilestoneCard(milestone, navigateToIssue)
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun MilestoneCard(milestone: Milestone, navigateToIssue: (Milestone) -> Unit) {
    ElevatedCard(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = {
                navigateToIssue(milestone)
            }),
    ) {
        Column(
            modifier = Modifier.padding(8.dp)
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
