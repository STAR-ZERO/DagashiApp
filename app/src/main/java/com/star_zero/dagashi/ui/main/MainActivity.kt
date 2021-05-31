package com.star_zero.dagashi.ui.main

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navArgument
import androidx.navigation.compose.rememberNavController
import com.star_zero.dagashi.core.data.repository.DagashiRepository
import com.star_zero.dagashi.core.data.repository.SettingRepository
import com.star_zero.dagashi.ui.issue.IssueScreen
import com.star_zero.dagashi.ui.issue.IssueViewModel
import com.star_zero.dagashi.ui.milestone.MilestoneScreen
import com.star_zero.dagashi.ui.milestone.MilestoneViewModel
import com.star_zero.dagashi.ui.setting.SettingScreen
import com.star_zero.dagashi.ui.setting.SettingViewModel
import com.star_zero.dagashi.ui.theme.DagashiAppTheme
import com.star_zero.dagashi.ui.util.AppNavigator
import com.star_zero.dagashi.ui.util.LocalNavigator
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var dagashiRepository: DagashiRepository

    @Inject
    lateinit var settingRepository: SettingRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            DagashiAppTheme {
                val navController = rememberNavController()
                CompositionLocalProvider(LocalNavigator provides AppNavigator(navController)) {
                    NavHost(
                        navController = navController,
                        startDestination = "milestone",
                    ) {
                        composable("milestone") { backStackEntry ->
                            MilestoneNav(backStackEntry)
                        }
                        composable(
                            "issue/{path}/{title}",
                            arguments = listOf(
                                navArgument("path") { type = NavType.StringType },
                                navArgument("title") { type = NavType.StringType },
                            )
                        ) { backStackEntry ->
                            IssueNav(backStackEntry)
                        }
                        composable("setting") { backStackEntry ->
                            val viewModelFactory = SettingViewModel.Factory(
                                backStackEntry,
                                settingRepository
                            )
                            SettingScreen(viewModelFactory)
                        }
                    }
                }
            }
        }
    }

    @Composable
    private fun MilestoneNav(backStackEntry: NavBackStackEntry) {
        val viewModel: MilestoneViewModel = hiltViewModel(backStackEntry)
        val uiState by viewModel.uiState.collectAsState()

        LaunchedEffect(null) {
            viewModel.getMilestones(false)
        }

        MilestoneScreen(
            uiState = uiState,
            onRefresh = {
                viewModel.refresh()
            }
        )
    }

    @Composable
    private fun IssueNav(backStackEntry: NavBackStackEntry) {
        val viewModel: IssueViewModel = hiltViewModel(backStackEntry)
        val uiState by viewModel.uiState.collectAsState()
        val isOpenLinkInApp by viewModel.isOpenLinkInApp.collectAsState(initial = false)

        val path = backStackEntry.arguments!!.getString("path")!!
        val title = backStackEntry.arguments!!.getString("title")!!

        LaunchedEffect(null) {
            viewModel.getIssues(path)
        }

        IssueScreen(
            uiState = uiState,
            title = title,
            isOpenLinkInApp = isOpenLinkInApp,
            onRefresh = {
                viewModel.refresh(path)
            }
        )
    }
}
