package com.star_zero.dagashi.features.issue

import android.net.Uri
import androidx.browser.customtabs.CustomTabsIntent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material.Card
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.LocalContentColor
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.ScaffoldState
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.rememberImagePainter
import coil.transform.CircleCropTransformation
import com.google.accompanist.flowlayout.FlowRow
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.spec.DestinationStyle
import com.star_zero.dagashi.core.CoreString
import com.star_zero.dagashi.core.ui.components.ErrorRetry
import com.star_zero.dagashi.core.ui.components.formatLinkedText
import com.star_zero.dagashi.core.ui.theme.DagashiAppTheme
import com.star_zero.dagashi.shared.model.Author
import com.star_zero.dagashi.shared.model.Comment
import com.star_zero.dagashi.shared.model.Issue
import com.star_zero.dagashi.shared.model.Label
import com.star_zero.dagashi.shared.model.Milestone

@Destination(style = DestinationStyle.Runtime::class)
@Composable
fun IssueScreen(
    navigator: IssueNavigator,
    milestone: Milestone
) {
    val viewModel: IssueViewModel = hiltViewModel()

    LaunchedEffect(milestone) {
        viewModel.getIssues(milestone.path)
    }

    IssueContainer(
        uiState = viewModel.uiState,
        title = milestone.title,
        onRefresh = {
            viewModel.refresh(milestone.path)
        },
        onClickFavorite = { issue ->
            viewModel.addFavorite(issue)
        },
        navigateBack = {
            navigator.navigateBack()
        }
    )
}

@Composable
private fun IssueContainer(
    uiState: IssueUiState,
    title: String,
    onRefresh: () -> Unit,
    onClickFavorite: (Issue) -> Unit,
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
                onClickFavorite = onClickFavorite
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
    onClickFavorite: (Issue) -> Unit
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
                IssueList(uiState, onClickFavorite)
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
private fun IssueList(uiState: IssueUiState, onClickFavorite: (Issue) -> Unit) {
    LazyColumn(contentPadding = PaddingValues(top = 8.dp, bottom = 8.dp)) {
        items(uiState.issues, key = { it.url }) { issue ->
            IssueCard(issue, uiState.isOpenLinkInApp, onClickFavorite)
        }
    }
}

@Composable
private fun IssueCard(
    issue: Issue,
    isOpenLinkInApp: Boolean,
    onClickFavorite: (Issue) -> Unit
) {

    val uriHandler = LocalUriHandler.current
    val context = LocalContext.current

    val openLink: (String) -> Unit = {
        if (isOpenLinkInApp) {
            val customTabsIntent = CustomTabsIntent.Builder().build()
            customTabsIntent.launchUrl(context, Uri.parse(it))
        } else {
            uriHandler.openUri(it)
        }
    }

    Card(
        modifier = Modifier
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .fillMaxWidth(),
        elevation = 4.dp,
    ) {
        Column(
            modifier = Modifier.padding(8.dp)
        ) {

            Row(modifier = Modifier.fillMaxWidth()) {

                Text(
                    text = issue.title,
                    style = MaterialTheme.typography.subtitle1,
                    modifier = Modifier.weight(1f)
                )

                Spacer(modifier = Modifier.width(16.dp))

                IconButton(
                    onClick = { onClickFavorite(issue) },
                    modifier = Modifier
                        .size(32.dp)
                        .align(Alignment.Top)
                ) {
                    Icon(imageVector = Icons.Filled.Star, contentDescription = null)
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            if (issue.labels.isNotEmpty()) {
                IssueLabels(issue.labels)
                Spacer(modifier = Modifier.height(8.dp))
            }

            val linkColor = MaterialTheme.colors.primary

            val linkedText = remember(issue.body) { formatLinkedText(issue.body, linkColor) }

            ClickableText(
                text = linkedText,
                style = MaterialTheme.typography.caption.copy(color = LocalContentColor.current),
                onClick = { position ->
                    val annotation = linkedText.getStringAnnotations(
                        start = position,
                        end = position
                    ).firstOrNull()

                    annotation?.let {
                        openLink(it.item)
                    }
                }
            )

            if (issue.comments.isNotEmpty()) {
                Divider(
                    modifier = Modifier.padding(vertical = 8.dp),
                    color = MaterialTheme.colors.onSurface.copy(alpha = 0.2F),
                    thickness = 1.dp,
                )
            }

            issue.comments.forEach { comment ->
                Comment(
                    comment = comment,
                    modifier = Modifier.padding(start = 16.dp)
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            TextButton(
                modifier = Modifier.align(Alignment.End),
                onClick = {
                    openLink(issue.url)
                }
            ) {
                Text(
                    text = "GITHUB",
                    style = MaterialTheme.typography.caption,
                )
            }
        }
    }
}

@Composable
private fun IssueLabels(labels: List<Label>) {
    FlowRow(
        mainAxisSpacing = 4.dp,
        crossAxisSpacing = 4.dp,
    ) {
        labels.forEach { label ->
            Box(
                Modifier.background(
                    color = Color(label.color),
                    shape = RoundedCornerShape(percent = 50)
                )
            ) {
                Text(
                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                    text = label.name,
                    style = MaterialTheme.typography.overline,
                    color = Color.White
                )
            }
        }
    }
}

@Composable
private fun Comment(comment: Comment, modifier: Modifier = Modifier) {
    Column(modifier = modifier) {
        Row(modifier = Modifier.padding(vertical = 8.dp)) {

            Image(
                painter = rememberImagePainter(
                    data = comment.author.avatarUrl,
                    builder = {
                        transformations(CircleCropTransformation())
                    }
                ),
                contentDescription = comment.author.name,
                modifier = Modifier.size(24.dp)
            )

            Spacer(modifier = Modifier.size(8.dp))

            Text(
                text = comment.author.name,
                style = MaterialTheme.typography.caption,
                color = MaterialTheme.colors.primary,
                modifier = Modifier.align(Alignment.CenterVertically)
            )
        }

        Text(
            text = comment.body,
            style = MaterialTheme.typography.caption
        )
    }
}

// region Compose Preview
val previewIssue = Issue(
    "https://github.com/AndroidDagashi/AndroidDagashi/issues/1530",
    "11 Weeks of Android: UI and Compose",
    "https://android-developers.googleblog.com/2020/08/11-weeks-of-android-ui-and-compose.html\n\nSample Sample",
    listOf(
        Label(
            "Kotlin",
            "FFEC953C".toLong(radix = 16)
        )
    ),
    listOf(
        Comment(
            "Comment",
            Author(
                "STAR_ZERO",
                "url",
                "https://avatars0.githubusercontent.com/u/376376?v=4"
            )
        )
    )
)

@Preview("Issue card")
@Composable
private fun PreviewIssueCard() {
    DagashiAppTheme {
        IssueCard(previewIssue, true, onClickFavorite = {})
    }
}

@Preview("Issue card dark theme")
@Composable
private fun PreviewIssueCardDark() {
    DagashiAppTheme(darkTheme = true) {
        IssueCard(previewIssue, true, onClickFavorite = {})
    }
}
// endregion
