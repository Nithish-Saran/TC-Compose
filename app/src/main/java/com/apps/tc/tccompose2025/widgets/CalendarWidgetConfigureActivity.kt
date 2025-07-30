package com.apps.tc.tccompose2025.widgets

import android.appwidget.AppWidgetManager
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.glance.appwidget.updateAll
import com.apps.tc.tccompose2025.App
import com.apps.tc.tccompose2025.RasiSelectionDialog
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class CalendarWidgetConfigureActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val app = application as App

        setContent {
            RasiSelectionDialog(app = app) { selectedIndex ->
                val appWidgetId = intent.getIntExtra(
                    AppWidgetManager.EXTRA_APPWIDGET_ID,
                    AppWidgetManager.INVALID_APPWIDGET_ID
                )

                if (appWidgetId != AppWidgetManager.INVALID_APPWIDGET_ID) {
                    app.saveSelectedRasi(selectedIndex)

                    // Run everything inside a coroutine and only finish after it's done
                    CoroutineScope(Dispatchers.Main).launch {
                        // Load and save today's data first
                        withContext(Dispatchers.IO) {
                            //loadTodayData(app)
                        }

                        // Now update the widget
                        RasiWidget().updateAll(app)

                        // Only finish AFTER widget is updated
                        setResult(
                            RESULT_OK,
                            Intent().putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId)
                        )
                        finish()
                    }
                }
            }
        }
    }
}

