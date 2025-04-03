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
    data object Numerology : Screen(
        route = "numerology"
    )
    data object Wishes : Screen(
        route = "wishes"
    )
    data object ParigaraThalangal : Screen(
        route = "parigaraThalangal"
    )
    data object WebScreen : Screen(
        route = "web/{mode}/{uri}",
        navArguments = listOf(
            navArgument("mode") { type = NavType.IntType },
            navArgument("uri") { type = NavType.StringType },
        )
    ) {
        fun createRoute(mode: Int, uri: String) = "web/$mode/$uri"
    }
}