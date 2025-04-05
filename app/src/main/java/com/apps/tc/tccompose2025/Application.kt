package com.apps.tc.tccompose2025

import android.app.Application
import android.content.Context
import androidx.core.content.edit
import com.apps.tc.tccompose2025.models.ReminderNote
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
}