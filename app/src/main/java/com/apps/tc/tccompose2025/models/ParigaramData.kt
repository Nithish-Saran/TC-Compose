package com.apps.tc.tccompose2025.models

import android.util.Log
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import org.json.JSONObject

@Serializable
data class ParigaramData(
    val title: String,
    val articles: Array<String>,
    val htmls: Array<String>
) {
    companion object {
        fun serialize(json: JSONObject): ParigaramData? = try {
            Log.d("WHILOGS", "Parsing JSON: $json")
            Json.decodeFromString<ParigaramData>(json.toString())
        } catch (e: Exception) {
            Log.e("WHILOGS", "Error parsing JSON: ${e.message}", e)
            null
        }
    }
}
