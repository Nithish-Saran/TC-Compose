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
    val imgUrl get() = "babyName/ic_${id}"
    companion object {
        fun serialize(json: JSONObject): BabyNameData? = try {
            Json.decodeFromString<BabyNameData>(json.toString())
        } catch(e: Exception) {
            logE(e, "")
            null
        }
    }
}