package com.apps.tc.tccompose2025.models

import com.apps.tc.tccompose2025.Rasi
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import org.json.JSONObject

@Serializable
data class PlanetoryData(
    @SerialName("last_start_date")val startDate: String,
    @SerialName("current_rasi")val currentRasi: Int,
    @SerialName("total_days")val totalDays: Int
) {
    val rasi: String get() = Rasi.getAllRasis()[currentRasi - 1].displayName

    companion object {
        fun serialize(json: JSONObject): PlanetoryData? = try {
            Json.decodeFromString<PlanetoryData>(json.toString())
        } catch (e: Exception) {
            null
        }
    }
}
