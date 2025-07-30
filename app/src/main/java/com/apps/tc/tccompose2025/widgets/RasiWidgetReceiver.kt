package com.apps.tc.tccompose2025.widgets

import android.appwidget.AppWidgetManager
import android.content.Context
import android.content.Intent
import androidx.glance.appwidget.GlanceAppWidget
import androidx.glance.appwidget.GlanceAppWidgetReceiver
import com.apps.tc.tccompose2025.MainActivity

class RasiWidgetReceiver : GlanceAppWidgetReceiver() {

    override val glanceAppWidget: GlanceAppWidget = RasiWidget()

//    override fun onEnabled(context: Context) {
//        super.onEnabled(context)
//        val intent = Intent(context, MainActivity::class.java).apply {
//            flags = Intent.FLAG_ACTIVITY_NEW_TASK
//            putExtra("open_module", "rasi_dialog")
//        }
//        context.startActivity(intent)
//    }

//    override fun onUpdate(context: Context, appWidgetManager: AppWidgetManager, appWidgetIds: IntArray) {
//        super.onUpdate(context, appWidgetManager, appWidgetIds)
//        refreshGlanceWidgets(context, RasiWidget::class.java)
//    }
}
