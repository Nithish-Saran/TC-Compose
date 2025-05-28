package com.apps.tc.tccompose2025.planetory

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBarsPadding
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
import com.apps.tc.tccompose2025.totalDays
import com.apps.tc.tccompose2025.ui.theme.ComposeTamilCalendar2025Theme
import java.util.Date
import kotlin.math.cos
import kotlin.math.roundToInt
import kotlin.math.sin

@Composable
fun KiragaAmaippu() {
    val angle = remember { Animatable(270f) } // Start at top
    val bgSize = 260.dp
    val rotatingImageSize = 42.dp
    val selectedTab = remember { mutableIntStateOf(0) }

    val radius = remember(bgSize, rotatingImageSize) {
        (bgSize + 40.dp - rotatingImageSize) / 2
    }
    val radiusPx = with(LocalDensity.current) { radius.toPx() }

    val tabTitle = arrayOf(
        "சனி பகவான்",
        "ராகு/கேது பகவான்",
        "குரு பகவான்")

    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
        ) {
            ScrollableTabRow(
                selectedTabIndex = selectedTab.intValue,
                containerColor = MaterialTheme.colorScheme.onPrimaryContainer,
                edgePadding = 6.dp,
                indicator = {
                    SecondaryIndicator(
                        modifier = Modifier
                            .tabIndicatorOffset(it[selectedTab.intValue]),
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
            PlanetaryData("தற்போதைய ராசி", "2 வருடம், 6 மாதம், 10 நாள்")
            PlanetaryData("சஞ்சரித்த காலம்", "2 வருடம், 6 மாதம், 10 நாள்")
            PlanetaryData("பாகை", "2 வருடம், 6 மாதம், 10 நாள்")

            Box(
                modifier = Modifier
                    .padding(vertical = 32.dp)
                    .fillMaxWidth()
                    .background(Color.Transparent),
                contentAlignment = Alignment.Center
            ) {
                val x = radiusPx * cos(Math.toRadians(angle.value.toDouble())).toFloat()
                val y = radiusPx * sin(Math.toRadians(angle.value.toDouble())).toFloat()

                // Background image
                Image(
                    painter = painterResource(id = R.drawable.planet_background),
                    contentDescription = null,
                    modifier = Modifier.size(bgSize)
                )

                when(selectedTab.intValue) {
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
                text = when(selectedTab.intValue) {
                    0 -> "12 ராசியை சுற்றி வர சனி பகவான் 30 வருடம் வரை எடுத்துக்கொள்வார். ஒரு ராசியில் 2 வருடம் 6 மாதம் சஞ்சரிப்பார்."
                    1 -> "12 ராசியை சுற்றி வர ராகு/கேது பகவான் 18 வருடம் வரை எடுத்துக்கொள்வார். ஒரு ராசியில் 1 வருடம் 6 மாதம் சஞ்சரிப்பார்."
                    else -> "12 ராசியை சுற்றி வர குரு பகவான் 12 வருடம் வரை எடுத்துக்கொள்வார். ஒரு ராசியில் 1 வருடம் சஞ்சரிப்பார்."
                },
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
                modifier = Modifier
                    .size(64.dp)
            )
        }
    }

    LaunchedEffect(Unit) {
        angle.snapTo(
            when (selectedTab.intValue) {
                0 -> 160f + Date().totalDays("29/3/2025") * 0.0328f
                1 -> (Date().totalDays("29/4/2024") * 0.0547f) - 80
                2 -> 135f + Date().totalDays("1/5/2024") * 0.08219178f
                else -> 0f
            }
        )
        angle.animateTo(
            targetValue = angle.value + 360f, // 270° → 630° = full clockwise circle
            animationSpec = tween(durationMillis = 2000, easing = LinearEasing)
        )
    }
}

@Composable
private fun PlanetaryData(title: String, content: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = "$title : ",
            color = MaterialTheme.colorScheme.onBackground,
            style = MaterialTheme.typography.labelMedium,
            textAlign = TextAlign.End,
            modifier = Modifier.width(180.dp)
        )
        Text(
            text = content,
            color = MaterialTheme.colorScheme.onBackground,
            style = MaterialTheme.typography.labelMedium
        )
    }
}

@Preview(showBackground = true, showSystemUi = true,)
@Composable
fun KiragaAmaipuPreview() {
    ComposeTamilCalendar2025Theme {
        KiragaAmaippu()
    }
}