package com.apps.tc.tccompose2025

import androidx.navigation.NamedNavArgument
import androidx.navigation.NavType
import androidx.navigation.navArgument

sealed class Screen(
    val route: String,
    val navArguments: List<NamedNavArgument> = emptyList(),
) {
    data object SplashScreen : Screen(
        route = "splash"
    )
    data object Palankal : Screen(
        route = "palankal"
    )
}