package com.apps.tc.tccompose2025.widgets

import android.annotation.SuppressLint
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.glance.ColorFilter
import androidx.glance.GlanceModifier
import androidx.glance.Image
import androidx.glance.ImageProvider
import androidx.glance.background
import androidx.glance.layout.Alignment
import androidx.glance.layout.Box
import androidx.glance.layout.Column
import androidx.glance.layout.Row
import androidx.glance.layout.fillMaxWidth
import androidx.glance.layout.height
import androidx.glance.layout.padding
import androidx.glance.layout.size
import androidx.glance.layout.width
import androidx.glance.text.FontWeight
import androidx.glance.text.Text
import androidx.glance.text.TextAlign
import androidx.glance.text.TextStyle
import androidx.glance.unit.ColorProvider
import com.apps.tc.tccompose2025.App

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
    date: Int,
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
            text = date.toString(),
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

@SuppressLint("RestrictedApi")
@Composable
fun NallaNeramView(image: Int, text: String) {
    Row(
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            provider = ImageProvider(image),
            contentDescription = "3D Calendar",
            modifier = GlanceModifier
                .padding(end = 4.dp)
                .size(16.dp)
        )
        Text(
            text = text,
            style = TextStyle(
                fontSize = 8.sp,
                fontWeight = FontWeight.Medium,
                color = ColorProvider(Color(0xFFB51E25)),
            )
        )
    }
}

@SuppressLint("RestrictedApi")
@Composable
fun FastingLayout(image: Int, text: String) {
    Row(
        modifier = GlanceModifier
            .padding(vertical = 2.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            provider = ImageProvider(image),
            contentDescription = "3D Calendar",
            modifier = GlanceModifier
                .padding(end = 8.dp)
                .size(24.dp),
            colorFilter = ColorFilter.tint(ColorProvider(Color(0xFFF4F4F4)))
        )
        Text(
            text = text,
            modifier = GlanceModifier
                .defaultWeight(),
            style = TextStyle(
                fontSize = 10.sp,
                fontWeight = FontWeight.Medium,
                color = ColorProvider(Color(0xFFF4F4F4)),
                textAlign = TextAlign.Start
            )
        )
    }
}
