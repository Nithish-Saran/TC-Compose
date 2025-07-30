package com.apps.tc.tccompose2025.widgets

import android.appwidget.AppWidgetManager
import android.content.Context
import androidx.glance.appwidget.GlanceAppWidget
import androidx.glance.appwidget.GlanceAppWidgetReceiver

class DailySheetWidgetReceiver : GlanceAppWidgetReceiver() {
    override val glanceAppWidget: GlanceAppWidget
        get() = DailySheetWidget()

    override fun onEnabled(context: Context) {
        super.onEnabled(context)
        refreshGlanceWidgets(context, DailySheetWidget::class.java)
    }

    override fun onUpdate(
        context: Context,
        appWidgetManager: AppWidgetManager,
        appWidgetIds: IntArray
    ) {
        super.onUpdate(context, appWidgetManager, appWidgetIds)
        refreshGlanceWidgets(context, DailySheetWidget::class.java)
    }
}

