package com.star_zero.dagashi.ui.milestone

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.compose.foundation.Icon
import androidx.compose.foundation.Text
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumnFor
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Settings
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.setContent
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.ui.tooling.preview.Preview
import com.star_zero.dagashi.R
import com.star_zero.dagashi.core.data.model.Milestone
import com.star_zero.dagashi.ui.ambients.NavHandlerAmbient
import com.star_zero.dagashi.ui.ambients.NavigationHandler
import com.star_zero.dagashi.ui.components.ErrorRetry
import com.star_zero.dagashi.ui.components.LoadingProgress
import com.star_zero.dagashi.ui.theme.DagashiAppTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MilestoneFragment : Fragment() {

    private val viewModel: MilestoneViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        return FrameLayout(requireContext()).apply {
            id = R.id.milestone_fragment

            layoutParams = ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT
            )

            setContent(Recomposer.current()) {
                Providers(NavHandlerAmbient provides NavigationHandler(findNavController())) {
                    DagashiAppTheme {
                        Surface(color = MaterialTheme.colors.background) {
                            Scaffold(
                                topBar = {
                                    AppBar(viewModel = viewModel)
                                }
                            ) {
                                MilestoneContent(
                                    viewModel = viewModel
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun AppBar(viewModel: MilestoneViewModel) {
    val coroutineScope = rememberCoroutineScope()
    val navHandler = NavHandlerAmbient.current

    TopAppBar(
        title = {
            Text(text = stringResource(id = R.string.milestone_title))
        },
        actions = {
            IconButton(onClick = {
                navHandler.navigate(R.id.action_milestone_to_setting)
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
private fun MilestoneContent(viewModel: MilestoneViewModel) {
    LaunchedTask {
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
        MilestoneList(viewModel.milestones)
    }
}

@Composable
private fun MilestoneList(milestones: List<Milestone>) {
    LazyColumnFor(
        items = milestones,
        contentPadding = PaddingValues(top = 8.dp, bottom = 8.dp)
    ) { milestone ->
        MilestoneCard(milestone)
    }
}

@Composable
private fun MilestoneCard(milestone: Milestone) {
    val navHandler = NavHandlerAmbient.current
    Card(
        modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
            .fillMaxWidth()
            .clickable(onClick = {
                val action = MilestoneFragmentDirections.actionMilestoneToIssue(milestone)
                navHandler.navigate(action)
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
                    text = milestone.description,
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
        MilestoneCard(milestone)
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
        MilestoneCard(milestone)
    }
}
// endregion
