package com.apps.tc.tccompose2025.widgets

import android.content.Context
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.glance.GlanceId
import androidx.glance.appwidget.GlanceAppWidget
import androidx.glance.appwidget.GlanceAppWidgetManager
import androidx.glance.appwidget.state.getAppWidgetState
import androidx.glance.appwidget.state.updateAppWidgetState
import androidx.glance.state.PreferencesGlanceStateDefinition
import com.apps.tc.tccompose2025.App
import com.apps.tc.tccompose2025.DayImportance
import com.apps.tc.tccompose2025.RasiSelectionDialog
import com.apps.tc.tccompose2025.currentYear
import com.apps.tc.tccompose2025.dateOfMonth
import com.apps.tc.tccompose2025.getTamilMonthName
import com.apps.tc.tccompose2025.getWeekDayInTamil
import com.apps.tc.tccompose2025.loadJsonFromAssets
import com.apps.tc.tccompose2025.logE
import com.apps.tc.tccompose2025.models.FastingWidgetData
import com.apps.tc.tccompose2025.models.WidgetData
import com.apps.tc.tccompose2025.objectArray
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlinx.serialization.json.Json
import org.json.JSONArray
import org.json.JSONObject
import java.util.Date
import kotlin.to

suspend fun loadTodayData(context: Context, glanceId: GlanceId): WidgetData? =
    withContext(Dispatchers.IO) {
        val context = context.applicationContext as App
        val data = loadJsonFromAssets(context, "dailySheet/date-7-2025.json")
        val monthData = JSONArray(data).optJSONObject(Date().dateOfMonth - 1)

        repeat(12) { index ->
            val data = loadJsonFromAssets(context, "dailySheet/date-7-2025.json")
            val monthData = JSONArray(data).optJSONObject(Date().dateOfMonth - 1)
        }

        WidgetData.serialize(monthData)?.also { widgetData ->
            saveWidgetData(context, glanceId, widgetData)
        }
    }

fun getFastingDayData(context: Context): List<FastingWidgetData> {
    val appContext = context.applicationContext as App
    val year = Date().currentYear

    fun parseString(json: JSONObject, month: Int): String {
        return "${json.getInt("date")} " +
                getWeekDayInTamil(year, month, json.getInt("date"))
    }

    val keys = listOf(
        "ammavasai", "pournami", "kiruthigai", "sivarathiri",
        "sathurthi", "sSathurthi", "egathasi", "shasti", "piradosham", "taYear",
    )

    return (1..12).map { month ->
        val jsonString = loadJsonFromAssets(appContext, "dailySheet/date-${month}-2025.json")
        val fastingMap = keys.associateWith { mutableListOf<String>() }
        JSONArray(jsonString).objectArray().forEach{ day -> // represent single day from array of one month
            keys.forEach { key ->
                if (day.getBoolean(key)) {
                    fastingMap[key]?.add(parseString(day, month))
                } else if (day.getString(key) == "taYear") {
                    fastingMap[key]?.add(day.getString(key))
                }
            }
        }
        FastingWidgetData(
            ammavasi = fastingMap["ammavasai"]!!.joinToString(", "),
            pournami = fastingMap["pournami"]!!.joinToString(", "),
            kiruthigai = fastingMap["kiruthigai"]!!.joinToString(", "),
            sivarathiri = fastingMap["sivarathiri"]!!.joinToString(", "),
            sathurthi = fastingMap["sathurthi"]!!.joinToString(", "),
            sSathurthi = fastingMap["sSathurthi"]!!.joinToString(", "),
            egathasi = fastingMap["egathasi"]!!.joinToString(", "),
            shasti = fastingMap["shasti"]!!.joinToString(", "),
            piradosham = fastingMap["piradosham"]!!.joinToString(", "),
            taYear = fastingMap["taYear"]!!.joinToString(", "),
        )
    }
}


private val WIDGET_DATA_KEY = stringPreferencesKey("widget_data")
private val FASTING_WIDGET_DATA_KEY = stringPreferencesKey("fasting_widget_data")

suspend fun saveFastingWidgetData(
    context: Context,
    glanceId: GlanceId,
    widgetData: List<FastingWidgetData>
) {
    val json = Json.encodeToString(widgetData)
    updateAppWidgetState(context, PreferencesGlanceStateDefinition, glanceId) { prefs ->
        prefs.toMutablePreferences().apply {
            this[FASTING_WIDGET_DATA_KEY] = json
        }
    }
}

suspend fun getFastingWidgetData(context: Context, glanceId: GlanceId): List<FastingWidgetData>? {
    val prefs = getAppWidgetState(context, PreferencesGlanceStateDefinition, glanceId)
    val json = prefs[FASTING_WIDGET_DATA_KEY] ?: return null
    return Json.decodeFromString(json)
}

suspend fun saveWidgetData(context: Context, glanceId: GlanceId, widgetData: WidgetData) {
    val json = Json.encodeToString(widgetData)
    updateAppWidgetState(context, PreferencesGlanceStateDefinition, glanceId) { prefs ->
        prefs.toMutablePreferences().apply {
            this[WIDGET_DATA_KEY] = json
        }
    }
}

suspend fun getWidgetData(context: Context, glanceId: GlanceId): WidgetData? {
    val prefs = getAppWidgetState(context, PreferencesGlanceStateDefinition, glanceId)
    val json = prefs[WIDGET_DATA_KEY] ?: return null
    return Json.decodeFromString(json)
}

fun <T : GlanceAppWidget> refreshGlanceWidgets(
    context: Context,
    widgetClass: Class<T>
) {
    CoroutineScope(Dispatchers.Main).launch {
        val manager = GlanceAppWidgetManager(context)
        val glanceIds = manager.getGlanceIds(widgetClass)
        glanceIds.forEach { glanceId ->
            val widgetData = loadTodayData(context, glanceId)
            if (widgetData != null) {
                widgetClass.getDeclaredConstructor().newInstance().update(context, glanceId)
            }
        }
    }
}