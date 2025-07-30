package com.apps.tc.tccompose2025

import android.app.UiModeManager
import android.content.Context
import android.content.Intent
import android.net.Uri
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
import java.util.Calendar
import java.util.Date
import java.util.Locale
import java.util.concurrent.TimeUnit
import androidx.core.net.toUri

const val AppPref = "whiture.reader.pref"
const val PrefNotes = "PrefNotes"
const val WHILOGS = "WHILOGS"
const val WidgetPref = "widget_pref"
const val RasiPref = "rasi_pref"


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

val Date.currentMonth: Int
    get() {
        val calendar = Calendar.getInstance()
        calendar.time = this
        return calendar.get(Calendar.MONTH)
    }

val Date.currentYear: Int
    get() {
        val calendar = Calendar.getInstance()
        calendar.time = this
        return calendar.get(Calendar.YEAR)
    }

// get the day of the year 1-365
val Date.dayOfYear: Int
    get() {
        val calendar = Calendar.getInstance()
        calendar.time = this
        return calendar.get(Calendar.DAY_OF_YEAR)
    }
// get the day of the year 1-31
val Date.dateOfMonth: Int
    get() {
        val calendar = Calendar.getInstance()
        calendar.time = this
        return calendar.get(Calendar.DATE)
    }

fun getWeekDay(day: Int, isEn: Boolean = false, isShorter: Boolean = false): String = when (day) {
    2 -> if (isEn) if (isShorter) "Mon" else "Monday" else if (isShorter) "திங்கள்" else "திங்கட்கிழமை"
    3 -> if (isEn) if (isShorter) "Tue" else "Tuesday" else if (isShorter) "செவ்வாய்" else "செவ்வாய்க்கிழமை"
    4 -> if (isEn) if (isShorter) "Wed" else "Wednesday" else if (isShorter) "புதன்" else "புதன்கிழமை"
    5 -> if (isEn) if (isShorter) "Thu" else "Thursday" else if (isShorter) "வியாழன்" else "வியாழக்கிழமை"
    6 -> if (isEn) if (isShorter) "Fri" else "Friday" else if (isShorter) "வெள்ளி" else "வெள்ளிக்கிழமை"
    7 -> if (isEn) if (isShorter) "Sat" else "Saturday" else if (isShorter) "சனி" else "சனிக்கிழமை"
    else -> if (isEn) if (isShorter) "Sun" else "Sunday" else if (isShorter) "ஞாயிறு" else "ஞாயிற்றுக்கிழமை"
}

fun getEnMonthName(month: Int, isEn: Boolean = false): String = if (isEn) arrayOf(
    "January", "February", "March", "April", "May", "June", "July", "August", "September",
    "October", "November", "December"
)[month] else arrayOf(
    "ஜனவரி",
    "பிப்ரவரி",
    "மார்ச்",
    "ஏப்ரல்",
    "மே",
    "ஜூன்",
    "ஜூலை",
    "ஆகஸ்ட்",
    "செப்டம்பர்",
    "அக்டோபர்",
    "நவம்பர்",
    "டிசம்பர்"
)[month]

fun getTamilMonthName(month: Int): String = arrayOf(
    "சித்திரை",
    "வைகாசி",
    "ஆனி",
    "ஆடி",
    "ஆவணி",
    "புரட்டாசி",
    "ஐப்பசி",
    "கார்த்திகை",
    "மார்கழி",
    "தை",
    "மாசி",
    "பங்குனி"
)[month]

val Date.monthTa: String
    get() {
        val calendar = Calendar.getInstance()
        calendar.time = this
        return getEnMonthName(month = calendar.get(Calendar.MONTH))
    }

val Date.monthEn: String
    get() {
        val calendar = Calendar.getInstance()
        calendar.time = this
        return getEnMonthName(
            month = calendar.get(Calendar.MONTH),
            isEn = true
        )
    }

val Date.dayTa: String
    get() {
        val calendar = Calendar.getInstance()
        calendar.time = this
        return getWeekDay(day = calendar.get(Calendar.DAY_OF_WEEK), isEn = false, isShorter = true)
    }

val Date.dayTaLengthier: String
    get() {
        val calendar = Calendar.getInstance()
        calendar.time = this
        return getWeekDay(day = calendar.get(Calendar.DAY_OF_WEEK), isEn = false, isShorter = false)
    }

val Date.dayEn: String
    get() {
        val calendar = Calendar.getInstance()
        calendar.time = this
        return getWeekDay(day = calendar.get(Calendar.DAY_OF_WEEK), isEn = true, isShorter = false)
    }

val Date.dayEnShort: String
    get() {
        val calendar = Calendar.getInstance()
        calendar.time = this
        return getWeekDay(day = calendar.get(Calendar.DAY_OF_WEEK), isEn = true, isShorter = true)
    }

fun calculateInitialDelay(): Long {
    val now = Calendar.getInstance()
    val targetTime = Calendar.getInstance().apply {
        set(Calendar.HOUR_OF_DAY, 0)
        set(Calendar.MINUTE, 0)
        set(Calendar.SECOND, 0)
        set(Calendar.MILLISECOND, 0)
        if (before(now)) {
            add(Calendar.DAY_OF_MONTH, 1)
        }
    }
    return targetTime.timeInMillis - now.timeInMillis
}

fun formatNallaNeram(msm: Int, duration: Int): String {
    // Convert minutes since midnight to start time
    val startHour = msm / 60
    val startMinute = msm % 60

    // Calculate end time
    val endTotalMinutes = msm + duration
    val endHour = endTotalMinutes / 60
    val endMinute = endTotalMinutes % 60

    // Format as 12-hour time (AM/PM)
    val startTime = formatTo12Hour(startHour, startMinute)
    val endTime = formatTo12Hour(endHour, endMinute)

    return "$startTime - $endTime"
}

fun formatTo12Hour(hour24: Int, minute: Int): String {
    val amPm = if (hour24 >= 12) "PM" else "AM"
    val hour12 = when {
        hour24 == 0 -> 12
        hour24 > 12 -> hour24 - 12
        else -> hour24
    }
    val minuteStr = minute.toString().padStart(2, '0')
    return "$hour12:$minuteStr $amPm"
}

enum class DayImportance {
    ammavasai,
    astami,
    chaturthi,
    kiruthigai,
    navami,
    pournami,
    pradosham,
    sangadaSathurthi,
    sasti,
    shivaRathitri,
    yekathasi,
    muhurtham,
    valarPirai,
    theiPirai;

    fun getImage(): Int = when(this) {
        ammavasai -> R.drawable.widget_ammavasai
        astami -> R.drawable.widget_astami
        chaturthi -> R.drawable.widget_chaturthi
        kiruthigai -> R.drawable.widget_kiruthikai
        navami -> R.drawable.widget_navami
        pournami -> R.drawable.widget_pournami
        pradosham -> R.drawable.widget_prathosam
        sangadaSathurthi -> R.drawable.widget_sangada_sathurthi
        sasti -> R.drawable.widget_sasti
        shivaRathitri -> R.drawable.widget_shiva_rathri
        yekathasi -> R.drawable.widget_yekathasi
        muhurtham -> R.drawable.month_muhurtham
        valarPirai -> R.drawable.widget_valarpirai
        theiPirai -> R.drawable.widget_theipirai
    }
}

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
    val displayIconLite: Int
        get() = when (this) {
            mesham -> R.drawable.rasi_ic_mesam
            rishabam -> R.drawable.rasi_ic_risabam
            midhunam -> R.drawable.rasi_ic_midhunam
            kadagam -> R.drawable.rasi_ic_kadagam
            simmam -> R.drawable.rasi_ic_simmam
            kanni -> R.drawable.rasi_ic_kanni
            thulaam -> R.drawable.rasi_ic_dhulam
            viruchigam -> R.drawable.rasi_ic_viruchigam
            thanusu -> R.drawable.rasi_ic_dhanushu
            magaram -> R.drawable.rasi_ic_magaram
            kumbam -> R.drawable.rasi_ic_kumbam
            meenam -> R.drawable.rasi_ic_meenam
        }

    val displayIconDark: Int
        get() = when (this) {
            mesham -> R.drawable.widget_mesam
            rishabam -> R.drawable.widget_risabam
            midhunam -> R.drawable.widget_midhunam
            kadagam -> R.drawable.widget_kadagam
            simmam -> R.drawable.widget_simmam
            kanni -> R.drawable.widget_kanni
            thulaam -> R.drawable.widget_dhulam
            viruchigam -> R.drawable.widget_viruchigam
            thanusu -> R.drawable.widget_dhanushu
            magaram -> R.drawable.widget_magaram
            kumbam -> R.drawable.widget_kumbam
            meenam -> R.drawable.widget_meenam
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

fun getWeekDayInTamil(year: Int, month: Int, date: Int): String = Calendar.getInstance().let {
    it.set(Calendar.YEAR, year)
    it.set(Calendar.MONTH, month - 1)
    it.set(Calendar.DATE, date)
    it.time.dayTa
}

fun sendEmail(context: App) {
    val emailIntent = Intent(Intent.ACTION_SEND).apply {
        putExtra(Intent.EXTRA_EMAIL, arrayOf("sudhakar.kanakaraj@outlook.com"))
        putExtra(Intent.EXTRA_SUBJECT, "Nila Tamil Calendar - Meetup")
        putExtra(Intent.EXTRA_TEXT, "Hi,\n\n")
        selector = Intent(Intent.ACTION_SENDTO).apply {
            data = "mailto:".toUri()
        }
    }
    context.startActivity(
        Intent.createChooser(emailIntent, "Send feedback..").apply {
            addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        }
    )
}

fun openMapLocation(context: Context, mapUrl: String) {
    val intent = Intent(Intent.ACTION_VIEW, mapUrl.toUri()).apply {
        setPackage("com.google.android.apps.maps") // Force open in Google Maps app
    }
    try {
        context.startActivity(intent)
    } catch (e: Exception) {
        // Fallback: open in browser if Maps app isn't available
        context.startActivity(Intent(Intent.ACTION_VIEW, mapUrl.toUri()))
    }
}


