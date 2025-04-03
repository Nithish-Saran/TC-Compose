package com.apps.tc.tccompose2025.models

import android.util.Log
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import org.json.JSONObject

@Serializable
data class RewindData(
    val date: String,
    val month: String,
    val title: String,
    val key: String,
    val desc: String
) {
    companion object {
        fun serialize(json: JSONObject) : RewindData? = try {
            Json.decodeFromString<RewindData>(json.toString())
        } catch (e: Exception) {
            Log.e("WHILOGS", "Error parsing JSON: ${e.message}", e)
            null
        }
    }
}
