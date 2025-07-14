package com.apps.tc.tccompose2025.widgets

import android.annotation.SuppressLint
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.glance.GlanceModifier
import androidx.glance.background
import androidx.glance.layout.Alignment
import androidx.glance.layout.Box
import androidx.glance.layout.Column
import androidx.glance.layout.fillMaxWidth
import androidx.glance.layout.height
import androidx.glance.layout.padding
import androidx.glance.layout.width
import androidx.glance.text.FontWeight
import androidx.glance.text.Text
import androidx.glance.text.TextAlign
import androidx.glance.text.TextStyle
import androidx.glance.unit.ColorProvider
import java.time.Month

@SuppressLint("RestrictedApi")
@Composable
fun HorizondalDivider() {
    Box(
        modifier = GlanceModifier
            .fillMaxWidth()
            .height(1.dp)
            .background(ColorProvider(Color.Gray))
    ) {}
}

@SuppressLint("RestrictedApi")
@Composable
fun VerticalDivider(height: Int) {
    Box(
        modifier = GlanceModifier
            .width(1.dp)
            .height(height.dp)
            .background(ColorProvider(Color.Gray))
    ) {}
}

@SuppressLint("RestrictedApi")
@Composable
fun DateView(
    date: String,
    month: String,
    day: String,
    isTamil: Boolean,
    modifier: GlanceModifier,
    fontSizeDate: Int,

) {
    Column(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = month,
            style = TextStyle(
                fontSize = 9.sp,
                fontWeight = FontWeight.Bold,
                color = ColorProvider(if (!isTamil) Color(0xFF252525) else Color(0xFFB51E25)),
                textAlign = TextAlign.Center
            )
        )
        Text(
            text = date,
            style = TextStyle(
                fontSize = fontSizeDate.sp,
                fontWeight = FontWeight.Bold,
                color = ColorProvider(if (isTamil) Color(0xFF252525) else Color(0xFFB51E25)),
                textAlign = TextAlign.Center
            )
        )
        Text(
            text = day,
            style = TextStyle(
                fontSize = 9.sp,
                fontWeight = FontWeight.Bold,
                color = ColorProvider(if (!isTamil) Color(0xFF252525) else Color(0xFFB51E25)),
                textAlign = TextAlign.Center
            )
        )
    }
}