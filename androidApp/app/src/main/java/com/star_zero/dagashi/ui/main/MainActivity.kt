package com.star_zero.dagashi.ui.main

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.animation.ExperimentalAnimationApi
import com.google.accompanist.navigation.material.ExperimentalMaterialNavigationApi
import com.ramcosta.composedestinations.DestinationsNavHost
import com.ramcosta.composedestinations.animations.rememberAnimatedNavHostEngine
import com.ramcosta.composedestinations.navigation.dependency
import com.star_zero.dagashi.core.ui.theme.DagashiAppTheme
import com.star_zero.dagashi.features.favorite.destinations.FavoriteScreenDestination
import com.star_zero.dagashi.features.issue.destinations.IssueScreenDestination
import com.star_zero.dagashi.features.milestone.destinations.MilestoneScreenDestination
import com.star_zero.dagashi.features.setting.destinations.SettingScreenDestination
import com.star_zero.dagashi.ui.AppNavigator
import com.star_zero.dagashi.ui.FavoriteTransitions
import com.star_zero.dagashi.ui.IssueTransitions
import com.star_zero.dagashi.ui.MilestoneTransitions
import com.star_zero.dagashi.ui.NavGraphs
import com.star_zero.dagashi.ui.SettingTransitions
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    @OptIn(
        ExperimentalAnimationApi::class,
        ExperimentalMaterialNavigationApi::class
    )
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            DagashiAppTheme {
                val navHostEngine = rememberAnimatedNavHostEngine()

                MilestoneScreenDestination.style = MilestoneTransitions
                IssueScreenDestination.style = IssueTransitions
                FavoriteScreenDestination.style = FavoriteTransitions
                SettingScreenDestination.style = SettingTransitions

                DestinationsNavHost(
                    navGraph = NavGraphs.root,
                    engine = navHostEngine,
                    dependenciesContainerBuilder = {
                        dependency(AppNavigator(destinationsNavigator))
                    }
                )
            }
        }
    }
}
