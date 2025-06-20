package com.apps.tc.tccompose2025.poojacorner

import android.os.Build
import android.os.VibrationEffect
import android.os.Vibrator
import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.apps.tc.tccompose2025.App
import com.apps.tc.tccompose2025.loadJsonFromAssets
import com.apps.tc.tccompose2025.models.PoojaData
import com.apps.tc.tccompose2025.objectArray
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import org.json.JSONArray
import org.json.JSONObject

class PoojaViewModel : ViewModel() {
    private val _poojaState = MutableStateFlow<PoojaState>(PoojaState.Loading)
    val poojaState: StateFlow<PoojaState> = _poojaState.asStateFlow()

    var poojaData: PoojaData? = null
    var selectedGod = 0
    var selectedTheme = 0
    var selectedFlower = 0

    fun fetchPoojaData(context: App) {
        _poojaState.value = PoojaState.Loading
        viewModelScope.launch(Dispatchers.IO) {
            val json = loadJsonFromAssets(context, "poojacorner/virtual_pooja.json")
            val data = JSONObject(json)
            poojaData = PoojaData.serialize(data)
            updateTheme()
        }
    }

    fun vibrator(context: App, duration: Long) {
        val vibrator = context.getSystemService(Vibrator::class.java)
        vibrator.let {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                it.vibrate(
                    VibrationEffect.createOneShot(
                        duration,
                        VibrationEffect.DEFAULT_AMPLITUDE
                    )
                )
            } else it.vibrate(duration)
        }
    }

    fun updateTheme() {
        poojaData?.let {
            if (it.themes.isNotEmpty() && it.gods.isNotEmpty() && it.flowers.isNotEmpty())
                _poojaState.value = PoojaState.ShowPoojaItems(
                    poojaData = it,
                    godData = it.gods[selectedGod],
                    themes = it.themes[selectedTheme],
                    flowers = it.flowers[selectedFlower]
                )
        }
    }
}
