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
import com.apps.tc.tccompose2025.dayEn
import com.apps.tc.tccompose2025.dayTa
import com.apps.tc.tccompose2025.models.WidgetData
import com.apps.tc.tccompose2025.widgets.DateView
import com.apps.tc.tccompose2025.widgets.HorizondalDivider
import com.apps.tc.tccompose2025.widgets.NallaNeramView
import com.apps.tc.tccompose2025.widgets.VerticalDivider
import java.util.Date

@SuppressLint("RestrictedApi", "ViewModelConstructorInComposable")
@RequiresApi(Build.VERSION_CODES.S)
@Composable
fun DailySheetMinView(data: WidgetData, context: Context) {
    Box(
        modifier = GlanceModifier
            .wrapContentSize()
            .background(ColorProvider(Color(0xFFFFF8DD)))
            .padding(16.dp)
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
            Row(
                modifier = GlanceModifier
                    .padding(bottom = 8.dp)
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
                        .padding(end = 8.dp)
                        .defaultWeight(),
                    fontSizeDate = 64
                )
                VerticalDivider(64)

                DateView(
                    day = data.taYear,
                    date = data.taDate,
                    month = data.monthTa,
                    isTamil = true,
                    modifier = GlanceModifier
                        .padding(start = 8.dp)
                        .defaultWeight(),
                    fontSizeDate = 64
                )
            }

            HorizondalDivider()

            Row(
                modifier = GlanceModifier
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                listOfNotNull(
                    data.piraiImg,
                    data.nokkuImg,
                    data.importantDay?.getImage()
                ).forEachIndexed{ index, img ->
                    Image(
                        provider = ImageProvider(img),
                        contentDescription = "3D Calendar",
                        modifier = GlanceModifier
                            .defaultWeight()
                            .padding(8.dp)
                            .size(38.dp),
                        colorFilter = ColorFilter.tint(
                            ColorProvider(if (index != 1) Color(0xFFB51E25)
                            else Color(0xFF252525)))
                    )
                }
            }

            HorizondalDivider()

            Row(
                modifier = GlanceModifier
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Column(
                    modifier = GlanceModifier.defaultWeight(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    Text(
                        text = "நல்ல நேரம்",
                        modifier = GlanceModifier
                            .padding(vertical = 8.dp),
                        style = TextStyle(
                            fontSize = 8.sp,
                            fontWeight = FontWeight.Medium,
                            color = ColorProvider(Color(0xFF252525)),
                        )
                    )
                    NallaNeramView(
                        image = R.drawable.ic_morning,
                        text = "6.00 - 7.00"
                    )
                    Spacer(modifier = GlanceModifier.height(4.dp))
                    NallaNeramView(
                        image = R.drawable.ic_night,
                        text = "12.00 - 1.00"
                    )
                }

                Column(
                    modifier = GlanceModifier
                        .defaultWeight(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "எமகண்டம்",
                        modifier = GlanceModifier
                            .padding(vertical = 8.dp),
                        style = TextStyle(
                            fontSize = 8.sp,
                            fontWeight = FontWeight.Medium,
                            color = ColorProvider(Color(0xFF252525)),
                        )
                    )
                    Text(
                        text = data.emagandam,
                        modifier = GlanceModifier
                            .padding(start = 18.dp),
                        style = TextStyle(
                            fontSize = 8.sp,
                            fontWeight = FontWeight.Medium,
                            color = ColorProvider(Color(0xFFB51E25)),
                        )
                    )
                }
            }
        }
    }
}