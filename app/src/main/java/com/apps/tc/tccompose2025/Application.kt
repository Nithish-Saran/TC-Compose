package com.apps.tc.tccompose2025

import android.app.Application
import android.content.Context
import androidx.core.content.edit
import com.apps.tc.tccompose2025.models.ReminderNote
import com.apps.tc.tccompose2025.models.WidgetData
import kotlinx.serialization.json.Json
import org.json.JSONArray

class App: Application() {

    companion object {
        /**
         * Singleton instance of the ListApp application class.
         * This allows access to application-level context and functions.
         */
        lateinit var instance: App
            private set
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
    }

    fun getNotes(): Array<ReminderNote> = ReminderNote.getNotes(
        getSharedPreferences(
            AppPref, MODE_PRIVATE
        ).getString(PrefNotes, "") ?: ""
    )

    fun setNotes(notes: Array<ReminderNote>) {
        val array = JSONArray()
        notes.forEach { array.put(it.toJSON()) }
        getSharedPreferences(AppPref, MODE_PRIVATE).edit {
            putString(PrefNotes, array.toString())
            apply()
        }
    }

    fun setWidgetData(data: WidgetData) {
        val prefs = getSharedPreferences(AppPref, MODE_PRIVATE)
        val jsonString = Json.encodeToString(data)
        prefs.edit {
            putString(WidgetPref, jsonString)
            apply()
        }
    }

    fun getWidgetData(): WidgetData?  {
        val prefs = getSharedPreferences(AppPref, MODE_PRIVATE)
        val jsonString = prefs.getString(WidgetPref, null) ?: return null
        return if (jsonString.isEmpty()) null else Json.decodeFromString<WidgetData>(jsonString)
    }

    fun saveSelectedRasi(index: Int) {
        val prefs = getSharedPreferences(AppPref, MODE_PRIVATE)
        prefs.edit {
            putInt(RasiPref, index)
            apply()
        }
    }

    fun getSelectedRasi(context: Context): Int {
        val prefs = context.getSharedPreferences(AppPref, MODE_PRIVATE)
        return prefs.getInt(RasiPref, -1)
    }
}