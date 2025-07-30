package com.apps.tc.tccompose2025.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import org.json.JSONObject


@Serializable
data class MeetupData(
    val venues: List<Venue>,
    val teams: List<Team>
) {
    companion object {
        fun serialize(json: JSONObject): MeetupData? = try {
            Json.decodeFromString<MeetupData>(json.toString())
        } catch (e: Exception) {
            null
        }
    }
}

@Serializable
data class Venue(
    val date: String,
    val time: String,
    @SerialName("banner_url") val bannerUrl: String,
    val location: String,
    @SerialName("shop_name") val shopName: String,
    val address: String,
    @SerialName("location_link") val locationLink: String,
    @SerialName("open_time") val openTime: String
)

@Serializable
data class Team(
    val name: String,
    val title: String,
    @SerialName("image_url") val imageUrl: String
)

