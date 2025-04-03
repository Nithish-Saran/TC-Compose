package com.apps.tc.tccompose2025.rewind

import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.apps.tc.tccompose2025.loadJsonFromAssets
import com.apps.tc.tccompose2025.models.ParigaramData
import com.apps.tc.tccompose2025.models.RewindData
import com.apps.tc.tccompose2025.objectArray
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import org.json.JSONArray

class RewindViewModel: ViewModel() {
    private val _rewindState = MutableStateFlow<RewindViewState>(RewindViewState.Loading)
    val rewindState: StateFlow<RewindViewState> = _rewindState.asStateFlow()

    fun fetchRewind(context: Context, tab: Int) {
        _rewindState.value = RewindViewState.Loading
        val fileName = when(tab) {
            0 -> "general.json"
            1 -> "india.json"
            2 -> "world.json"
            3 -> "politics.json"
            4 -> "films.json"
            else -> "sports.json"
        }
        viewModelScope.launch(Dispatchers.Main) {
            val jsonString = loadJsonFromAssets(context, "rewind/${fileName}")
            val jsonArray = JSONArray(jsonString)
            val rewindData = jsonArray.objectArray().map {
                RewindData.Companion.serialize(it)
            }
            _rewindState.value = RewindViewState.Success(rewindData as List<RewindData>)
        }
    }
}