package com.apps.tc.tccompose2025.models

import com.apps.tc.tccompose2025.log
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import org.json.JSONObject

@Serializable
data class StickersData(
    val identifier: String,
    val name: String,
    val publisher: String,
    @SerialName("tray_image_file") val imgUrl: String,
    @SerialName("folder_name") val folderName: String,
    val size: String
) {

    val folder get() = folderName.replace("_", " ")
    companion object {
        fun serialize(json: JSONObject): StickersData? = try {
            Json.decodeFromString<StickersData>(json.toString())
        } catch (e: Exception) {
            log(e)
            null
        }
    }
}