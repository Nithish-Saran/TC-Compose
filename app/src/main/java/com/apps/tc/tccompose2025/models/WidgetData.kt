package com.apps.tc.tccompose2025.models

import com.apps.tc.tccompose2025.DayImportance
import com.apps.tc.tccompose2025.R
import com.apps.tc.tccompose2025.formatNallaNeram
import com.apps.tc.tccompose2025.getEnMonthName
import com.apps.tc.tccompose2025.getTamilMonthName
import com.apps.tc.tccompose2025.logE
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import org.json.JSONObject

@Serializable
data class WidgetData(
    val date: Int,
    val taDate: Int,
    val month: Int,
    val taMonth: Int,
    val taYear: String,
    val valarpirai: Boolean,
    val nallaNeram: List<Time>,
    val rasi: List<String>,
    val nokkuNaal: Int,
    val yamagandam: Time,
    val kiruthigai: Boolean,
    val sivarathiri: Boolean,
    val ammavasai: Boolean,
    val pournami: Boolean,
    val sathurthi: Boolean,
    val sSathurthi: Boolean,
    val egathasi: Boolean,
    val shasti: Boolean,
    val piradosham: Boolean,
) {
    companion object {
        private val jsonParser = Json {
            ignoreUnknownKeys = true  // <-- This allows skipping fields like "year"
        }

        fun serialize(json: JSONObject): WidgetData? = try {
            jsonParser.decodeFromString<WidgetData>(json.toString())
        } catch (e: Exception) {
            logE(e, "WidgetData serialization failed")
            null
        }
    }

    val emagandam get() = formatNallaNeram(yamagandam.msm, yamagandam.dur)
    val monthEn get() = getEnMonthName(month - 1)
    val monthTa get() = getTamilMonthName(taMonth - 1)
    val nokkuImg
        get() = when (nokkuNaal) {
            0 -> R.drawable.widget_kel
            1 -> R.drawable.widget_mel
            else -> R.drawable.widget_sama
        }
    val nokkuName
        get() = when (nokkuNaal) {
            0 -> "கீழ்நோக்கு"
            1 -> "மேல்நோக்கு"
            else -> "சமநோக்கு"
        }

    val piraiImg get() = if (valarpirai) R.drawable.widget_valarpirai else R.drawable.widget_theipirai
    val piraiName: String
        get() = if (valarpirai) "வளர்பிறை" else "வளர்பிறை"

    val importantDay: DayImportance?
        get() = when (true) {
            kiruthigai -> DayImportance.kiruthigai
            piradosham -> DayImportance.pradosham
            ammavasai -> DayImportance.ammavasai
            pournami -> DayImportance.pournami
            sathurthi -> DayImportance.chaturthi
            sSathurthi -> DayImportance.sangadaSathurthi
            egathasi -> DayImportance.yekathasi
            shasti -> DayImportance.sasti
            sivarathiri -> DayImportance.shivaRathitri
            else -> null
        }
}
