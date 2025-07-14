package com.apps.tc.tccompose2025.models

import com.apps.tc.tccompose2025.poojacorner.assetPath
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import org.json.JSONObject

@Serializable
data class PoojaData(
    val gods: Array<God>,
    val themes: Array<Theme>,
    val flowers: Array<PoojaService>
) {
    companion object {
        fun serialize(json: JSONObject): PoojaData? = try {
            Json.decodeFromString<PoojaData>(json.toString())
        } catch (e: Exception) {
            null
        }
    }
}

@Serializable
data class PoojaService(
    val name: String,
    @SerialName("image") val imageUrl: String
) {
    val serviceIcon get() = assetPath(imageUrl)
}

@Serializable
data class Theme(
    @SerialName("pooja_bg") val poojaBg: String,
    @SerialName("god_bg") val godBg: String,
    val plate: String,
    val bell: String,
    val hybiscus: String,
    val samanthi: String,
    val rose: String,
    val sevvanthi: String,
    val lotus: String,
    val incense: String,
    val coconut: String,
    val theme: String,
    val song: String,
    val lamp: String,
    val vinayagar: String,
    val murugan: String,
    val hanuman: String,
    val saibaba: String,
    val lakshmi: String,
    val saraswathi: String,
    val iyappan: String,
    val durga: String,
    val perumal: String,
    val sivan: String,
    val stop: String,
) {
    val poojaBgPath get() = assetPath(poojaBg)
    val godBgPath get() = assetPath(godBg)
    val platePath get() = assetPath(plate)
    val bellPath get() = assetPath(bell)
    val hybiscusPath get() = assetPath(hybiscus)
    val samanthiPath get() = assetPath(samanthi)
    val rosePath get() = assetPath(rose)
    val sevvanthiPath get() = assetPath(sevvanthi)
    val lotusPath get() = assetPath(lotus)
    val incensePath get() = assetPath(incense)
    val coconutPath get() = assetPath(coconut)
    val themePath get() = assetPath(theme)
    val musicPath get() = assetPath(song)
    val lampPath get() = assetPath(lamp)
    val vinayagarPath get() = assetPath(vinayagar)
    val muruganPath get() = assetPath(murugan)
    val hanumanPath get() = assetPath(hanuman)
    val saibabaPath get() = assetPath(saibaba)
    val lakshmiPath get() = assetPath(lakshmi)
    val saraswathiPath get() = assetPath(saraswathi)
    val durgaPath get() = assetPath(durga)
    val iyappanPath get() = assetPath(iyappan)
    val perumalPath get() = assetPath(perumal)
    val sivanPath get() = assetPath(sivan)
    val stopPath get() = assetPath(stop)
}


@Serializable
data class God(
    @SerialName("god_id") val godId: Int,
    @SerialName("god") val godName: String,
    @SerialName("image_url") val imageUrl: String,
    val songs: Array<PoojaService>,
) {
    val godImage get() = assetPath(imageUrl)
}

