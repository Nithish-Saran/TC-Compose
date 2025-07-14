package com.apps.tc.tccompose2025.widgets

import android.appwidget.AppWidgetManager
import android.content.Context
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.glance.GlanceId
import androidx.glance.LocalContext
import androidx.glance.LocalSize
import androidx.glance.appwidget.GlanceAppWidget
import androidx.glance.appwidget.GlanceAppWidgetManager
import androidx.glance.appwidget.GlanceAppWidgetReceiver
import androidx.glance.appwidget.SizeMode
import androidx.glance.appwidget.provideContent
import com.apps.tc.tccompose2025.log
import com.apps.tc.tccompose2025.widgets.view.DailySheetExpandView
import com.apps.tc.tccompose2025.widgets.view.DailySheetMinView

class DailySheetWidget : GlanceAppWidget() {

    companion object {
        private val SMALL_SQUARE = DpSize(165.dp, 165.dp)
        private val LARGE_SQUARE = DpSize(250.dp, 120.dp)
    }
    override val sizeMode = SizeMode.Responsive(setOf(SMALL_SQUARE, LARGE_SQUARE))

    @RequiresApi(Build.VERSION_CODES.S)
    override suspend fun provideGlance(context: Context, id: GlanceId) {

        provideContent {
            val size = LocalSize.current
            when(size) {
                SMALL_SQUARE -> {
                    log(size)
                    DailySheetMinView()
                }
                LARGE_SQUARE -> {
                    log(size)
                    DailySheetExpandView()
                }
            }
        }
    }
}

class DailySheetWidgetReceiver : GlanceAppWidgetReceiver() {
    override val glanceAppWidget: GlanceAppWidget
        get() = DailySheetWidget()
}
