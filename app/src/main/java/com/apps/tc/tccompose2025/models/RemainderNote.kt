package com.apps.tc.tccompose2025.models

import android.util.Log
import com.apps.tc.tccompose2025.objectArray
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject
import java.util.Date

data class ReminderNote(
    val title: String,
    val desc: String,
    val reminder: Date
) {

    val display: String
        get() = "$title - $desc"

    fun toJSON(): JSONObject = JSONObject().apply {
        put("title", title)
        put("desc", desc)
        put("reminder", reminder.time)
    }

    companion object {
        fun prepare(notes: JSONObject): ReminderNote? {
            if (notes.has("title") && notes.has("desc") && notes.has("reminder")) {
                return ReminderNote(notes.getString("title"), notes.getString("desc"),
                    Date(notes.getLong("reminder")))
            }
            return null
        }

        fun getNotes(notes: String): Array<ReminderNote> {
            try {
                return JSONArray(notes).objectArray().mapNotNull { prepare(it) }.toTypedArray()
            }
            catch(e: JSONException) {
                Log.e(e.toString(), "Reminder Notes JSON Parsing")
            }
            return emptyArray<ReminderNote>()
        }
    }

}