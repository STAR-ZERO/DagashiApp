package com.star_zero.dagashi.ui.main

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
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
                NavHost(
                    navController = navController,
                    startDestination = "milestone",
                ) {
                    composable("milestone") { backStackEntry ->
                        val viewModelFactory = MilestoneViewModel.Factory(
                            backStackEntry,
                            dagashiRepository
                        )
                        MilestoneScreen(navController, viewModelFactory)
                    }
                    composable(
                        "issue/{path}/{title}",
                        arguments = listOf(
                            navArgument("path") { type = NavType.StringType },
                            navArgument("title") { type = NavType.StringType },
                        )
                    ) { backStackEntry ->
                        val viewModelFactory = IssueViewModel.Factory(
                            backStackEntry,
                            dagashiRepository,
                            settingRepository
                        )
                        IssueScreen(
                            navController = navController,
                            viewModelFactory = viewModelFactory,
                            path = backStackEntry.arguments!!.getString("path")!!,
                            title = backStackEntry.arguments!!.getString("title")!!
                        )
                    }
                    composable("setting") { backStackEntry ->
                        val viewModelFactory = SettingViewModel.Factory(
                            backStackEntry,
                            settingRepository
                        )
                        SettingScreen(navController, viewModelFactory)
                    }
                }
            }
        }
    }
}
