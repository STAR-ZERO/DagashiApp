package com.star_zero.dagashi.ui.issue

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.compose.foundation.ClickableText
import androidx.compose.foundation.Icon
import androidx.compose.foundation.Text
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.preferredHeight
import androidx.compose.foundation.lazy.LazyColumnFor
import androidx.compose.material.Card
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.TextButton
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Recomposer
import androidx.compose.runtime.launchInComposition
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.UriHandlerAmbient
import androidx.compose.ui.platform.setContent
import androidx.compose.ui.unit.dp
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.ui.tooling.preview.Preview
import com.star_zero.dagashi.R
import com.star_zero.dagashi.core.data.model.Issue
import com.star_zero.dagashi.core.data.model.Milestone
import com.star_zero.dagashi.ui.components.ErrorRetry
import com.star_zero.dagashi.ui.components.SwipeToRefreshIndicator
import com.star_zero.dagashi.ui.components.SwipeToRefreshLayout
import com.star_zero.dagashi.ui.components.formatLinkedText
import com.star_zero.dagashi.ui.theme.DagashiAppTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class IssueFragment : Fragment() {

    private val args: IssueFragmentArgs by navArgs()

    private val viewModel: IssueViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        return FrameLayout(requireContext()).apply {
            id = R.id.issue_fragment

            layoutParams = ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT
            )

            val milestone = args.milestone

            setContent(Recomposer.current()) {
                DagashiAppTheme {
                    Surface(color = MaterialTheme.colors.background) {
                        Scaffold(
                            topBar = {
                                AppBar(
                                    title = milestone.title,
                                    onBack = ::onBack
                                )
                            }
                        ) {
                            IssueContent(viewModel, milestone)
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
private fun AppBar(title: String, onBack: () -> Unit) {
    TopAppBar(
        title = {
            Text(text = title)
        },
        navigationIcon = {
            IconButton(onClick = onBack) {
                Icon(Icons.Filled.ArrowBack)
            }
        }
    )
}

@Composable
private fun IssueContent(viewModel: IssueViewModel, milestone: Milestone) {
    launchInComposition {
        viewModel.getIssues(milestone)
    }

    val coroutineScope = rememberCoroutineScope()

    if (viewModel.hasError) {
        ErrorRetry(
            onRetry = {
                coroutineScope.launch {
                    viewModel.refresh(milestone)
                }
            }
        )
    } else {
        SwipeToRefreshLayout(
            refreshingState = viewModel.loading,
            onRefresh = {
                coroutineScope.launch {
                    viewModel.refresh(milestone)
                }
            },
            refreshIndicator = {
                SwipeToRefreshIndicator()
            }
        ) {
            IssueList(viewModel.issues)
        }
    }
}

@Composable
private fun IssueList(issues: List<Issue>) {
    LazyColumnFor(
        items = issues,
        contentPadding = PaddingValues(top = 8.dp, bottom = 8.dp)
    ) { issue ->
        IssueCard(issue)
    }
}

@Composable
private fun IssueCard(issue: Issue) {
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

            val uriHandler = UriHandlerAmbient.current
            val linkColor = MaterialTheme.colors.primary

            val linkedText = remember(issue.body) { formatLinkedText(issue.body, linkColor) }

            ClickableText(
                text = linkedText,
                style = MaterialTheme.typography.caption,
                onClick = { position ->
                    val annotation = linkedText.getStringAnnotations(
                        start = position,
                        end = position
                    ).firstOrNull()

                    annotation?.let {
                        uriHandler.openUri(it.item)
                    }
                }
            )

            Spacer(modifier = Modifier.preferredHeight(8.dp))

            TextButton(
                modifier = Modifier.align(Alignment.End),
                onClick = {
                    uriHandler.openUri(issue.url)
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

// region Compose Preview
@Preview("Issue card")
@Composable
private fun PreviewIssueCard() {
    val issues = Issue(
        "https://github.com/AndroidDagashi/AndroidDagashi/issues/1530",
        "11 Weeks of Android: UI and Compose",
        "https://android-developers.googleblog.com/2020/08/11-weeks-of-android-ui-and-compose.html\n\nSample Sample"
    )
    DagashiAppTheme {
        IssueCard(issues)
    }
}

@Preview("Issue card dark theme")
@Composable
private fun PreviewIssueCardDark() {
    val issues = Issue(
        "https://github.com/AndroidDagashi/AndroidDagashi/issues/1530",
        "11 Weeks of Android: UI and Compose",
        "https://android-developers.googleblog.com/2020/08/11-weeks-of-android-ui-and-compose.html\n\nSample Sample"
    )
    DagashiAppTheme(darkTheme = true) {
        IssueCard(issues)
    }
}
// endregion
