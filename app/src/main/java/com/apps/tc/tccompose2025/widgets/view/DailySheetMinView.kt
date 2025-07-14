package com.apps.tc.tccompose2025.widgets.view

import android.annotation.SuppressLint
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
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
import androidx.glance.layout.wrapContentSize
import androidx.glance.text.FontWeight
import androidx.glance.text.Text
import androidx.glance.text.TextAlign
import androidx.glance.text.TextStyle
import androidx.glance.unit.ColorProvider
import com.apps.tc.tccompose2025.R
import com.apps.tc.tccompose2025.widgets.DateView
import com.apps.tc.tccompose2025.widgets.HorizondalDivider
import com.apps.tc.tccompose2025.widgets.VerticalDivider
import java.util.Date

@SuppressLint("RestrictedApi")
@RequiresApi(Build.VERSION_CODES.S)
@Composable
fun DailySheetMinView() {
    val isSpecial = true
    Box(
        modifier = GlanceModifier
            .wrapContentSize()
            .background(ColorProvider(Color(0xFFFFF8DD).copy(alpha = 0.93f)))
            .padding(start = 18.dp, end = 18.dp, top = 12.dp, bottom = 12.dp)
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
                    day = "செவ்வாய்",
                    date = "29",
                    month = "செப்டம்பர்",
                    isTamil = false,
                    modifier = GlanceModifier
                        .padding(end = 8.dp)
                        .defaultWeight(),
                    fontSizeDate = 64
                )
                VerticalDivider(64)

                DateView(
                    day = "ருத்ரோத்காரி",
                    date = "29",
                    month = "கார்த்திகை",
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
                Image(
                    provider = ImageProvider(R.drawable.widget_valarpirai),
                    contentDescription = "3D Calendar",
                    modifier = GlanceModifier
                        .defaultWeight()
                        .padding(8.dp)
                        .size(38.dp),

                    )
                Image(
                    provider = ImageProvider(R.drawable.widget_sama),
                    contentDescription = "3D Calendar",
                    modifier = GlanceModifier
                        .defaultWeight()
                        .padding(8.dp)
                        .size(38.dp),
                    colorFilter = ColorFilter.tint(ColorProvider(Color(0xFF252525)))
                )
                if (isSpecial) {
                    Image(
                        provider = ImageProvider(R.drawable.month_muhurtham),
                        contentDescription = "3D Calendar",
                        modifier = GlanceModifier
                            .defaultWeight()
                            .padding(8.dp)
                            .size(38.dp),
                        colorFilter = ColorFilter.tint(ColorProvider(Color(0xFF252525)))
                    )
                    Image(
                        provider = ImageProvider(R.drawable.month_sasti),
                        contentDescription = "3D Calendar",
                        modifier = GlanceModifier
                            .defaultWeight()
                            .padding(8.dp)
                            .size(38.dp)
                    )
                }
            }

            HorizondalDivider()

            Row(
                modifier = GlanceModifier
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
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
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Image(
                            provider = ImageProvider(R.drawable.ic_morning),
                            contentDescription = "3D Calendar",
                            modifier = GlanceModifier
                                .padding(end = 8.dp)
                                .size(24.dp)
                        )
                        Text(
                            text = "6.00 - 7.00",
                            style = TextStyle(
                                fontSize = 10.sp,
                                fontWeight = FontWeight.Medium,
                                color = ColorProvider(Color(0xFFB51E25)),
                            )
                        )
                    }

                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Image(
                            provider = ImageProvider(R.drawable.ic_night),
                            contentDescription = "3D Calendar",
                            modifier = GlanceModifier
                                .padding(end = 8.dp)
                                .size(24.dp),
                            colorFilter = ColorFilter.tint(ColorProvider(Color(0xFFA60E1B)))
                        )
                        Text(
                            text = "6.00 - 7.00",
                            style = TextStyle(
                                fontSize = 10.sp,
                                fontWeight = FontWeight.Medium,
                                color = ColorProvider(Color(0xFFB51E25)),
                            )
                        )
                    }
                }
                Column(
                    modifier = GlanceModifier
                        .padding(start = 32.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Image(
                        provider = ImageProvider(R.drawable.widget_viruchigam),
                        contentDescription = "3D Calendar",
                        modifier = GlanceModifier
                            .padding(end = 8.dp)
                            .size(42.dp)
                    )
                    Text(
                        text = "இன்பம்",
                        style = TextStyle(
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold,
                            color = ColorProvider(Color(0xFF252525)),
                        )
                    )
                }
            }
        }
    }
}