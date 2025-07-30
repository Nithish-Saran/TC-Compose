package com.apps.tc.tccompose2025.widgets.view

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.glance.ColorFilter
import androidx.glance.GlanceModifier
import androidx.glance.Image
import androidx.glance.ImageProvider
import androidx.glance.LocalContext
import androidx.glance.LocalSize
import androidx.glance.action.clickable
import androidx.glance.appwidget.action.actionStartActivity
import androidx.glance.background
import androidx.glance.layout.Alignment
import androidx.glance.layout.Box
import androidx.glance.layout.Column
import androidx.glance.layout.Row
import androidx.glance.layout.Spacer
import androidx.glance.layout.fillMaxWidth
import androidx.glance.layout.height
import androidx.glance.layout.padding
import androidx.glance.layout.size
import androidx.glance.layout.width
import androidx.glance.layout.wrapContentSize
import androidx.glance.text.FontWeight
import androidx.glance.text.Text
import androidx.glance.text.TextAlign
import androidx.glance.text.TextStyle
import androidx.glance.unit.ColorProvider
import com.apps.tc.tccompose2025.MainActivity
import com.apps.tc.tccompose2025.R
import com.apps.tc.tccompose2025.dayTa
import com.apps.tc.tccompose2025.log
import com.apps.tc.tccompose2025.models.WidgetData
import com.apps.tc.tccompose2025.widgets.DateView
import com.apps.tc.tccompose2025.widgets.HorizondalDivider
import com.apps.tc.tccompose2025.widgets.NallaNeramView
import com.apps.tc.tccompose2025.widgets.VerticalDivider
import java.util.Date

@SuppressLint("RestrictedApi")
@RequiresApi(Build.VERSION_CODES.S)
@Composable
fun DailySheetExpandView(data: WidgetData, context: Context) {
    Box(
        modifier = GlanceModifier
            .wrapContentSize()
            .background(ColorProvider(Color(0xFFFFF8DD)))
            .padding(12.dp)
            .clickable(
                actionStartActivity(
                    Intent(context, MainActivity::class.java).apply {
                        putExtra("open_module", "daily_sheet")
                        flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP
                    }
                )
            )
    ) {
        Column(
            verticalAlignment = Alignment.CenterVertically,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Top Row: Month and Tamil date
            Row(
                modifier = GlanceModifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                DateView(
                    day = Date().dayTa,
                    date = data.date,
                    month = data.monthEn,
                    isTamil = false,
                    modifier = GlanceModifier
                        .defaultWeight(),
                    fontSizeDate = 58
                )
                VerticalDivider(64)
                DateView(
                    day = data.taYear,
                    date = data.taDate,
                    month = data.monthTa,
                    isTamil = true,
                    modifier = GlanceModifier
                        .defaultWeight(),
                    fontSizeDate = 58
                )
                VerticalDivider(64)
                Column(
                    modifier = GlanceModifier
                        .defaultWeight(),
                    horizontalAlignment = Alignment.CenterHorizontally,

                ) {
                    Text(
                        text = data.piraiName,
                        style = TextStyle(
                            fontSize = 9.sp,
                            fontWeight = FontWeight.Bold,
                            color = ColorProvider( Color(0xFF252525)),
                            textAlign = TextAlign.Center
                        )
                    )
                    Spacer(modifier = GlanceModifier.height(15.dp))
                    Image(
                        provider = ImageProvider(data.importantDay?.getImage() ?: data.piraiImg),
                        contentDescription = "3D Calendar",
                        modifier = GlanceModifier
                            .size(48.dp),
                        colorFilter = ColorFilter.tint(
                            ColorProvider(Color(0xFFB51E25)))
                    )
                    Spacer(modifier = GlanceModifier.height(15.dp))
                    Text(
                        text = data.nokkuName,
                        style = TextStyle(
                            fontSize = 9.sp,
                            fontWeight = FontWeight.Bold,
                            color = ColorProvider( Color(0xFF252525)),
                            textAlign = TextAlign.Center
                        )
                    )
                }
            }

            Spacer(modifier = GlanceModifier.height(8.dp))
            HorizondalDivider()
            Row(
                modifier = GlanceModifier
                    .padding(vertical = 8.dp)
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text(
                        text = "நல்ல நேரம்",
                        modifier = GlanceModifier
                            .padding(bottom = 4.dp),
                        style = TextStyle(
                            fontSize = 8.sp,
                            fontWeight = FontWeight.Medium,
                            color = ColorProvider(Color(0xFF252525)),
                        )
                    )
                    Row {
                        NallaNeramView(
                            image = R.drawable.ic_morning,
                            text = "6.00 - 7.00"
                        )
                        Spacer(modifier = GlanceModifier.width(8.dp))
                        NallaNeramView(
                            image = R.drawable.ic_night,
                            text = "12.00 - 1.00"
                        )
                    }

                }
                Spacer(modifier = GlanceModifier.width(16.dp))
                Column(
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Text(
                        text = "எமகண்டம்",
                        modifier = GlanceModifier
                            .padding(bottom = 4.dp),
                        style = TextStyle(
                            fontSize = 8.sp,
                            fontWeight = FontWeight.Medium,
                            color = ColorProvider(Color(0xFF252525)),
                        )
                    )
                    Text(
                        text = data.emagandam,
                        style = TextStyle(
                            fontSize = 8.sp,
                            fontWeight = FontWeight.Medium,
                            color = ColorProvider(Color(0xFFB51E25)),
                        )
                    )
                }
            }
            HorizondalDivider()
            Spacer(modifier = GlanceModifier.height(8.dp))

            Text(
                text = "உண்மையான கல்வி மனிதனை சிந்திக்க வைக்கிறது. -கன்பூசியஸ்.",
                style = TextStyle(
                    fontSize = 10.sp,
                    fontWeight = FontWeight.Medium,
                    color = ColorProvider(Color(0xFFB51E25)),
                    textAlign = TextAlign.Center
                )
            )
        }
    }
}