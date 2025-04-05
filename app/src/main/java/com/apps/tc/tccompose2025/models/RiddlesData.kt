package com.apps.tc.tccompose2025.models

import android.util.Log
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import org.json.JSONObject

@Serializable
data class Riddle(
    val question: String,
    val answer: String,
    val hint: String,
    val category: Int
)

@Serializable
data class RiddlesData(
    val id: Int,
    val title: String,
    val riddles: List<Riddle>
) {
    val imgUrl get() = "file:///android_asset/riddles/ic_$id.png"
    val heading get() = "$title(${riddles.size})"
    companion object {
        fun serialize (json: JSONObject): RiddlesData? = try {
            Json.decodeFromString<RiddlesData>(json.toString())

        } catch (e: Exception) {
            Log.e("WHILOGS", "Error parsing JSON: ${e.message}", e)
            null
        }
    }
}
