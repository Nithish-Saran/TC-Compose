package com.apps.tc.tccompose2025

import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.glance.appwidget.updateAll
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.apps.tc.tccompose2025.babyNames.BabyNames
import com.apps.tc.tccompose2025.coffeeMeetup.CoffeeMeet
import com.apps.tc.tccompose2025.models.RewindData
import com.apps.tc.tccompose2025.models.RiddlesData
import com.apps.tc.tccompose2025.notes.Notes
import com.apps.tc.tccompose2025.numerology.Numerology
import com.apps.tc.tccompose2025.palankal.Palankal
import com.apps.tc.tccompose2025.parigaram.ParigaraThalangal
import com.apps.tc.tccompose2025.planetory.KiragaAmaippu
import com.apps.tc.tccompose2025.poojacorner.PoojaCorner
import com.apps.tc.tccompose2025.poojacorner.VirtualPooja
import com.apps.tc.tccompose2025.pranayamam.Pranayamam
import com.apps.tc.tccompose2025.rewind.Rewind
import com.apps.tc.tccompose2025.rewind.YearBookDetail
import com.apps.tc.tccompose2025.riddles.Riddles
import com.apps.tc.tccompose2025.riddles.RiddlesPlay
import com.apps.tc.tccompose2025.ui.theme.ComposeTamilCalendar2025Theme
import com.apps.tc.tccompose2025.ui.theme.colorGoldBg
import com.apps.tc.tccompose2025.view.WebScreen
import com.apps.tc.tccompose2025.whatsappStickers.Stickers
import com.apps.tc.tccompose2025.widgets.DailySheetWidget
import com.apps.tc.tccompose2025.widgets.FastingDayWidget
import com.apps.tc.tccompose2025.widgets.RasiWidget
import com.apps.tc.tccompose2025.widgets.loadTodayData
import com.apps.tc.tccompose2025.widgets.refreshGlanceWidgets
import com.apps.tc.tccompose2025.wishes.Wishes
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlinx.serialization.json.Json
import java.net.URLDecoder
import java.net.URLEncoder

class MainActivity : ComponentActivity() {
    private val app: App by lazy { application as App }


    @RequiresApi(Build.VERSION_CODES.P)
    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        this.setStatusBarColor(R.color.status_bar)
        setContent {
            ComposeTamilCalendar2025Theme {

                val navController = rememberNavController()
                LaunchedEffect(Unit) {
                    DailySheetWidget().updateAll(app)
                    RasiWidget().updateAll(app)
                    FastingDayWidget().updateAll(app)
                }
                //todo:handle screen navig for widget
                val module = intent.getStringExtra("open_module")
                LaunchedEffect(module) {
                    when (module) {
                        "daily_sheet" -> navController.navigate(Screen.KiragaAmaippu.route)
                        "rasi_dialog" -> navController.navigate(Screen.Rasipalan.route)
                    }
                }
                Scaffold(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(
                            color = colorGoldBg
                        )
                )
                { innerPadding ->
                    NavHost(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(innerPadding),
                        navController = navController,
                        startDestination = Screen.SplashScreen.route
                    ) {
                        composable(Screen.SplashScreen.route) {
                            SplashScreen {
                                navController.navigate(Screen.VirtualPooja.route) {
                                    popUpTo(Screen.SplashScreen.route) { inclusive = true }
                                }
                            }
                        }

                        composable(
                            route = Screen.Palankal.route,
                        ) {
                            Palankal(2) {
                                navController.navigate(Screen.Palankal.route)
                            }
                        }

                        composable(
                            route = Screen.Wishes.route,
                        ) {
                            Wishes {
                                navController.navigate(Screen.Wishes.route)
                            }
                        }

                        composable(
                            route = Screen.Rasipalan.route,
                        ) {
                            RasiSelectionDialog(app) {
                                app.saveSelectedRasi(it)
                                refreshGlanceWidgets(app, RasiWidget::class.java)
                            }
                        }

                        composable(
                            route = Screen.KiragaAmaippu.route,
                        ) {
                            KiragaAmaippu(app)
                        }

                        composable(
                            route = Screen.ParigaraThalangal.route,
                        ) {
                            ParigaraThalangal(
                                onReturn = {
                                    navController.navigate(
                                        Screen.WebScreen.createRoute(
                                            WebScreenMode.Assets.value,
                                            URLEncoder.encode(it, "utf-8")
                                        )
                                    )
                                },
                                onBackReq = {}
                            )
                        }

                        composable(
                            route = Screen.WebScreen.route,
                            arguments = Screen.WebScreen.navArguments,
                        ) { backStackEntry ->
                            val mode = (backStackEntry.arguments?.getInt("mode") ?: 0).webScreenMode
                            val uri = backStackEntry.arguments?.getString("uri") ?: ""
                            WebScreen(mode = mode, uri = uri)
                        }

                        composable(
                            route = Screen.Rewind.route
                        ) {
                            Rewind(2024) {
                                navController.navigate(
                                    Screen.YearBook.createRoute(it, 2024)
                                )
                            }
                        }

                        composable(
                            route = Screen.YearBook.route,
                            arguments = Screen.YearBook.navArguments
                        ) { backStackEntry ->
                            val json = backStackEntry.arguments?.getString("data") ?: ""
                            val decodedJson =
                                URLDecoder.decode(json, "utf-8") // Decode JSON from URL
                            val rewindData =
                                Json.decodeFromString<RewindData>(decodedJson) // Convert back to object

                            val year =
                                backStackEntry.arguments?.getInt("year")
                                    ?: 2024  // Fetch dynamic year

                            YearBookDetail(rewindData, year) // Pass decoded data and correct year
                        }

                        composable(
                            route = Screen.Riddles.route
                        ) {
                            Riddles(
                                onBackReq = {
                                    navController.navigate(Screen.Riddles.route)
                                },
                                onNextReq = {
                                    navController.navigate(
                                        Screen.RiddlesPlay.createRoute(it)
                                    )
                                }
                            )
                        }

                        composable(
                            route = Screen.RiddlesPlay.route,
                            arguments = Screen.RiddlesPlay.navArguments
                        ) { backStackEntry ->
                            val json = backStackEntry.arguments?.getString("data") ?: ""
                            val decodedJson =
                                URLDecoder.decode(json, "utf-8") // Decode JSON from URL
                            val riddle =
                                Json.decodeFromString<RiddlesData>(decodedJson) // Convert back to object

                            RiddlesPlay(riddle) {
                                navController.navigate(
                                    Screen.Riddles.route
                                )

                            }
                        }

                        composable(
                            route = Screen.Notes.route,
                        ) {
                            Notes(app)
                        }

                        composable(
                            route = Screen.BabyNames.route
                        ) {
                            BabyNames(app) { }
                        }

                        composable(
                            route = Screen.Stickers.route
                        ) {
                            Stickers(app) { }
                        }

                        composable(
                            route = Screen.Numerology.route,
                        ) {
                            Numerology(
                                app = app,
                                onReturn = {
                                    navController.navigate(
                                        Screen.WebScreen.createRoute(
                                            WebScreenMode.Assets.value,
                                            URLEncoder.encode(it, "utf-8")
                                        )
                                    )
                                },
                                onBackReq = {
                                    navController.navigate(Screen.Numerology.route)
                                }
                            )
                        }

                        composable(
                            route = Screen.Pranayamam.route
                        ) {
                            Pranayamam(
                                app = app,
                                onReturn = {
                                    navController.navigate(
                                        Screen.WebScreen.createRoute(
                                            WebScreenMode.Assets.value,
                                            URLEncoder.encode(it, "utf-8")
                                        )
                                    )
                                }
                            ) {
                                navController.navigate(Screen.Pranayamam.route)
                            }
                        }
                        composable(
                            route = Screen.VirtualPooja.route
                        ) {
                            PoojaCorner(app)
                        }
                        composable(
                            route = Screen.CoffeeMeetUp.route
                        ) {
                            CoffeeMeet(app)
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    ComposeTamilCalendar2025Theme {
        Greeting("Android")
    }
}