package com.apps.tc.tccompose2025.planetory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.apps.tc.tccompose2025.App
import com.apps.tc.tccompose2025.loadJsonFromAssets
import com.apps.tc.tccompose2025.models.PlanetoryData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import org.json.JSONArray

class PlanetoryViewModel: ViewModel()  {
    private val _planetoryState = MutableStateFlow<PlanetoryViewState>(PlanetoryViewState.Loading)
    val planetoryState: StateFlow<PlanetoryViewState> = _planetoryState.asStateFlow()

    var data = mutableListOf<PlanetoryData>()

    fun fetchPlanetoryData(app: App) {
        _planetoryState.value = PlanetoryViewState.Loading
        viewModelScope.launch(Dispatchers.IO) {
            val json = loadJsonFromAssets(app, "planitory-positions.json")
            repeat(JSONArray(json).length()) { index ->
                PlanetoryData.serialize(JSONArray(json).getJSONObject(index))?.let { data.add(it) }
            }
            with(Dispatchers.Main) {
                _planetoryState.value = PlanetoryViewState.Success(data)
            }
        }
    }

    fun totalDegrees(diffDays: Int, totalDays: Int): Float {
        return if (diffDays > totalDays) {
            (diffDays % totalDays).toFloat() / totalDays * 360f
        } else {
            (diffDays.toFloat() / totalDays * 360f) * 10
        }
    }
}