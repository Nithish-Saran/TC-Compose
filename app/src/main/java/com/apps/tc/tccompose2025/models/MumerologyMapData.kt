package com.apps.tc.tccompose2025.models

import com.apps.tc.tccompose2025.logE
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import org.json.JSONObject

@Serializable
data class MapEntry(
    val politics: Int,
    val business: Int,
    @SerialName("team_work") val teamWork: Int,
    val love: Int,
    val friendship: Int,
    val employment: Int,
    val project: Int,
    val job: Int,
    val location: Int,
    val vehicle: Int,
    val teaching: Int,
    @SerialName("num_one") val numOne: Int,
    @SerialName("num_two")val numTwo: Int
) {
    companion object {
        fun serialize(json: JSONObject): MapEntry? = try {
            Json.decodeFromString<MapEntry>(json.toString())
        } catch(e: Exception) {
            logE(e, "")
            null
        }
    }
}
