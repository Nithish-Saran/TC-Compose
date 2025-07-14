package com.apps.tc.tccompose2025.widgets

import androidx.glance.appwidget.GlanceAppWidget
import androidx.glance.appwidget.GlanceAppWidgetReceiver

class GoodTimeReceiver: GlanceAppWidgetReceiver()  {
    override val glanceAppWidget: GlanceAppWidget = GoodTimeWidget()
}