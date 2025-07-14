package com.apps.tc.tccompose2025.widgets

import android.annotation.SuppressLint
import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.glance.ColorFilter
import androidx.glance.GlanceId
import androidx.glance.GlanceModifier
import androidx.glance.Image
import androidx.glance.ImageProvider
import androidx.glance.appwidget.GlanceAppWidget
import androidx.glance.appwidget.cornerRadius
import androidx.glance.appwidget.provideContent
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
import androidx.glance.layout.width
import androidx.glance.text.FontWeight
import androidx.glance.text.Text
import androidx.glance.text.TextAlign
import androidx.glance.text.TextStyle
import androidx.glance.unit.ColorProvider
import com.apps.tc.tccompose2025.R

class FastingDayWidget : GlanceAppWidget() {
    @SuppressLint("RestrictedApi")
    @Composable
    fun FastingDayView() {
        Box(
            modifier = GlanceModifier
                .fillMaxSize()
                .background(ColorProvider(Color(0xFFFFF8DD).copy(alpha = 0.93f)))
                .padding(12.dp)
        ) {
            Column(
                verticalAlignment = Alignment.CenterVertically,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Row(
                    modifier = GlanceModifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp)
                        .background(ColorProvider(Color(0xFFE8E8E8).copy(alpha = 0.93f)))
                        .cornerRadius(12.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    DateView(
                        date = "29",
                        day = "செவ்வாய்",
                        month = "செப்டம்பர்",
                        isTamil = false
                    )
                    VerticalDivider(58)
                    DateView(
                        date = "2",
                        day = "ருத்ரோத்காரி",
                        month = "கார்த்திகை",
                        isTamil = true
                    )
                }
                Spacer(modifier = GlanceModifier.height(8.dp))
                Column(
                    modifier = GlanceModifier
                        .fillMaxWidth()
                        .cornerRadius(12.dp)
                        .padding(12.dp)
                        .background(ColorProvider(Color(0xFFB51E25))),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalAlignment = Alignment.Start
                ) {
                    Text(
                        text = "விரத நாட்கள்",
                        modifier = GlanceModifier
                            .padding(bottom = 6.dp)
                            .fillMaxWidth(),
                        style = TextStyle(
                            fontSize = 10.sp,
                            fontWeight = FontWeight.Normal,
                            color = ColorProvider(Color(0xFFF4F4F4)),
                            textAlign = TextAlign.Center
                        )
                    )

                    FastingLayout(R.drawable.sdays_ammavasai, "அமாவாசை - 24 வியாழன்")
                    FastingLayout(R.drawable.sdays_pournami, "பௌர்ணமி - 24 வியாழன்")
                    FastingLayout(R.drawable.sdays_kiruthikai, "கிருத்திகை - 24 வியாழன்")
                    FastingLayout(R.drawable.sdays_sasti, "ஷஷ்டி - 24 வியாழன்")
                    FastingLayout(R.drawable.sdays_sathurthi, "சங்கடா சதுர்த்தி - 24 வியாழன்")
                    FastingLayout(R.drawable.sdays_yekathase, "ஏகாதேசி - 24 வியாழன்")
                    FastingLayout(R.drawable.sdays_pradhosam, "பிரதோஷம் - 24 வியாழன்")
                    FastingLayout(R.drawable.sdays_shiva_rathiri, "மாத சிவராத்திரி - 24 வியாழன்")
                    FastingLayout(R.drawable.sdays_sathurthi, "சதுர்த்தி - 24 வியாழன்")
                }
            }

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

    @SuppressLint("RestrictedApi")
    @Composable
    fun DateView(date: String, month: String, day: String, isTamil: Boolean) {
        Column(
            modifier = GlanceModifier
                .padding(horizontal = 12.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = month,
                style = TextStyle(
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold,
                    color = ColorProvider(if (isTamil) Color(0xFF252525) else Color(0xFFB51E25)),
                )
            )
            Text(
                text = date,
                modifier = GlanceModifier
                    .padding(vertical = 4.dp),
                style = TextStyle(
                    fontSize = 32.sp,
                    fontWeight = FontWeight.Bold,
                    color = ColorProvider(if (!isTamil) Color(0xFF252525) else Color(0xFFB51E25)),
                )
            )
            Text(
                text = day,
                style = TextStyle(
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold,
                    color = ColorProvider(if (isTamil) Color(0xFF252525) else Color(0xFFB51E25)),
                )
            )
        }
    }

    override suspend fun provideGlance(
        context: Context,
        id: GlanceId
    ) {
        provideContent {
            FastingDayView()
        }
    }
}