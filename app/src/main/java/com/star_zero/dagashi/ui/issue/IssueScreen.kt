package com.star_zero.dagashi.ui.issue

import android.net.Uri
import androidx.browser.customtabs.CustomTabsIntent
import androidx.compose.foundation.ClickableText
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumnFor
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.ContextAmbient
import androidx.compose.ui.platform.UriHandlerAmbient
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.viewModel
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import coil.transform.CircleCropTransformation
import com.star_zero.dagashi.shared.model.Author
import com.star_zero.dagashi.shared.model.Comment
import com.star_zero.dagashi.shared.model.Issue
import com.star_zero.dagashi.shared.model.Label
import com.star_zero.dagashi.ui.components.ErrorRetry
import com.star_zero.dagashi.ui.components.LoadingProgress
import com.star_zero.dagashi.ui.components.formatLinkedText
import com.star_zero.dagashi.ui.theme.DagashiAppTheme
import dev.chrisbanes.accompanist.coil.CoilImage
import kotlinx.coroutines.launch

@Composable
fun IssueScreen(
    navController: NavController,
    viewModelFactory: ViewModelProvider.Factory,
    path: String,
    title: String,
) {
    val viewModel: IssueViewModel = viewModel(factory = viewModelFactory)
    Surface(color = MaterialTheme.colors.background) {
        Scaffold(
            topBar = {
                AppBar(
                    viewModel = viewModel,
                    path = path,
                    title = title,
                    navigateBack = {
                        navController.popBackStack()
                    }
                )
            }
        ) {
            IssueContent(viewModel, path)
        }
    }
}

@Composable
private fun AppBar(
    viewModel: IssueViewModel,
    path: String,
    title: String,
    navigateBack: () -> Unit
) {
    val coroutineScope = rememberCoroutineScope()
    TopAppBar(
        title = {
            Text(text = title)
        },
        navigationIcon = {
            IconButton(onClick = {
                navigateBack()
            }) {
                Icon(Icons.Filled.ArrowBack)
            }
        },
        actions = {
            IconButton(onClick = {
                coroutineScope.launch {
                    viewModel.refresh(path)
                }
            }) {
                Icon(Icons.Filled.Refresh)
            }
        }
    )
}

@Composable
private fun IssueContent(viewModel: IssueViewModel, path: String) {
    LaunchedEffect(Unit) {
        viewModel.getIssues(path)
    }

    val isOpenLinkInApp by viewModel.isOpenLinkInApp.collectAsState(initial = false)

    val coroutineScope = rememberCoroutineScope()

    @Suppress("CascadeIf")
    if (viewModel.hasError) {
        ErrorRetry(
            onRetry = {
                coroutineScope.launch {
                    viewModel.refresh(path)
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
        IssueList(viewModel.issues, isOpenLinkInApp)
    }
}

@Composable
private fun IssueList(issues: List<Issue>, isOpenLinkInApp: Boolean) {
    LazyColumnFor(
        items = issues,
        contentPadding = PaddingValues(top = 8.dp, bottom = 8.dp)
    ) { issue ->
        IssueCard(issue, isOpenLinkInApp)
    }
}

@Composable
private fun IssueCard(issue: Issue, isOpenLinkInApp: Boolean) {

    val uriHandler = UriHandlerAmbient.current
    val context = ContextAmbient.current

    val openLink: (String) -> Unit = {
        if (isOpenLinkInApp) {
            val customTabsIntent = CustomTabsIntent.Builder().build()
            customTabsIntent.launchUrl(context, Uri.parse(it))
        } else {
            uriHandler.openUri(it)
        }
    }

    Card(
        modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp).fillMaxWidth()
    ) {
        Column(
            modifier = Modifier.padding(8.dp)
        ) {

            Text(
                text = issue.title,
                style = MaterialTheme.typography.subtitle1
            )

            Spacer(modifier = Modifier.preferredHeight(8.dp))

            if (issue.labels.isNotEmpty()) {
                IssueLabels(issue.labels)
                Spacer(modifier = Modifier.preferredHeight(8.dp))
            }

            val linkColor = MaterialTheme.colors.primary

            val linkedText = remember(issue.body) { formatLinkedText(issue.body, linkColor) }

            ClickableText(
                text = linkedText,
                style = MaterialTheme.typography.caption.copy(color = AmbientContentColor.current),
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

            Spacer(modifier = Modifier.preferredHeight(8.dp))

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

@OptIn(ExperimentalLayout::class)
@Composable
private fun IssueLabels(labels: List<Label>) {
    FlowRow(
        mainAxisSpacing = 4.dp,
        crossAxisSpacing = 4.dp
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

            CoilImage(
                data = comment.author.avatarUrl,
                requestBuilder = {
                    transformations(CircleCropTransformation())
                },
                modifier = Modifier.preferredSize(24.dp)
            )

            Spacer(modifier = Modifier.preferredSize(8.dp))

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
        IssueCard(previewIssue, true)
    }
}

@Preview("Issue card dark theme")
@Composable
private fun PreviewIssueCardDark() {
    DagashiAppTheme(darkTheme = true) {
        IssueCard(previewIssue, true)
    }
}
// endregion
