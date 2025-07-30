package com.apps.tc.tccompose2025.models

import com.apps.tc.tccompose2025.logE
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import org.json.JSONObject

@Serializable
data class FastingWidgetData(
    val kiruthigai: String,
    val sivarathiri: String,
    val ammavasi: String,
    val pournami: String,
    val sathurthi: String,
    val sSathurthi: String,
    val egathasi: String,
    val shasti: String,
    val piradosham: String,
    val taYear: String,
) {
}

