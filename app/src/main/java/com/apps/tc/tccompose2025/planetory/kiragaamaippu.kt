package com.apps.tc.tccompose2025.planetory

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ScrollableTabRow
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRowDefaults.SecondaryIndicator
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.apps.tc.tccompose2025.App
import com.apps.tc.tccompose2025.R
import com.apps.tc.tccompose2025.Rasi
import com.apps.tc.tccompose2025.log
import com.apps.tc.tccompose2025.parigaram.ParigaramViewModel
import com.apps.tc.tccompose2025.poojacorner.PoojaViewModel
import com.apps.tc.tccompose2025.totalDays
import com.apps.tc.tccompose2025.ui.theme.ComposeTamilCalendar2025Theme
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import kotlin.collections.get
import kotlin.math.abs
import kotlin.math.cos
import kotlin.math.roundToInt
import kotlin.math.sin
import kotlin.ranges.contains

@Composable
fun KiragaAmaippu(app: App) {
    val planetoryModel: PlanetoryViewModel = viewModel()
    val planetoryState by planetoryModel.planetoryState.collectAsState()
    val scope = rememberCoroutineScope()

    val selectedTab = remember { mutableIntStateOf(0) }
    val radius = with(LocalDensity.current) { 70.dp.toPx() }
    val tabTitle = arrayOf("சனி பகவான்", "ராகு/கேது பகவான்", "குரு பகவான்")

    val animatables = remember {
        List(tabTitle.size) { Animatable(90f) }
    }

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            ScrollableTabRow(
                selectedTabIndex = selectedTab.intValue,
                containerColor = MaterialTheme.colorScheme.onPrimaryContainer,
                edgePadding = 6.dp,
                indicator = {
                    SecondaryIndicator(
                        modifier = Modifier.tabIndicatorOffset(it[selectedTab.intValue]),
                        color = MaterialTheme.colorScheme.onPrimary,
                    )
                },
            ) {
                tabTitle.forEachIndexed { index, title ->
                    Tab(
                        selected = selectedTab.intValue == index,
                        onClick = { selectedTab.intValue = index },
                        text = {
                            Text(
                                text = title,
                                style = MaterialTheme.typography.bodyLarge,
                                textAlign = TextAlign.Center
                            )
                        },
                        selectedContentColor = MaterialTheme.colorScheme.primary,
                        unselectedContentColor = MaterialTheme.colorScheme.primaryContainer
                    )
                }
            }

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = tabTitle[selectedTab.intValue],
                    color = MaterialTheme.colorScheme.onBackground,
                    style = MaterialTheme.typography.bodyLarge,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .padding(top = 64.dp)
                        .fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(28.dp))

                when (val state = planetoryState) {
                    is PlanetoryViewState.Success -> {
                        val planetData = state.planetData[selectedTab.intValue]
                        val animatedDegree = animatables[selectedTab.intValue].value

                        val radian = Math.toRadians(animatedDegree.toDouble())
                        val radian2 = Math.toRadians((animatedDegree + 180).toDouble())

                        Box(
                            modifier = Modifier.wrapContentSize(),
                            contentAlignment = Alignment.Center
                        ) {
                            Image(
                                painter = painterResource(id = R.drawable.planet_background),
                                contentDescription = null,
                                modifier = Modifier.size(460.dp)
                            )

                            when (selectedTab.intValue) {
                                0 -> {
                                    Image(
                                        painter = painterResource(id = R.drawable.planet_saturn),
                                        contentDescription = null,
                                        modifier = Modifier
                                            .size(48.dp)
                                            .graphicsLayer {
                                                translationX = cos(radian).toFloat() * radius
                                                translationY = sin(radian).toFloat() * radius
                                            }
                                    )
                                }

                                1 -> {
                                    Image(
                                        painter = painterResource(id = R.drawable.planet_raagu),
                                        contentDescription = null,
                                        modifier = Modifier
                                            .size(48.dp)
                                            .graphicsLayer {
                                                translationX = cos(radian).toFloat() * radius
                                                translationY = sin(radian).toFloat() * radius
                                            }
                                    )

                                    Image(
                                        painter = painterResource(id = R.drawable.planet_kaedhu),
                                        contentDescription = null,
                                        modifier = Modifier
                                            .size(48.dp)
                                            .graphicsLayer {
                                                translationX = cos(radian2).toFloat() * radius
                                                translationY = sin(radian2).toFloat() * radius
                                            }
                                    )
                                }

                                2 -> {
                                    Image(
                                        painter = painterResource(id = R.drawable.planet_guru),
                                        contentDescription = null,
                                        modifier = Modifier
                                            .size(48.dp)
                                            .graphicsLayer {
                                                translationX = cos(radian).toFloat() * radius
                                                translationY = sin(radian).toFloat() * radius
                                            }
                                    )
                                }
                            }
                        }
                    }

                    else -> {}
                }
            }
        }

        FloatingActionButton(
            onClick = {},
            containerColor = Color.Transparent,
            modifier = Modifier
                .padding(end = 8.dp, bottom = 8.dp)
                .align(Alignment.BottomEnd),
            elevation = FloatingActionButtonDefaults.elevation(0.dp)
        ) {
            Image(
                painter = painterResource(R.drawable.ic_share),
                contentDescription = " ",
                modifier = Modifier.size(48.dp)
            )
        }
    }

    // Animate to new degree only when tab changes or data loaded
    LaunchedEffect(selectedTab.intValue, planetoryState) {
        if (planetoryState is PlanetoryViewState.Success) {
            val planetData =
                (planetoryState as PlanetoryViewState.Success).planetData[selectedTab.intValue]
            val degree = planetoryModel.totalDegrees(
                Date().totalDays(planetData.startDate),
                planetData.totalDays
            )
            log(Date().totalDays(planetData.startDate))
            log(planetData.totalDays)
            log(degree)
            animatables[selectedTab.intValue].animateTo(
                targetValue = degree,
                animationSpec = tween(durationMillis = 2000, easing = LinearEasing)
            )
        }
    }

    // Load data once
    LaunchedEffect(Unit) {
        scope.launch {
            planetoryModel.fetchPlanetoryData(app)
        }
    }
}

@Composable
private fun PlanetaryData(index: Int, days: Int, degree: Float) {
    val years: Int = days / 365
    val months: Int = days % 365 / 30
    val days: Int = days % 365 % 30
    val dayWord = if (days > 1) " நாட்கள்" else " நாள்"
    when {
        (years > 0) -> "$years வருடம், $months மாதம், $days $dayWord"
        (months > 0) -> "$months மாதம், $days $dayWord"
        else -> "$days $dayWord"
    }
    val rasiPosition = ((degree + if (index != 1) 150 else 30) % 360 / 30).toInt() + 1
    if (index == 1) "மீனம்/கன்னி" else Rasi.getAllRasis()[rasiPosition - 1].displayName
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = when (index) {
                0 -> "தற்போதைய ராசி"
                1 -> "சஞ்சரித்த காலம்"
                else -> "பாகை"
            },
            color = MaterialTheme.colorScheme.onBackground,
            style = MaterialTheme.typography.labelMedium,
            textAlign = TextAlign.End,
            modifier = Modifier.width(180.dp)
        )
        Text(
            text = "",
            color = MaterialTheme.colorScheme.onBackground,
            style = MaterialTheme.typography.labelMedium
        )
    }
}

private fun displayPlanetDegree(degree: Float): String {
    val absDegree = abs(degree)
    val originalDegree: Float = if (absDegree in 0.0..90.0) 90 - absDegree else 450 - absDegree
    return originalDegree.toString()
}


@Preview(showBackground = true, showSystemUi = true)
@Composable
fun KiragaAmaipuPreview() {
    ComposeTamilCalendar2025Theme {
        KiragaAmaippu(App())
    }
}