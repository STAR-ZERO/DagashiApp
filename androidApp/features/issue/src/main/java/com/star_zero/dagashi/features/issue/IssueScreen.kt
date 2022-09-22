package com.star_zero.dagashi.features.issue

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.WindowInsetsSides
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.only
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import com.star_zero.dagashi.core.CoreString
import com.star_zero.dagashi.core.tools.DagashiPreview
import com.star_zero.dagashi.core.ui.components.ErrorRetry
import com.star_zero.dagashi.core.ui.components.IssueCard
import com.star_zero.dagashi.core.ui.theme.DagashiAppTheme
import com.star_zero.dagashi.core.usecase.OpenLinkUseCase
import com.star_zero.dagashi.shared.model.Author
import com.star_zero.dagashi.shared.model.Comment
import com.star_zero.dagashi.shared.model.Issue
import com.star_zero.dagashi.shared.model.Label

@Composable
fun IssueRoute(
    navigateBack: () -> Unit
) {
    val viewModel: IssueViewModel = hiltViewModel()

    val uriHandler = LocalUriHandler.current
    val context = LocalContext.current
    val openLinkUseCase = remember(uriHandler, context) {
        OpenLinkUseCase(context, uriHandler)
    }

    val uiState by viewModel.uiState.collectAsState(IssueUiState())

    IssueScreen(
        uiState = uiState,
        onRefresh = viewModel::getIssues,
        onOpenLink = {
            openLinkUseCase(it, uiState.isOpenLinkInApp)
        },
        onClickFavorite = viewModel::toggleFavorite,
        navigateBack = navigateBack
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun IssueScreen(
    uiState: IssueUiState,
    onRefresh: () -> Unit,
    onOpenLink: (String) -> Unit,
    onClickFavorite: (Issue, Boolean) -> Unit,
    navigateBack: () -> Unit
) {
    Surface {
        val snacbarHostState = remember { SnackbarHostState() }

        Scaffold(
            snackbarHost = { SnackbarHost(snacbarHostState) },
            topBar = {
                AppBar(
                    uiState = uiState,
                    navigateBack = {
                        navigateBack()
                    }
                )
            }
        ) { innerPadding ->
            IssueContent(
                uiState = uiState,
                snackbarHostState = snacbarHostState,
                onRefresh = onRefresh,
                onOpenLink = onOpenLink,
                onClickFavorite = onClickFavorite,
                modifier = Modifier.padding(innerPadding)
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun AppBar(
    uiState: IssueUiState,
    navigateBack: () -> Unit
) {
    if (uiState.isTwoPane) {
        // 2 Pane Layout
        CenterAlignedTopAppBar(
            title = {
                Text(text = uiState.milestoneTitle)
            },
            modifier = Modifier.windowInsetsPadding(
                WindowInsets.safeDrawing.only(WindowInsetsSides.Top)
            )
        )
    } else {
        TopAppBar(
            title = {
                Text(text = uiState.milestoneTitle)
            },
            navigationIcon = {
                IconButton(onClick = {
                    navigateBack()
                }) {
                    Icon(Icons.Filled.ArrowBack, "Back")
                }
            },
            modifier = Modifier.windowInsetsPadding(
                WindowInsets.safeDrawing.only(WindowInsetsSides.Top)
            )
        )
    }
}

@Composable
private fun IssueContent(
    uiState: IssueUiState,
    snackbarHostState: SnackbarHostState,
    onRefresh: () -> Unit,
    onOpenLink: (String) -> Unit,
    onClickFavorite: (Issue, Boolean) -> Unit,
    modifier: Modifier = Modifier
) {
    if (uiState.error && uiState.issues.isEmpty()) {
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
            LaunchedEffect(snackbarHostState) {
                snackbarHostState.showSnackbar(message)
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
    LazyColumn(
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
    ) {
        items(uiState.issues, key = { it.issue.url }) { issueItem ->
            IssueCard(
                issue = issueItem.issue,
                isFavorite = issueItem.isFavorite,
                onOpenLink = onOpenLink,
                onClickFavorite = onClickFavorite
            )
        }
    }
}

@DagashiPreview
@Composable
private fun PreviewIssueScreen() {
    val issues = (1..10).map {
        IssueItemUiState(
            issue = Issue(
                url = "url/$it",
                title = "Title $it",
                body = "Body Body\nBody Body",
                labels = listOf(
                    Label("label", 0xFF006b75)
                ),
                comments = listOf(
                    Comment(
                        body = "Comment",
                        author = Author(
                            name = "author",
                            url = "comment/url",
                            avatarUrl = ""
                        )
                    )
                )
            ),
            isFavorite = it % 2 == 0
        )
    }
    DagashiAppTheme {
        IssueScreen(
            uiState = IssueUiState(
                issues = issues,
            ),
            onRefresh = {},
            onOpenLink = {},
            onClickFavorite = { _, _ -> },
            navigateBack = {}
        )
    }
}

@DagashiPreview
@Composable
private fun PreviewIssueScreenError() {
    DagashiAppTheme {
        IssueScreen(
            uiState = IssueUiState(
                error = true
            ),
            onRefresh = {},
            onOpenLink = {},
            onClickFavorite = { _, _ -> },
            navigateBack = {}
        )
    }
}
