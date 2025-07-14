package com.apps.tc.tccompose2025

import androidx.navigation.NamedNavArgument
import androidx.navigation.NavType
import androidx.navigation.navArgument
import com.apps.tc.tccompose2025.models.RewindData
import com.apps.tc.tccompose2025.models.RiddlesData
import kotlinx.serialization.json.Json
import java.net.URLEncoder

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

    data object KiragaAmaippu : Screen(
        route = "kiragaAmaippu"
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

    data object Rewind : Screen(
        route = "rewind",
    )

    data object YearBook : Screen(
        route = "yearbook/{data}/{year}",
        navArguments = listOf(
            navArgument("data") { type = NavType.StringType },
            navArgument("year") { type = NavType.IntType }
        )
    ) {
        fun createRoute(data: RewindData, year: Int): String {
            val json = Json.encodeToString(RewindData.serializer(), data) // Convert data to JSON
            val encodedJson = URLEncoder.encode(json, "utf-8") // URL encode it
            return "yearbook/$encodedJson/$year" // Ensure it's properly formatted
        }
    }

    data object Riddles : Screen(
        route = "riddles"
    )

    data object RiddlesPlay : Screen(
        route = "riddlesPlay/{data}",
        navArguments = listOf(
            navArgument("data") { type = NavType.StringType },
        )
    ) {
        fun createRoute(data: RiddlesData): String {
            val json = Json.encodeToString(RiddlesData.serializer(), data) // Convert data to JSON
            val encodedJson = URLEncoder.encode(json, "utf-8") // URL encode it
            return "riddlesPlay/$encodedJson" // Ensure it's properly formatted
        }
    }

    data object Notes : Screen(
        route = "notes"
    )

    data object BabyNames : Screen(
        route = "babyNames"
    )

    data object Stickers : Screen(
        route = "stickers"
    )

    data object Pranayamam : Screen(
        route = "pranayamam"
    )

    data object VirtualPooja : Screen(
        route = "virtualPooja"
    )

}