package com.apps.tc.tccompose2025.parigaram

import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.apps.tc.tccompose2025.loadJsonFromAssets
import com.apps.tc.tccompose2025.models.ParigaramData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.json.JSONArray

class ParigaramViewModel: ViewModel() {
    private val _parigaramState = MutableStateFlow<ParigaramState>(ParigaramState.Loading)
    val parigaramState: StateFlow<ParigaramState> = _parigaramState.asStateFlow()

    fun fetchParigaram(context: Context) {
        _parigaramState.value = ParigaramState.Loading
        viewModelScope.launch(Dispatchers.IO) {
            val jsonString = loadJsonFromAssets(context, "parigaram-articles.json")
            val jsonArray = JSONArray(jsonString)
            Log.d("WHILOGS", jsonArray.toString())
            val parigaramList = (0 until jsonArray.length()).mapNotNull { index ->
                val jsonObject = jsonArray.getJSONObject(index)
                ParigaramData.Companion.serialize(jsonObject)
            }
            Log.d("WHILOGS", parigaramList.toString())
            withContext(Dispatchers.Main) {
                _parigaramState.value = ParigaramState.ParigaramList(parigaramList)
            }
        }
    }
}