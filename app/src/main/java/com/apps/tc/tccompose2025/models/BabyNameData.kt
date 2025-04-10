package com.apps.tc.tccompose2025.models

import com.apps.tc.tccompose2025.logE
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import org.json.JSONObject

@Serializable
data class BabyNameData(
    val id: Int,
    val title: String,
    val names: Array<String>
) {
    val imgUrl get() = "file:///android_asset/babyName/ic_${id}.png"
    val imgDefault get() = "file:///android_asset/babyName/ic_0.png"
    val starTitle get() = "நட்சத்திர பெயர்கள்"

    companion object {
        fun serialize(json: JSONObject): BabyNameData? = try {
            Json.decodeFromString<BabyNameData>(json.toString())
        } catch(e: Exception) {
            logE(e, "")
            null
        }
    }
}