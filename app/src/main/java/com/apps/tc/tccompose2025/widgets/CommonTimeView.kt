package com.apps.tc.tccompose2025.widgets

import android.annotation.SuppressLint
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.glance.ColorFilter
import androidx.glance.GlanceModifier
import androidx.glance.Image
import androidx.glance.ImageProvider
import androidx.glance.layout.Alignment
import androidx.glance.layout.Column
import androidx.glance.layout.Row
import androidx.glance.layout.padding
import androidx.glance.layout.size
import androidx.glance.text.FontWeight
import androidx.glance.text.Text
import androidx.glance.text.TextStyle
import androidx.glance.unit.ColorProvider

@SuppressLint("RestrictedApi")
@Composable
fun TimingView(
    columnModifier: GlanceModifier = GlanceModifier,
    columnVerticalAlignment: Alignment.Vertical,
    columnHorizontalAlignment: Alignment.Horizontal,
    heading: String,
    morningImage: Int,
    nightImage: Int,
    morningText: String,
    nightText: String,
    imageSize: Int,
    fontSize: Int,
    fontWeight: FontWeight,
    fontColor: Color,
    imageColor: Color,
    timeViewModifier: GlanceModifier,
    timeViewVerticalAlignment: Alignment.Vertical,
    timeViewHorizontalAlignment: Alignment.Horizontal
) {
    Column(
        modifier = columnModifier,
        horizontalAlignment = columnHorizontalAlignment,
        verticalAlignment =columnVerticalAlignment
    ) {
        Text(
            text = heading,
            modifier = GlanceModifier
                .padding(bottom = 4.dp),
            style = TextStyle(
                fontSize = 8.sp,
                fontWeight = FontWeight.Medium,
                color = ColorProvider(Color(0xFF252525)),
            )
        )
        GoodTimingView(
            image = morningImage,
            text = morningText,
            modifier = timeViewModifier,
            verticalAlignment = timeViewVerticalAlignment,
            horizontalAlignment = timeViewHorizontalAlignment,
            imageSize = imageSize,
            fontSize = fontSize,
            fontWeight = fontWeight,
            fontColor = fontColor,
            imageColor = imageColor
        )
        GoodTimingView(
            image = nightImage,
            text = nightText,
            modifier = timeViewModifier,
            verticalAlignment = timeViewVerticalAlignment,
            horizontalAlignment = timeViewHorizontalAlignment,
            imageSize = imageSize,
            fontSize = fontSize,
            fontWeight = fontWeight,
            fontColor = fontColor,
            imageColor = imageColor
        )

    }
}

@SuppressLint("RestrictedApi")
@Composable
fun GoodTimingView(
    image: Int,
    text: String,
    modifier: GlanceModifier,
    imageSize: Int,
    fontSize: Int,
    fontWeight: FontWeight,
    fontColor: Color,
    imageColor: Color,
    verticalAlignment: Alignment.Vertical,
    horizontalAlignment: Alignment.Horizontal

) {
    Row(
        modifier,
        verticalAlignment = verticalAlignment,
        horizontalAlignment = horizontalAlignment
    ) {
        Image(
            provider = ImageProvider(image),
            contentDescription = "3D Calendar",
            modifier = GlanceModifier
                .padding(end = 8.dp)
                .size(imageSize.dp),
            colorFilter = ColorFilter.tint(ColorProvider(imageColor))
        )
        Text(
            text = text,
            style = TextStyle(
                fontSize = fontSize.sp,
                fontWeight = fontWeight,
                color = ColorProvider(fontColor),
            )
        )
    }
}

@SuppressLint("RestrictedApi")
@Composable
fun OtherTimings(
    heading: String,
    time: String,
) {
    Column(
        modifier = GlanceModifier
            .padding(vertical = 6.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = heading,
            modifier = GlanceModifier
                .padding(bottom = 4.dp),
            style = TextStyle(
                fontSize = 8.sp,
                fontWeight = FontWeight.Medium,
                color = ColorProvider(Color(0xFF252525)),
            )
        )
        Text(
            text = time,
            style = TextStyle(
                fontSize = 10.sp,
                fontWeight = FontWeight.Medium,
                color = ColorProvider(Color(0xFFB51E25)),
            )
        )
    }

}
