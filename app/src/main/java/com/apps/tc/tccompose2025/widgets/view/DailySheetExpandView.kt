package com.apps.tc.tccompose2025.widgets.view

import android.annotation.SuppressLint
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.glance.GlanceModifier
import androidx.glance.Image
import androidx.glance.ImageProvider
import androidx.glance.LocalSize
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
import com.apps.tc.tccompose2025.R
import com.apps.tc.tccompose2025.log
import com.apps.tc.tccompose2025.widgets.DateView
import com.apps.tc.tccompose2025.widgets.HorizondalDivider
import com.apps.tc.tccompose2025.widgets.VerticalDivider

@SuppressLint("RestrictedApi")
@RequiresApi(Build.VERSION_CODES.S)
@Composable
fun DailySheetExpandView() {
    val isSpecial = true
    val size = LocalSize.current
    //log(size)
    Box(
        modifier = GlanceModifier
            .wrapContentSize()
            .background(ColorProvider(Color(0xFFFFF8DD).copy(alpha = 0.93f)))
            .padding(12.dp)
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
                    day = "செவ்வாய்",
                    date = "29",
                    month = "செப்டம்பர்",
                    isTamil = false,
                    modifier = GlanceModifier
                        .defaultWeight(),
                    fontSizeDate = 64
                )
                VerticalDivider(64)
                Spacer(modifier = GlanceModifier.width(10.dp))
                DateView(
                    day = "ருத்ரோத்காரி",
                    date = "29",
                    month = "கார்த்திகை",
                    isTamil = true,
                    modifier = GlanceModifier
                        .defaultWeight(),
                    fontSizeDate = 64
                )
                Spacer(modifier = GlanceModifier.width(10.dp))
                VerticalDivider(64)

                Column(
                    modifier = GlanceModifier
                        .padding(start = if (size.width <= 250.dp) 8.dp else 0.dp)
                        .defaultWeight(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "தேய்பிறை",
                        style = TextStyle(
                            fontSize = 9.sp,
                            fontWeight = FontWeight.Bold,
                            color = ColorProvider(Color(0xFF252525)),
                            textAlign = TextAlign.Center
                        )
                    )
                    Spacer(modifier = GlanceModifier.height(if (!isSpecial)18.dp else 22.dp))
                    Row(
                        modifier = GlanceModifier
                            .defaultWeight(),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        if (isSpecial) {
                            Image(
                                provider = ImageProvider(R.drawable.month_muhurtham),
                                contentDescription = "3D Calendar",
                                modifier = GlanceModifier
                                    .size(42.dp),
                            )
                        }
                        Spacer(modifier = GlanceModifier.width(if (size.width <= 250.dp)0.dp else 12.dp))
                        Image(
                            provider = ImageProvider(R.drawable.month_sasti),
                            contentDescription = "3D Calendar",
                            modifier = GlanceModifier
                                .size(if (!isSpecial) 50.dp else 42.dp)
                        )
                    }
                    Spacer(modifier = GlanceModifier.height(if (!isSpecial)18.dp else 22.dp))
                    Text(
                        text = "கீழ்நோக்கு",
                        style = TextStyle(
                            fontSize = 9.sp,
                            fontWeight = FontWeight.Bold,
                            color = ColorProvider(Color(0xFF252525)),
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
                verticalAlignment = Alignment.CenterVertically,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "விருச்சிகம் - இன்பம்",
                    modifier = GlanceModifier.defaultWeight(),
                    style = TextStyle(
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Medium,
                        color = ColorProvider(Color(0xFF252525)),
                        textAlign = TextAlign.Center
                    )
                )
                if (size.width > 280.dp) {
                    VerticalDivider(24)
                    Text(
                        text = "சூரிய உதயம் - 6.00",
                        modifier = GlanceModifier.defaultWeight(),
                        style = TextStyle(
                            fontSize = 10.sp,
                            fontWeight = FontWeight.Medium,
                            color = ColorProvider(Color(0xFFB51E25)),
                            textAlign = TextAlign.Center
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