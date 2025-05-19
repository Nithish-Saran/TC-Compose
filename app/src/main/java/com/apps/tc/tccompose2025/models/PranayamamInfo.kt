package com.apps.tc.tccompose2025.models

import kotlinx.serialization.json.Json
import org.json.JSONObject

data class PranayamamInfo(
    val id: Int,
    val title: String,
    val youtubeLink: String,
    val inhaleBeginner: Int,
    val inhaleIntermediate: Int,
    val inhaleExpert: Int,
    val inhaleRatio: Int,
    val exhaleRatio: Int,
    val holdRatio: Int,
    val retainRatio: Int,
    val breathingCycle: Int,
    val caution: String,
    val benefits: List<String>,
    val prerequisite: List<String>,
    val bestPractices: List<String>,
    val procedure: List<String>
) {
    companion object {
        fun serialize(json: JSONObject): PranayamamInfo? = try {
            Json.decodeFromString<PranayamamInfo>(json.toString())
        } catch (e: Exception) {
            null
        }
    }
}

