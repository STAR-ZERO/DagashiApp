package com.star_zero.dagashi.ui.util

import androidx.compose.runtime.staticCompositionLocalOf
import androidx.navigation.NavController

val LocalNavigator = staticCompositionLocalOf<Navigator> {
    error("CompositionLocal `Navigator` not present")
}

interface Navigator {
    fun navigateBack()
    fun navigateIssue(path: String, title: String)
    fun navigateSetting()
}

class AppNavigator(private val navController: NavController): Navigator {
    override fun navigateBack() {
        navController.popBackStack()
    }

    override fun navigateIssue(path: String, title: String) {
        navController.navigate("issue/$path/$title")
    }

    override fun navigateSetting() {
        navController.navigate("setting")
    }
}
