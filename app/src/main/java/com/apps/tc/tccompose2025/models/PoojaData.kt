package com.apps.tc.tccompose2025.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import org.json.JSONObject

@Serializable
data class Song(
    @SerialName("name")
    val songName: String,

    @SerialName("image")
    val imageUrl: String
) {
    val songImg get() = "file:///android_asset/virtualpooja/$imageUrl"
}

@Serializable
data class Theme(
    @SerialName("bg")
    val poojaBg: String,
    @SerialName("god_bg")
    val godBg: String
) {
    val poojaBgImage get() = "file:///android_asset/virtualpooja/$poojaBg"
    val godBgImage get() = "file:///android_asset/virtualpooja/$godBg"
}

data class PoojaButton(
    val icon: Int,
    val label: String,
    val orbitIndex: Int? = null // null means no orbiting behavior
)


@Serializable
data class PoojaData(
    @SerialName("god_id")
    val godId: Int,

    @SerialName("god")
    val godName: String,

    @SerialName("image_url")
    val imageUrl: String,

    val songs: Array<Song>,
    val themes: Array<Theme>
) {
    val godImage get() = "file:///android_asset/virtualpooja/$imageUrl"
    companion object {
        fun serialize(json: JSONObject): PoojaData? = try {
            Json.decodeFromString<PoojaData>(json.toString())
        } catch (e: Exception) {
            null
        }
    }
}
