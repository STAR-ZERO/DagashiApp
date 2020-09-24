package com.star_zero.dagashi.ui.ambients

import androidx.compose.runtime.staticAmbientOf
import androidx.navigation.NavController
import androidx.navigation.NavDirections

class NavigationHandler(private val navController: NavController) {
    fun popBackStack() {
        navController.popBackStack()
    }

    fun navigate(id: Int) {
        navController.navigate(id)
    }

    fun navigate(directions: NavDirections) {
        navController.navigate(directions)
    }
}

val NavHandlerAmbient = staticAmbientOf<NavigationHandler> {
    error("Ambient used without Provider")
}
