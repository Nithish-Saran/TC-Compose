package com.apps.tc.tccompose2025.pranayamam

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.apps.tc.tccompose2025.App
import com.apps.tc.tccompose2025.loadJsonFromAssets
import com.apps.tc.tccompose2025.models.PranayamamInfo
import com.apps.tc.tccompose2025.objectArray
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.json.JSONArray

class PranayamamViewModel: ViewModel() {
    private val _pranayamamState = MutableStateFlow<PranayamamViewState>(PranayamamViewState.Loading)
    val pranayamamState: StateFlow<PranayamamViewState> = _pranayamamState.asStateFlow()

    fun getPranayamamMenu(app: App) {
        viewModelScope.launch(Dispatchers.IO) {
            _pranayamamState.value = PranayamamViewState.Loading
            val menu = arrayOf(
                Pair("pranayamam/ic_pranayamam_body", "பிராணாயாமம் அறிந்தும் அறியாததும்"),
                Pair("pranayamam/ic_pranayamam_start", "ஆரம்ப நிலை பிராணாயாமம்"),
                Pair("pranayamam/ic_pranayamam_benefits", "பிராணாயாமத்தின் பலன்கள்"),
                Pair("pranayamam/ic_pranayamam_mind_peace", "மனம் அமைதி பெற"),
                Pair("pranayamam/ic_pranayamam_memory_power", "ஞாபக சக்தி அதிகரிக்க"),
                Pair("pranayamam/ic_pranayamam_cold", "சளி, ஆஸ்துமா பிரச்சனைகளுக்கு"),
                Pair("pranayamam/ic_pranayamam_weight_loss", "தொப்பை மற்றும் உடல் எடை குறைய"),
                Pair("pranayamam/ic_pranayamam_heat_tolow", "உடல் உஷ்ணம் குறைய"),
                Pair("pranayamam/ic_pranayamam_heat_tohigh", "உடல் உஷ்ணம் அதிகரிக்க"),
                Pair("pranayamam/ic_pranayamam_shining_face", "முகம் பொலிவு பெற"),
                Pair("pranayamam/ic_pranayamam_body", "உடல் நச்சுக்கள் வெளியேற"),
                Pair("pranayamam/ic_pranayamam_burning", "நெஞ்சு எரிச்சல் நீங்க"),
                Pair("pranayamam/ic_pranayamam_thyroid", "சுரப்பிகள் சீராக"),
                Pair("pranayamam/ic_pranayamam_blood", "ரத்தம் சுத்தம் அடைய")
            )
            withContext(Dispatchers.Main) {
                _pranayamamState.value = PranayamamViewState.Menu(menu)
            }
        }
    }

    fun getPranayamamData(app: App) {
        viewModelScope.launch(Dispatchers.Main) {
            _pranayamamState.value = PranayamamViewState.Loading
            val data = loadJsonFromAssets(app, "pranayamam/pranayamam_data.json")
            val json = JSONArray(data)
            val pranayamam = json.objectArray().map {
                PranayamamInfo.serialize(it)
            }.toTypedArray()
        }
    }
}