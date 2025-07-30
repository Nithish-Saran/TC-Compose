package com.apps.tc.tccompose2025.widgets.view

import android.annotation.SuppressLint
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.glance.GlanceModifier
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
import androidx.glance.text.FontWeight
import androidx.glance.text.Text
import androidx.glance.text.TextAlign
import androidx.glance.text.TextStyle
import androidx.glance.unit.ColorProvider
import com.apps.tc.tccompose2025.DayImportance
import com.apps.tc.tccompose2025.currentMonth
import com.apps.tc.tccompose2025.dateOfMonth
import com.apps.tc.tccompose2025.dayTa
import com.apps.tc.tccompose2025.getEnMonthName
import com.apps.tc.tccompose2025.models.FastingWidgetData
import com.apps.tc.tccompose2025.widgets.DateView
import com.apps.tc.tccompose2025.widgets.FastingLayout
import com.apps.tc.tccompose2025.widgets.VerticalDivider
import java.util.Date

@SuppressLint("RestrictedApi")
@Composable
fun FastingDayView(data: FastingWidgetData) {
    val date = Date()
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
                    .padding(bottom = 8.dp)
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                DateView(
                    day = date.dayTa,
                    date = date.dateOfMonth,
                    month = getEnMonthName(date.currentMonth),
                    isTamil = false,
                    modifier = GlanceModifier
                        .padding(end = 8.dp)
                        .defaultWeight(),
                    fontSizeDate = 14
                )
                VerticalDivider(14)

                DateView(
                    day = "sdgsdgsdg",
                    date = 0,
                    month = "sdfgsdg",
                    isTamil = true,
                    modifier = GlanceModifier
                        .padding(start = 8.dp)
                        .defaultWeight(),
                    fontSizeDate = 14
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

                FastingLayout(DayImportance.ammavasai.getImage(), "அமாவாசை - ${data.ammavasi}")
                FastingLayout(DayImportance.pournami.getImage(), "பௌர்ணமி - ${data.pournami}")
                FastingLayout(DayImportance.kiruthigai.getImage(), "கிருத்திகை - ${data.kiruthigai}")
                FastingLayout(DayImportance.sasti.getImage(), "ஷஷ்டி - ${data.shasti}")
                FastingLayout(DayImportance.sangadaSathurthi.getImage(), "சங்கடா சதுர்த்தி - ${data.sSathurthi}")
                FastingLayout(DayImportance.yekathasi.getImage(), "ஏகாதேசி - ${data.egathasi}")
                FastingLayout(DayImportance.pradosham.getImage(), "பிரதோஷம் - ${data.piradosham}")
                FastingLayout(DayImportance.shivaRathitri.getImage(), "மாத சிவராத்திரி - ${data.sivarathiri}")
                FastingLayout(DayImportance.chaturthi.getImage(), "சதுர்த்தி - ${data.sathurthi}")
            }
        }

    }
}