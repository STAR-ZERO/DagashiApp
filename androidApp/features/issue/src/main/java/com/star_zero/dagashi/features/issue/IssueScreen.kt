package com.star_zero.dagashi.features.issue

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.ScaffoldState
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.spec.DestinationStyle
import com.star_zero.dagashi.core.CoreString
import com.star_zero.dagashi.core.ui.components.ErrorRetry
import com.star_zero.dagashi.core.usecase.OpenLinkUseCase
import com.star_zero.dagashi.shared.model.Issue
import com.star_zero.dagashi.shared.model.Milestone

@Destination(style = DestinationStyle.Runtime::class)
@Composable
fun IssueScreen(
    navigator: IssueNavigator,
    milestone: Milestone
) {
    val viewModel: IssueViewModel = hiltViewModel()

    val uriHandler = LocalUriHandler.current
    val context = LocalContext.current
    val openLinkUseCase = remember(uriHandler, context) {
        OpenLinkUseCase(context, uriHandler)
    }

    IssueContainer(
        uiState = viewModel.uiState,
        title = milestone.title,
        onRefresh = viewModel::getIssues,
        onOpenLink = {
            openLinkUseCase(it, viewModel.uiState.isOpenLinkInApp)
        },
        onClickFavorite = viewModel::toggleFavorite,
        navigateBack = navigator::navigateBack
    )
}

@Composable
private fun IssueContainer(
    uiState: IssueUiState,
    title: String,
    onRefresh: () -> Unit,
    onOpenLink: (String) -> Unit,
    onClickFavorite: (Issue, Boolean) -> Unit,
    navigateBack: () -> Unit
) {
    Surface(color = MaterialTheme.colors.background) {
        val scaffoldState = rememberScaffoldState()

        Scaffold(
            scaffoldState = scaffoldState,
            topBar = {
                AppBar(
                    title = title,
                    navigateBack = {
                        navigateBack()
                    }
                )
            }
        ) {
            IssueContent(
                uiState = uiState,
                scaffoldState = scaffoldState,
                onRefresh = onRefresh,
                onOpenLink = onOpenLink,
                onClickFavorite = onClickFavorite,
            )
        }
    }
}

@Composable
private fun AppBar(
    title: String,
    navigateBack: () -> Unit
) {
    TopAppBar(
        title = {
            Text(text = title)
        },
        navigationIcon = {
            IconButton(onClick = {
                navigateBack()
            }) {
                Icon(Icons.Filled.ArrowBack, "Back")
            }
        },
    )
}

@Composable
private fun IssueContent(
    uiState: IssueUiState,
    scaffoldState: ScaffoldState,
    onRefresh: () -> Unit,
    onOpenLink: (String) -> Unit,
    onClickFavorite: (Issue, Boolean) -> Unit
) {
    if (uiState.error && uiState.issues.isEmpty()) {
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
            if (uiState.issues.isEmpty()) {
                Box(modifier = Modifier.fillMaxSize()) // dummy ui for SwipeRefresh
            } else {
                IssueList(
                    uiState,
                    onOpenLink,
                    onClickFavorite
                )
            }
        }

        if (uiState.error) {
            val message = stringResource(id = CoreString.text_error)
            LaunchedEffect(scaffoldState.snackbarHostState) {
                scaffoldState.snackbarHostState.showSnackbar(message)
            }
        }
    }
}

@Composable
private fun IssueList(
    uiState: IssueUiState,
    onOpenLink: (String) -> Unit,
    onClickFavorite: (Issue, Boolean) -> Unit,
) {
    LazyColumn(contentPadding = PaddingValues(top = 8.dp, bottom = 8.dp)) {
        items(uiState.issues, key = { it.issue.url }) { issueItem ->
            com.star_zero.dagashi.core.ui.components.IssueCard(
                issue = issueItem.issue,
                isFavorite = issueItem.isFavorite,
                onOpenLink = onOpenLink,
                onClickFavorite = onClickFavorite
            )
        }
    }
}
