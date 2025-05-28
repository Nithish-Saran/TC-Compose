package com.apps.tc.tccompose2025

import androidx.compose.animation.core.Animatable
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
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.TabRowDefaults.SecondaryIndicator
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import com.apps.tc.tccompose2025.ui.theme.ComposeTamilCalendar2025Theme
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.concurrent.TimeUnit
import kotlin.math.cos
import kotlin.math.roundToInt
import kotlin.math.sin

@Composable
fun Planetary() {
    val selectedTab = remember { mutableIntStateOf(0) }
    val degrees = remember { Animatable(0f) }

    val tabTitle = arrayOf(
        "சனி பகவான்",
        "ராகு/கேது பகவான்",
        "குரு பகவான்")

    Column(
        modifier = Modifier
            .fillMaxSize()
            .systemBarsPadding()
    ) {
        TabRow(
            selectedTabIndex = selectedTab.intValue,
            containerColor = MaterialTheme.colorScheme.onPrimaryContainer,
            indicator = {
                SecondaryIndicator(
                    modifier = Modifier
                        .tabIndicatorOffset(it[selectedTab.intValue])
                        .padding(horizontal = 8.dp),
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
                .fillMaxWidth()
                .padding(top = 32.dp),
        ) {
            Image(
                painter = painterResource(R.drawable.planet_background),
                contentDescription = "",
                modifier = Modifier
                    .fillMaxWidth()
                    .size(275.dp)
            )

            LaunchedEffect(selectedTab.intValue) {
                val targetDegree = when (selectedTab.intValue) {
                    0 -> 160f + Date().totalDays("29/3/2025") * 0.0328f
                    1 -> (Date().totalDays("29/4/2024") * 0.0547f) - 80
                    2 -> 135f + Date().totalDays("1/5/2024") * 0.08219178f
                    else -> 0f
                }
                degrees.animateTo(targetDegree, animationSpec = tween(durationMillis = 2000))
            }

            val radius =  with(LocalDensity.current) { 120.dp.toPx() }
            val angleRad = Math.toRadians(degrees.value.toDouble())
            val xOffset = (radius * cos(angleRad)).toFloat()
            val yOffset = (radius * sin(angleRad)).toFloat()

            when(selectedTab.intValue) {
                0 -> {
                    Image(
                        painter = painterResource(R.drawable.planet_saturn),
                        contentDescription = "",
                        modifier = Modifier
                            .wrapContentSize()
                            .size(40.dp)
                            .align(Alignment.TopCenter)
                            .offset { IntOffset(xOffset.roundToInt(), yOffset.roundToInt()) }
                    )
                }
                1 -> {
                    Image(
                        painter = painterResource(R.drawable.planet_kaedhu),
                        contentDescription = "",
                        modifier = Modifier
                            .wrapContentSize()
                            .size(40.dp)
                            .align(Alignment.TopCenter)
                            .offset { IntOffset(xOffset.roundToInt(), yOffset.roundToInt()) }
                    )
                    Image(
                        painter = painterResource(R.drawable.planet_raagu),
                        contentDescription = "",
                        modifier = Modifier
                            .wrapContentSize()
                            .size(40.dp)
                            .align(Alignment.TopCenter)
                            .offset { IntOffset(xOffset.roundToInt(), yOffset.roundToInt()) }
                    )
                }
                2 -> {
                    Image(
                        painter = painterResource(R.drawable.planet_guru),
                        contentDescription = "",
                        modifier = Modifier
                            .wrapContentSize()
                            .size(40.dp)
                            .align(Alignment.TopCenter)
                            .offset { IntOffset(xOffset.roundToInt(), yOffset.roundToInt()) }
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

        Spacer(modifier = Modifier.weight(1f))
        FloatingActionButton(
            onClick = {},
            containerColor = Color.Transparent,
            modifier = Modifier
                .align(Alignment.End),
            elevation = FloatingActionButtonDefaults.elevation(0.dp)
        ) {
            Image(
                painter = painterResource(R.drawable.ic_share),
                contentDescription = " ",
                modifier = Modifier
                    .size(50.dp)
                    .padding(end = 8.dp, bottom = 8.dp)
            )
        }
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


/*@Composable
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
}*/

fun Date.totalDays(dateString: String): Int {
    val formatter = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
    val startDate = formatter.parse(dateString)
    val diff = this.time - (startDate?.time ?: 0L)
    return TimeUnit.MILLISECONDS.toDays(diff).toInt()
}

fun calculateDegree(
    daysElapsed: Int,
    orbitalPeriodInDays: Float,
    baseOffset: Float = 0f,
    isRetrograde: Boolean = false
): Float {
    val rotation = (daysElapsed / orbitalPeriodInDays) * 360f
    return if (isRetrograde) baseOffset - rotation else baseOffset + rotation
}

//fun Date.totalDays(dateString: String): Int {
//    dateFromString(dateString, "dd/MM/yyyy")?.let {
//        return this.daysBetween(it)
//    }
//    return 1
//}

fun dateFromString(value: String, format: String): Date? = SimpleDateFormat(format).parse(value)

fun Date.daysBetween(date: Date): Int = TimeUnit.DAYS.convert(
    this.time - date.time,
    TimeUnit.MILLISECONDS
).toInt()

@Preview(showBackground = true, showSystemUi = true,)
@Composable
fun PlanetaryPreview() {
    ComposeTamilCalendar2025Theme {
        Planetary()
    }
}