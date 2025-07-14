package com.apps.tc.tccompose2025.widgets

import android.annotation.SuppressLint
import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.glance.GlanceId
import androidx.glance.GlanceModifier
import androidx.glance.appwidget.GlanceAppWidget
import androidx.glance.appwidget.cornerRadius
import androidx.glance.appwidget.provideContent
import androidx.glance.background
import androidx.glance.layout.Alignment
import androidx.glance.layout.Box
import androidx.glance.layout.Column
import androidx.glance.layout.Row
import androidx.glance.layout.fillMaxSize
import androidx.glance.layout.fillMaxWidth
import androidx.glance.layout.height
import androidx.glance.layout.padding
import androidx.glance.layout.width
import androidx.glance.text.FontWeight
import androidx.glance.text.Text
import androidx.glance.text.TextAlign
import androidx.glance.text.TextStyle
import androidx.glance.unit.ColorProvider
import com.apps.tc.tccompose2025.R

class GoodTimeWidget: GlanceAppWidget() {

    @SuppressLint("RestrictedApi")
    @Composable
    fun GoodTimeView() {
        Box(
            modifier = GlanceModifier
                .fillMaxSize()
                .background(ColorProvider(Color(0xFFFFF8DD).copy(alpha = 0.93f)))
                .padding(8.dp)
        ) {
            Column(
                verticalAlignment = Alignment.CenterVertically,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "செப்டம்பர் 29",
                    modifier = GlanceModifier
                        .padding(top = 4.dp),
                    style = TextStyle(
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Medium,
                        color = ColorProvider(Color(0xFFB51E25)),
                        textAlign = TextAlign.Center
                    )
                )
                Text(
                    text = "செவ்வாய்\nகார்த்திகை 21",
                    modifier = GlanceModifier
                        .padding(bottom = 6.dp),
                    style = TextStyle(
                        fontSize = 8.sp,
                        fontWeight = FontWeight.Medium,
                        color = ColorProvider(Color(0xFF252525)),
                        textAlign = TextAlign.Center
                    )
                )

                HorizondalDivider()

                TimingView(
                    columnModifier = GlanceModifier
                        .padding(vertical = 4.dp)
                        .fillMaxWidth(),
                    columnVerticalAlignment = Alignment.CenterVertically,
                    columnHorizontalAlignment = Alignment.CenterHorizontally,
                    heading = "நல்ல நேரம்",
                    morningImage = R.drawable.ic_morning,
                    nightImage = R.drawable.ic_night,
                    morningText = "6.00 - 7.00",
                    nightText = "6.00 - 7.00",
                    timeViewModifier = GlanceModifier.fillMaxWidth(),
                    timeViewVerticalAlignment = Alignment.CenterVertically,
                    timeViewHorizontalAlignment = Alignment.CenterHorizontally,
                    imageSize = 20,
                    fontSize = 10,
                    fontWeight = FontWeight.Medium,
                    fontColor = Color(0xFFB51E25),
                    imageColor = Color(0xFFB51E25)
                )

                HorizondalDivider()

                OtherTimings(
                    heading = "ராகு",
                    time = "12.00 PM - 12.30 PM"
                )

                HorizondalDivider()

                OtherTimings(
                    heading = "எமகண்டம்",
                    time = "12.00 PM - 12.30 PM"
                )
                HorizondalDivider()

                OtherTimings(
                    heading = "சூரிய உதயம்",
                    time = "5.50"
                )
            }
        }
    }
    override suspend fun provideGlance(
        context: Context,
        id: GlanceId
    ) {
        provideContent {
            GoodTimeView()
        }
    }
}