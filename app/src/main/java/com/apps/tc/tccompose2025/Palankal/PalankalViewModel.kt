package com.apps.tc.tccompose2025.Palankal

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.apps.tc.tccompose2025.loadJsonFromAssets
import com.apps.tc.tccompose2025.objectArray
import com.apps.tc.tccompose2025.stringArray
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import org.json.JSONArray

class PalankalViewModel: ViewModel() {
    private val _palankalState = MutableStateFlow<PalankalState>(PalankalState.Loading)
    val palankalState: StateFlow<PalankalState> = _palankalState.asStateFlow()

    fun fetchPalankal(context: Context, mode: Int) {
        _palankalState.value = PalankalState.Loading
        viewModelScope.launch(Dispatchers.Main) {
            val fileName = when(mode) {
                0 -> "macham-palankal.json"
                1 -> "kanavu-palankal.json"
                else -> "proverbs.json"
            }
            val data = loadJsonFromAssets(context, fileName)
            val jsonArray = JSONArray(data)
            if (jsonArray.length() > 0) {
                val palankalList = jsonArray.objectArray().map {
                    Pair(it.getString("title"), it.stringArray("items"))
                }.toTypedArray()

                _palankalState.value = PalankalState.Success(palankalList)
            } else {
                _palankalState.value = PalankalState.Empty
            }
        }
    }

    fun fetchPalankalList(dataList: Array<Pair<String, Array<String>>>, index: Int) {
        _palankalState.value = PalankalState.ShowDetail(palankalData = dataList, palankalList = dataList[index])
    }

    fun backReq(onBackReq: () -> Unit) {
        if(_palankalState.value is PalankalState.ShowDetail) {
            val data = (_palankalState.value as PalankalState.ShowDetail).palankalData
            _palankalState.value = PalankalState.Success(data)
        }
        else {
            onBackReq()
        }
    }
}