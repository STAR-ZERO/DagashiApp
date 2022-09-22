package com.star_zero.dagashi.ui.milestoneIssue

import android.app.Activity
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.google.accompanist.adaptive.FoldAwareConfiguration
import com.google.accompanist.adaptive.HorizontalTwoPaneStrategy
import com.google.accompanist.adaptive.TwoPane
import com.google.accompanist.adaptive.calculateDisplayFeatures
import com.star_zero.dagashi.features.issue.IssueNavDestination
import com.star_zero.dagashi.features.issue.IssueRoute
import com.star_zero.dagashi.features.milestone.MilestoneRoute
import com.star_zero.dagashi.shared.model.Milestone

/**
 * 2 Pane screen
 */
@Composable
fun MilestoneIssueRoute() {
    val navController = rememberNavController()
    var selectedMilestone by rememberSaveable {
        mutableStateOf<Milestone?>(null)
    }

    MilestoneIssueScreen(
        navController = navController,
        selectedMilestone = selectedMilestone,
        navigateIssue = {
            selectedMilestone = it
            IssueNavDestination.navigate(
                navController = navController,
                milestone = it,
                isTwoPane = true
            )
        }
    )
}

@Composable
fun MilestoneIssueScreen(
    navController: NavHostController,
    selectedMilestone: Milestone?,
    navigateIssue: (Milestone) -> Unit
) {
    val activity = LocalContext.current as Activity
    val displayFeatures = calculateDisplayFeatures(activity)

    TwoPane(
        first = {
            MilestoneRoute(
                navigateIssue = navigateIssue,
                selectedMilestone = selectedMilestone
            )
        },
        second = {
            NavHost(
                navController = navController,
                startDestination = IssueNavDestination.route
            ) {
                composable(
                    route = IssueNavDestination.route,
                    arguments = IssueNavDestination.arguments
                ) {
                    if (it.arguments?.containsKey(IssueNavDestination.ARG_MILESTONE_ID) == true) {
                        IssueRoute(
                            navigateBack = {}
                        )
                    }
                }
            }
        },
        strategy = HorizontalTwoPaneStrategy(splitFraction = 0.5f),
        displayFeatures = displayFeatures,
        foldAwareConfiguration = FoldAwareConfiguration.VerticalFoldsOnly
    )
}
