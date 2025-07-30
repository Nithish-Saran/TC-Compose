package com.apps.tc.tccompose2025.widgets.view

import android.annotation.SuppressLint
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.glance.GlanceModifier
import androidx.glance.Image
import androidx.glance.ImageProvider
import androidx.glance.appwidget.cornerRadius
import androidx.glance.background
import androidx.glance.layout.Alignment
import androidx.glance.layout.Box
import androidx.glance.layout.Column
import androidx.glance.layout.Row
import androidx.glance.layout.Spacer
import androidx.glance.layout.fillMaxSize
import androidx.glance.layout.fillMaxWidth
import androidx.glance.layout.height
import androidx.glance.layout.padding
import androidx.glance.layout.size
import androidx.glance.layout.wrapContentSize
import androidx.glance.text.FontWeight
import androidx.glance.text.Text
import androidx.glance.text.TextAlign
import androidx.glance.text.TextStyle
import androidx.glance.unit.ColorProvider
import com.apps.tc.tccompose2025.App
import com.apps.tc.tccompose2025.Rasi
import com.apps.tc.tccompose2025.dayTa
import com.apps.tc.tccompose2025.models.WidgetData
import com.apps.tc.tccompose2025.widgets.DateView
import com.apps.tc.tccompose2025.widgets.VerticalDivider
import java.util.Date

@SuppressLint("RestrictedApi")
@Composable
fun RasiView(context: App, data: WidgetData) {
    val rasiIndex = if (context.getSelectedRasi(context) != -1) context.getSelectedRasi(context) else 0
    Box(
        modifier = GlanceModifier
            .wrapContentSize()
            .background(ColorProvider(Color(0xFFFFF8DD)))
            .padding(12.dp)
    ) {
        Column(
            verticalAlignment = Alignment.CenterVertically,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(
                modifier = GlanceModifier
                    .background(ColorProvider(Color(0xFFE9DCBF)))
                    .cornerRadius(12.dp)
                    .padding(8.dp)
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                DateView(
                    day = Date().dayTa,
                    date = data.date,
                    month = data.monthEn,
                    isTamil = false,
                    modifier = GlanceModifier
                        .defaultWeight(),
                    fontSizeDate = 16
                )
                VerticalDivider(58)

                DateView(
                    day = data.taYear,
                    date = data.taDate,
                    month = data.monthTa,
                    isTamil = true,
                    modifier = GlanceModifier
                        .defaultWeight(),
                    fontSizeDate = 16
                )
            }
            Spacer(modifier = GlanceModifier.height(8.dp))
            Column(
                modifier = GlanceModifier
                    .fillMaxWidth()
                    .cornerRadius(12.dp)
                    .padding(vertical = 12.dp)
                    .background(ColorProvider(Color(0xFFB51E25))),
                verticalAlignment = Alignment.CenterVertically,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Image(
                    provider = ImageProvider(Rasi.getAllRasis()[rasiIndex].displayIconLite),
                    contentDescription = "3D Calendar",
                    modifier = GlanceModifier
                        .padding(bottom = 8.dp)
                        .size(64.dp),
                )
                Text(
                    text = data.rasi[rasiIndex],
                    modifier = GlanceModifier.fillMaxWidth(),
                    style = TextStyle(
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        color = ColorProvider(Color(0xFFFFF8DD)),
                        textAlign = TextAlign.Center
                    )
                )
            }
        }
    }
}