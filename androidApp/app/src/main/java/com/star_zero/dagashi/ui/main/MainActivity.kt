package com.star_zero.dagashi.ui.main

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.padding
import androidx.compose.ui.Modifier
import com.google.accompanist.navigation.material.ExperimentalMaterialNavigationApi
import com.ramcosta.composedestinations.DestinationsNavHost
import com.ramcosta.composedestinations.navigation.dependency
import com.star_zero.dagashi.core.ui.theme.DagashiAppTheme
import com.star_zero.dagashi.features.issue.destinations.IssueScreenDestination
import com.star_zero.dagashi.features.milestone.destinations.MilestoneScreenDestination
import com.star_zero.dagashi.ui.AppNavigator
import com.star_zero.dagashi.ui.IssueTransitions
import com.star_zero.dagashi.ui.MilestoneTransitions
import com.star_zero.dagashi.ui.NavGraphs
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @OptIn(
        ExperimentalAnimationApi::class,
        ExperimentalMaterialNavigationApi::class
    )
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            DagashiAppTheme {
                val navHostEngine = rememberCustomAnimatedNavHostEngine()
                val navController = navHostEngine.rememberNavController()

                AppBottomBar(
                    navController = navController
                ) { innerPadding ->

                    MilestoneScreenDestination.style = MilestoneTransitions
                    IssueScreenDestination.style = IssueTransitions

                    DestinationsNavHost(
                        navGraph = NavGraphs.root,
                        navController = navController,
                        engine = navHostEngine,
                        dependenciesContainerBuilder = {
                            dependency(AppNavigator(destinationsNavigator))
                        },
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}
