package com.apps.tc.tccompose2025.widgets

import android.content.Context
import android.content.Intent
import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.Color
import androidx.glance.Button
import androidx.glance.GlanceId
import androidx.glance.GlanceModifier
import androidx.glance.action.ActionParameters
import androidx.glance.appwidget.GlanceAppWidget
import androidx.glance.appwidget.action.ActionCallback
import androidx.glance.appwidget.action.actionRunCallback
import androidx.glance.appwidget.provideContent
import androidx.glance.background
import androidx.glance.layout.Alignment
import androidx.glance.layout.Box
import androidx.glance.layout.fillMaxSize
import androidx.glance.text.Text
import com.apps.tc.tccompose2025.App
import com.apps.tc.tccompose2025.MainActivity
import com.apps.tc.tccompose2025.log
import com.apps.tc.tccompose2025.widgets.view.RasiView

class RasiWidget : GlanceAppWidget() {
    override suspend fun provideGlance(context: Context, id: GlanceId) {
        val app = context.applicationContext as App
        val selectedRasi = app.getSelectedRasi(context) // -1 if not chosen
        //val widgetData = getWidgetData(context, id)

        provideContent {
//            if (selectedRasi == -1) {
//                Box(
//                    modifier = GlanceModifier.fillMaxSize().background(Color.White),
//                    contentAlignment = Alignment.Center
//                ) {
//                    Button(
//                        text = "உங்கள் ராசியை தேர்வு செய்யவும்",
//                        onClick = actionRunCallback<OpenRasiPickerAction>()
//                    )
//                }
//            } else {
//                // Show actual Rasi widget
//                if (widgetData != null) RasiView(app, widgetData)
//            }
//            val intent = Intent(context, MainActivity::class.java).apply {
//                flags = Intent.FLAG_ACTIVITY_NEW_TASK
//                putExtra("open_rasi_dialog", true) // tell your MainActivity to show dialog
//            }
            actionRunCallback<OpenRasiPickerAction>()
            //RasiView(app, widgetData!!)
        }
    }
}

// Opens the Rasi selection dialog (without config activity)
class OpenRasiPickerAction : ActionCallback {
    override suspend fun onAction(context: Context, glanceId: GlanceId, parameters: ActionParameters) {
        val intent = Intent(context, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK
            putExtra("open_module", "rasi_dialog") // tell your MainActivity to show dialog
        }
        context.startActivity(intent)
    }
}
