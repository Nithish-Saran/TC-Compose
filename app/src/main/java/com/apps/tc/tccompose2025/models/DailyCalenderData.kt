package com.apps.tc.tccompose2025.models

import com.apps.tc.tccompose2025.logE
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import org.json.JSONObject

@Serializable
data class DailyCalendarData(
    val date: Int,
    val taDate: Int,
    val month: Int,
    val taMonth: Int,
    val year: Int,
    val taYear: String,
    val valarpirai: Boolean,
    val yogam: String,
    val sooranam: Time,
    val sunrise: String,
    val star: String,
    val nallaNeram: List<Time>,
    val muMonthName: String,
    val proverbIndex: Int,
    val visaesam: String,
    val muDateIndex: Int,
    val ragu: Time, // we have morning and evening data so how to show check list of item or single
    val soolam: String,
    val kuligai: Time, // we have morning and evening data so how to show check list of item or single
    val gowriNallaNeram: List<Time>,
    val rasi: List<String>,
    val chandrashtamam: String,
    val chandrashtamamRasi: String,
    val daySpecial: String,
    val parigaram: String,
    val yamagandam: Time, // we have morning and evening data so how to show check list of item or single
    val nokkuNaal: Int,
    val thithi: String,
    val karanamPanjangam: String,
    val namaYogam: String,
    val kiruthigai: Boolean,
    val kariNaal: Boolean,
    val sivarathiri: Boolean,
    val ammavasai: Boolean,
    val pournami: Boolean,
    val sathurthi: Boolean,
    val sSathurthi: Boolean,
    val egathasi: Boolean,
    val shasti: Boolean,
    val piradosham: Boolean,

    // Make nullable and optional
    val marriage: MarriageDay? = null,
    val guvHoliday: GuvHoliday? = null,
    val ashtami: Celeb? = null,
    val navami: Celeb? = null,
    val vaasthu: Celeb? = null,
    val hindu: Celeb? = null,
    val christ: Celeb? = null,
    val muslim: Celeb? = null,
    val leaders: Celeb? = null,
) {
    companion object {
        fun serialize(json: JSONObject): DailyCalendarData? = try {
            Json.decodeFromString<DailyCalendarData>(json.toString())
        } catch(e: Exception) {
            logE(e, "")
            null
        }
    }
}

@Serializable
data class Time(
    val msm: Int,   // start time in minutes since midnight
    val dur: Int    // duration in minutes
)

@Serializable
data class GuvHoliday(
    val date: Int,
    val name: String,
    val desc: String,
    val icon: String
)


@Serializable
data class MarriageDay(
    val date: Int,
    val time: Time,
    val yogam: String,
    val star: String,
    val thithi: String,
    val lagnam: String
)

@Serializable
data class Celeb(
    val date: Int,
    val name: String,
    val desc: String,
    val icon: String
)

