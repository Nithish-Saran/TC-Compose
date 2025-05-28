package com.apps.tc.tccompose2025.planetory

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
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
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import com.apps.tc.tccompose2025.R
import com.apps.tc.tccompose2025.Rasi
import com.apps.tc.tccompose2025.totalDays
import com.apps.tc.tccompose2025.ui.theme.ComposeTamilCalendar2025Theme
import java.text.SimpleDateFormat
import java.util.Date
import kotlin.collections.get
import kotlin.math.abs
import kotlin.math.cos
import kotlin.math.roundToInt
import kotlin.math.sin
import kotlin.ranges.contains

@Composable
fun KiragaAmaippu() {
    val angle = remember { Animatable(270f) }
    val secondPlanetAngle = remember { Animatable(270f) }
    val bgSize = 260.dp
    val rotatingImageSize = 42.dp
    val selectedTab = remember { mutableIntStateOf(0) }

    val radius = remember(bgSize, rotatingImageSize) {
        (bgSize + 40.dp - rotatingImageSize) / 2
    }
    val radiusPx = with(LocalDensity.current) { radius.toPx() }

    var days = remember { mutableIntStateOf(0) }
    var infoText = remember { mutableStateOf("") }

    val tabTitle = arrayOf("சனி பகவான்", "ராகு/கேது பகவான்", "குரு பகவான்")

    Box(modifier = Modifier.fillMaxSize()) {
        Column(modifier = Modifier.fillMaxSize()) {
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

            Spacer(Modifier.padding(top = 16.dp))
            PlanetaryData(selectedTab.intValue, days.intValue, angle.value)
            PlanetaryData(selectedTab.intValue, days.intValue, angle.value)
            PlanetaryData(selectedTab.intValue, days.intValue, angle.value)

            Box(
                modifier = Modifier
                    .padding(vertical = 32.dp)
                    .fillMaxWidth(),
                contentAlignment = Alignment.Center
            ) {
                val x = radiusPx * cos(Math.toRadians(angle.value.toDouble())).toFloat()
                val y = radiusPx * sin(Math.toRadians(angle.value.toDouble())).toFloat()
                val x2 = radiusPx * cos(Math.toRadians(secondPlanetAngle.value.toDouble())).toFloat()
                val y2 = radiusPx * sin(Math.toRadians(secondPlanetAngle.value.toDouble())).toFloat()

                Image(
                    painter = painterResource(id = R.drawable.planet_background),
                    contentDescription = null,
                    modifier = Modifier.size(bgSize)
                )

                when (selectedTab.intValue) {
                    0 -> {
                        Image(
                            painter = painterResource(id = R.drawable.planet_saturn),
                            contentDescription = null,
                            modifier = Modifier
                                .offset { IntOffset(x.roundToInt(), y.roundToInt()) }
                                .size(rotatingImageSize)
                        )
                    }
                    1 -> {
                        Image(
                            painter = painterResource(id = R.drawable.planet_raagu),
                            contentDescription = null,
                            modifier = Modifier
                                .offset { IntOffset(x.roundToInt(), y.roundToInt()) }
                                .size(rotatingImageSize)
                        )
                        Image(
                            painter = painterResource(id = R.drawable.planet_kaedhu),
                            contentDescription = null,
                            modifier = Modifier
                                .offset { IntOffset(x2.roundToInt(), y2.roundToInt()) }
                                .size(rotatingImageSize)
                        )
                    }
                    2 -> {
                        Image(
                            painter = painterResource(id = R.drawable.planet_guru),
                            contentDescription = null,
                            modifier = Modifier
                                .offset { IntOffset(x.roundToInt(), y.roundToInt()) }
                                .size(rotatingImageSize)
                        )
                    }
                }
            }

            Text(
                text = infoText.value,
                color = MaterialTheme.colorScheme.onBackground,
                style = MaterialTheme.typography.labelMedium,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            )
        }

        FloatingActionButton(
            onClick = {},
            containerColor = Color.Transparent,
            modifier = Modifier
                .padding(end = 12.dp)
                .align(Alignment.BottomEnd),
            elevation = FloatingActionButtonDefaults.elevation(0.dp)
        ) {
            Image(
                painter = painterResource(R.drawable.ic_share),
                contentDescription = " ",
                modifier = Modifier.size(64.dp)
            )
        }
    }

    LaunchedEffect(selectedTab.intValue) {
        days.intValue = when (selectedTab.intValue) {
            0 -> Date().totalDays("17/5/2023")
            1 -> Date().totalDays("29/4/2024")
            else -> Date().totalDays("1/5/2024")
        }

        val degree = when (selectedTab.intValue) {
            0 -> {
                infoText.value = "12 ராசியை சுற்றி வர சனி பகவான் 30 வருடம் வரை எடுத்துக்கொள்வார். ஒரு ராசியில் 2 வருடம் 6 மாதம் சஞ்சரிப்பார்."
                135 + days.intValue * 0.0328f
            }
            1 -> {
                infoText.value = "12 ராசியை சுற்றி வர ராகு/கேது பகவான் 18 வருடம் வரை எடுத்துக்கொள்வார். ஒரு ராசியில் 1 வருடம் 6 மாதம் சஞ்சரிப்பார்."
                val raaghuDegree = days.intValue * -0.0547f - 70
                val kethuDegree = 130 + (days.intValue * -0.0547f) - 20
                secondPlanetAngle.snapTo(kethuDegree)
                raaghuDegree
            }
            else -> {
                val peyarchiDate = SimpleDateFormat("dd/MM/yyyy").parse("1/5/2024")
                infoText.value = "12 ராசியை சுற்றி வர குரு பகவான் 12 வருடம் வரை எடுத்துக்கொள்வார். ஒரு ராசியில் 1 வருடம் சஞ்சரிப்பார்."
                135 + days.intValue * 0.08219178f + if (Date().after(peyarchiDate)) 90 else 0
            }
        }

        angle.snapTo(degree)
        angle.animateTo(
            targetValue = angle.value + 360f,
            animationSpec = tween(durationMillis = 2000, easing = LinearEasing)
        )
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
            text = when(index) {
                0 -> "தற்போதைய ராசி"
                1-> "சஞ்சரித்த காலம்"
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

@Preview(showBackground = true, showSystemUi = true,)
@Composable
fun KiragaAmaipuPreview() {
    ComposeTamilCalendar2025Theme {
        KiragaAmaippu()
    }
}