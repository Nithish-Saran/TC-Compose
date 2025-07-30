package com.apps.tc.tccompose2025.widgets

import android.annotation.SuppressLint
import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.glance.GlanceId
import androidx.glance.LocalSize
import androidx.glance.appwidget.GlanceAppWidget
import androidx.glance.appwidget.SizeMode
import androidx.glance.appwidget.provideContent
import com.apps.tc.tccompose2025.log
import com.apps.tc.tccompose2025.widgets.view.DailySheetExpandView
import com.apps.tc.tccompose2025.widgets.view.DailySheetMinView
import com.apps.tc.tccompose2025.widgets.view.NoDataView

// DailySheetWidget.kt
class DailySheetWidget : GlanceAppWidget() {

    companion object {
        private val SMALL_SQUARE = DpSize(165.dp, 165.dp)
        private val LARGE_SQUARE = DpSize(250.dp, 120.dp)
    }

    override val sizeMode = SizeMode.Responsive(setOf(SMALL_SQUARE, LARGE_SQUARE))

    @SuppressLint("RestrictedApi")
    @RequiresApi(Build.VERSION_CODES.S)
    override suspend fun provideGlance(context: Context, id: GlanceId) {
        val widgetData = getWidgetData(context, id)
        provideContent {
            val size = LocalSize.current
            widgetData?.let {data ->
                log(data.toString())
                when (size) {
                    SMALL_SQUARE -> {
                        DailySheetMinView(data, context)
                    }
                    LARGE_SQUARE -> DailySheetExpandView(data, context)
                }
            }?: NoDataView()
        }
    }
}



