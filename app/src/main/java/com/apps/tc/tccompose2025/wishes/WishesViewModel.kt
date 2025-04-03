package com.apps.tc.tccompose2025.wishes

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

class WishesViewModel : ViewModel() {
    private val _wishesState = MutableStateFlow<WishesState>(WishesState.Loading)
    val wishesState: StateFlow<WishesState> = _wishesState.asStateFlow()

    val wishList = mutableListOf<Pair<String, Array<String>>>()
    val wishMenu = mutableListOf<Pair<String, String>>()

    fun fetchWishesList(context: Context) {
        _wishesState.value = WishesState.Loading
        viewModelScope.launch(Dispatchers.Main) {
            val data = loadJsonFromAssets(context, "wishes.json")
            val json = JSONArray(data)
            if (json.length() > 0) {
//                val wishes = json.objectArray().map {
//                    Pair(it.getString("title"), it.getString("ic"))
//                }.toTypedArray()
                json.objectArray().map {
                    wishMenu.add(Pair(it.getString("title"), it.getString("ic")))
                    wishList.add(Pair(it.getString("title"), it.stringArray("items")))
                }
                _wishesState.value = WishesState.showMenu(wishMenu.toTypedArray())
            }
            else {
                _wishesState.value = WishesState.Empty
            }
        }
    }

    fun fetchWishes(index: Int) {
        _wishesState.value = WishesState.showList(wishList[index])
    }

    fun backReq(onBackReq: () -> Unit) {
        if(_wishesState.value is WishesState.showList) {
            _wishesState.value = WishesState.showMenu(wishMenu.toTypedArray())
        }
        else {
            onBackReq()
        }
    }
}