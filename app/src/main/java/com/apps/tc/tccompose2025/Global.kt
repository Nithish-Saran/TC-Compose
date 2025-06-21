package com.apps.tc.tccompose2025

import android.app.UiModeManager
import android.content.Context
import android.util.Log
import android.view.WindowManager
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.enableEdgeToEdge
import androidx.core.content.ContextCompat
import androidx.core.view.WindowCompat
import org.json.JSONArray
import org.json.JSONObject
import java.io.InputStreamReader
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.concurrent.TimeUnit

const val AppPref = "whiture.reader.pref"
const val PrefNotes = "PrefNotes"
const val WHILOGS = "WHILOGS"

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

    companion object {
        fun getAllRasis() = arrayOf(
            mesham, rishabam, midhunam, kadagam, simmam, kanni, thulaam,
            viruchigam, thanusu, magaram, kumbam, meenam
        )

        private val values = Rasi.values()
        fun of(value: Int) = values.firstOrNull { it.value == value }
    }
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


/**
 * JSONObject related util methods
 */
fun JSONObject.nullInt(key: String): Int? =
    if (this.has(key) && !this.isNull(key)) this.getInt(key) else null
fun JSONObject.nullDouble(key: String): Double? =
    if (this.has(key) && !this.isNull(key)) this.getDouble(key) else null
fun JSONObject.nullString(key: String): String? =
    if (this.has(key) && !this.isNull(key)) this.getString(key) else null
fun JSONObject.nullBool(key: String): Boolean? =
    if (this.has(key) && !this.isNull(key)) this.getBoolean(key) else null
fun JSONObject.nullObj(key: String): JSONObject? =
    if (this.has(key) && !this.isNull(key)) this.getJSONObject(key) else null
fun JSONObject.nullArray(key: String): JSONArray? =
    if (this.has(key) && !this.isNull(key)) this.getJSONArray(key) else null

fun JSONArray.stringArray() = (0 until this.length()).map { this.getString(it) }.toTypedArray()
fun JSONArray.intArray() = (0 until this.length()).map { this.getInt(it) }.toTypedArray()
fun JSONArray.objectArray(): Array<JSONObject> = (0 until this.length()).map {
    this.getJSONObject(it)
}.toTypedArray()

fun JSONArray.jsonArrayArray(): Array<JSONArray> = (0 until this.length()).map {
    this.optJSONArray(it)
}.toTypedArray()

fun JSONArray.longArray() = (0 until this.length()).map { this.getLong(it) }.toTypedArray()

fun JSONObject.intArray(arg: String): Array<Int> = try {
    with(getJSONArray(arg)) { intArray() }
} catch (e: Exception) {
    emptyArray()
}

fun JSONObject.stringArray(arg: String): Array<String> = try {
    with(getJSONArray(arg)) { stringArray() }
} catch (e: Exception) {
    emptyArray()
}

fun JSONObject.objectArray(arg: String): Array<JSONObject> = try {
    with(getJSONArray(arg)) { objectArray() }
} catch (e: Exception) {
    emptyArray()
}

fun JSONObject.longArray(arg: String): Array<Long> = try {
    with(getJSONArray(arg)) { longArray() }
} catch (e: Exception) {
    emptyArray()
}

fun log(message: Any) {
    Log.d(WHILOGS, message.toString())
}

fun logE(e: Throwable?, message: String) {
    Log.e(WHILOGS, message, e)
}

enum class WebScreenMode(val value: Int) {
    Internal(0),
    External(1),
    Assets(2);
}

val Int.webScreenMode: WebScreenMode get() {
    return when (this) {
        0 -> WebScreenMode.Internal
        1 -> WebScreenMode.External
        else -> WebScreenMode.Assets
    }
}

val Date.displayStringWithSlash: String
    get() = SimpleDateFormat("dd/MM/yyyy").format(this)

fun showToast(context: App, msg: String) {
    Toast.makeText(context, msg, Toast.LENGTH_SHORT).show()
}

fun dateFromString(value: String, format: String): Date? =
    runCatching {
        SimpleDateFormat(format, Locale.getDefault()).parse(value)
    }.getOrNull()


// method to find out dates between these two
fun Date.daysBetween(date: Date): Int = TimeUnit.DAYS.convert(
    this.time - date.time,
    TimeUnit.MILLISECONDS
).toInt()

fun Date.totalDays(dateString: String): Int {
    dateFromString(dateString, "dd/MM/yyyy")?.let {
        return this.daysBetween(it)
    }
    return 1
}

