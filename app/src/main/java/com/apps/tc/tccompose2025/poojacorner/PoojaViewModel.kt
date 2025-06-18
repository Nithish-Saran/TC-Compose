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

class PoojaViewModel: ViewModel() {
    private val _poojaState = MutableStateFlow<PoojaState>(PoojaState.Loading)
    val poojaState: StateFlow<PoojaState> = _poojaState.asStateFlow()

    fun fetchPoojaData(context: App) {
        _poojaState.value = PoojaState.Loading
        viewModelScope.launch(Dispatchers.IO) {
            val json = loadJsonFromAssets(context, "virtualpooja/virtual_pooja.json")
            val data = JSONArray(json).objectArray()
            val poojaList = data.mapNotNull {
                PoojaData.serialize(it)
            }.toTypedArray()
            _poojaState.value = PoojaState.Success(poojaList)
        }
    }

    fun vibrator(context: App, duration: Long) {
        val vibrator = context.getSystemService(Vibrator::class.java)
        vibrator.let {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                it.vibrate(VibrationEffect.createOneShot(duration, VibrationEffect.DEFAULT_AMPLITUDE))
            }
            else it.vibrate(duration)
        }
    }

    fun toastMsg(context: App) {
        Toast.makeText(context, "Click again", Toast.LENGTH_SHORT).show()
    }
}
