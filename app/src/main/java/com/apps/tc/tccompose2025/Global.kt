package com.apps.tc.tccompose2025

import android.app.UiModeManager
import android.content.Context
import android.view.WindowManager
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.enableEdgeToEdge
import androidx.core.content.ContextCompat
import androidx.core.view.WindowCompat
import org.json.JSONArray
import org.json.JSONObject
import java.io.InputStreamReader

enum class Rasi(val value: Int) {
    mesham(0),
    rishabam(1),
    midhunam(2),
    kadagam(3),
    simmam(4),
    kanni(5),
    thulaam(6),
    viruchigam(7),
    thanusu(8),
    magaram(9),
    kumbam(10),
    meenam(11);

    val displayEnName: String
        get() = when (this) {
            mesham -> "mesham"
            rishabam -> "rishabam"
            midhunam -> "midhunam"
            kadagam -> "kadagam"
            simmam -> "simmam"
            kanni -> "kanni"
            thulaam -> "thulaam"
            viruchigam -> "viruchigam"
            thanusu -> "thanusu"
            magaram -> "magaram"
            kumbam -> "kumbam"
            meenam -> "meenam"
        }
    val displayName: String
        get() = when (this) {
            mesham -> "மேஷம்"
            rishabam -> "ரிஷபம்"
            midhunam -> "மிதுனம்"
            kadagam -> "கடகம்"
            simmam -> "சிம்மம்"
            kanni -> "கன்னி"
            thulaam -> "துலாம்"
            viruchigam -> "விருச்சிகம்"
            thanusu -> "தனுசு"
            magaram -> "மகரம்"
            kumbam -> "கும்பம்"
            meenam -> "மீனம்"
        }
    val rasiPair: String
        get() = when (this) {
            mesham -> "மேஷம்/துலாம்"
            rishabam -> "ரிஷபம்/விருச்சிகம்"
            midhunam -> "மிதுனம்/தனுசு"
            kadagam -> "கடகம்/மகரம்"
            simmam -> "சிம்மம்/கும்பம்"
            kanni -> "கன்னி/மீனம்"
            thulaam -> "துலாம்/மேஷம்"
            viruchigam -> "விருச்சிகம்/ரிஷபம்"
            thanusu -> "தனுசு/மிதுனம்"
            magaram -> "மகரம்/கடகம்"
            kumbam -> "கும்பம்/சிம்மம்"
            meenam -> "மீனம்/கன்னி"
        }
//    val displayIconLite: Int
//        get() = when (this) {
//            mesham -> R.drawable.rasi_ic_mesam
//            rishabam -> R.drawable.rasi_ic_risabam
//            midhunam -> R.drawable.rasi_ic_midhunam
//            kadagam -> R.drawable.rasi_ic_kadagam
//            simmam -> R.drawable.rasi_ic_simmam
//            kanni -> R.drawable.rasi_ic_kanni
//            thulaam -> R.drawable.rasi_ic_dhulam
//            viruchigam -> R.drawable.rasi_ic_viruchigam
//            thanusu -> R.drawable.rasi_ic_dhanushu
//            magaram -> R.drawable.rasi_ic_magaram
//            kumbam -> R.drawable.rasi_ic_kumbam
//            meenam -> R.drawable.rasi_ic_meenam
//        }
//
//    val displayIconDark: Int
//        get() = when (this) {
//            mesham -> R.drawable.widget_mesam
//            rishabam -> R.drawable.widget_risabam
//            midhunam -> R.drawable.widget_midhunam
//            kadagam -> R.drawable.widget_kadagam
//            simmam -> R.drawable.widget_simmam
//            kanni -> R.drawable.widget_kanni
//            thulaam -> R.drawable.widget_dhulam
//            viruchigam -> R.drawable.widget_viruchigam
//            thanusu -> R.drawable.widget_dhanushu
//            magaram -> R.drawable.widget_magaram
//            kumbam -> R.drawable.widget_kumbam
//            meenam -> R.drawable.widget_meenam
//        }

    companion object {
        fun getAllRasis() = arrayOf(
            mesham, rishabam, midhunam, kadagam, simmam, kanni, thulaam,
            viruchigam, thanusu, magaram, kumbam, meenam
        )

        private val values = Rasi.values()
        fun of(value: Int) = values.firstOrNull { it.value == value }
    }
}

fun JSONArray.objectArray(): Array<JSONObject> {
    val objects = mutableListOf<JSONObject>()
    repeat(this.length()) { objects.add(this.getJSONObject(it)) }
    return objects.toTypedArray()
}

fun JSONObject.objectArray(arg: String): Array<JSONObject> {
    val objects = mutableListOf<JSONObject>()
    val jsonArray = this.getJSONArray(arg)
    repeat(jsonArray.length()) { objects.add(jsonArray.getJSONObject(it)) }
    return objects.toTypedArray()
}

fun loadJsonFromAssets(context: Context, fileName: String): String {
    return context.assets.open(fileName).use { inputStream ->
        InputStreamReader(inputStream).readText()
    }
}

fun ComponentActivity.setStatusBarColor(color: Int) {
    val windowInsetsController = WindowCompat.getInsetsController(window, window.decorView)
    val statusBarColor = ContextCompat.getColor(this, color)
    val whiteColor = ContextCompat.getColor(this, R.color.white)
    val blackColor = ContextCompat.getColor(this, R.color.black)

    // Ensure the window is prepared for system bar background changes
    window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
    window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)

    // Detect dark mode state
    val isDarkMode = (getSystemService(Context.UI_MODE_SERVICE) as UiModeManager).nightMode == UiModeManager.MODE_NIGHT_YES

    // Apply edge-to-edge configuration
    enableEdgeToEdge(
        statusBarStyle = if (isDarkMode) SystemBarStyle.dark(statusBarColor) else SystemBarStyle.light(statusBarColor, statusBarColor),
        navigationBarStyle = if (isDarkMode) SystemBarStyle.dark(blackColor) else SystemBarStyle.light(whiteColor, whiteColor)
    )

    // Apply light icons on the status bar
    windowInsetsController.isAppearanceLightStatusBars = false
}