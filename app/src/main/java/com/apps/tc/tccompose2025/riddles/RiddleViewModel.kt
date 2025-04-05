package com.apps.tc.tccompose2025.riddles

import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.apps.tc.tccompose2025.loadJsonFromAssets
import com.apps.tc.tccompose2025.models.RiddlesData
import com.apps.tc.tccompose2025.objectArray
import com.apps.tc.tccompose2025.palankal.PalankalState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlinx.serialization.json.Json
import org.json.JSONArray

class RiddleViewModel: ViewModel() {
    private val _riddlesData = MutableStateFlow<List<RiddlesData>>(emptyList())
    val riddlesData: StateFlow<List<RiddlesData>> = _riddlesData.asStateFlow()

    private val _riddleMenu = MutableStateFlow<RiddlesViewState>(RiddlesViewState.Loading)
    val riddleMenu: StateFlow<RiddlesViewState> = _riddleMenu.asStateFlow()

    fun fetchRiddleMenu(context: Context) {
        _riddleMenu.value = RiddlesViewState.Loading
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val jsonString = loadJsonFromAssets(context, "riddles/riddles.json")
                val riddlesList = JSONArray(jsonString).objectArray().map {
                    RiddlesData.Companion.serialize(it)
                }

                _riddlesData.value = riddlesList as List<RiddlesData>

                val menu = riddlesList.map { data ->
                    Pair(data.imgUrl, data.heading)

                }
                Log.d("WHILOGS", menu.toString())
                withContext(Dispatchers.Main) {
                    _riddleMenu.value = RiddlesViewState.Menu(menu)
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    _riddleMenu.value = RiddlesViewState.Error
                }
            }

        }
    }

//    fun selectRiddle(index: Int) {
//        val selectedRiddleData = _riddlesData.value.getOrNull(index) ?: return
//        _riddleMenu.value = RiddlesViewState.Play(selectedRiddleData)
//    }
//
//    fun backReq(onBackReq: () -> Unit) {
//        if(_riddleMenu.value is RiddlesViewState.Menu) {
//            val data = (_riddleMenu.value as RiddlesViewState.Menu).riddle
//            _riddleMenu.value = RiddlesViewState.Menu(data)
//        }
//        else {
//            onBackReq()
//        }
//    }
}