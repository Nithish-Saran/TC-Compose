package com.apps.tc.tccompose2025.whatsappStickers

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.apps.tc.tccompose2025.ApiRepository
import com.apps.tc.tccompose2025.App
import com.apps.tc.tccompose2025.loadJsonFromAssets
import com.apps.tc.tccompose2025.models.StickersData
import com.apps.tc.tccompose2025.objectArray
import com.apps.tc.tccompose2025.wishes.WishesState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import org.json.JSONObject

class StickersViewModel: ViewModel() {
    private val _stickersState = MutableStateFlow<StickersStateView>(StickersStateView.Loading)
    val stickersState: StateFlow<StickersStateView> = _stickersState.asStateFlow()

    fun loadStickers(app: App) {
        _stickersState.value = StickersStateView.Loading
        viewModelScope.launch(Dispatchers.Main) {
            val json = loadJsonFromAssets(app, "whatsapp/stickerPacks.json")
            val stickerData = JSONObject(json)
            val stickers = stickerData.objectArray("sticker_packs").map {
                StickersData.Companion.serialize(it)
            }
            if (stickers.filterNotNull().isNotEmpty()) {
                _stickersState.value = StickersStateView.Success(stickers as List<StickersData>)
            }
            else _stickersState.value = StickersStateView.Error
        }
    }

    fun downloadSticker(app: App, data: StickersData) {
        viewModelScope.launch(Dispatchers.Main) {
            ApiRepository.downloadStickers(app, data)
        }
    }

    fun instruction(data: List<StickersData>) {
        _stickersState.value = StickersStateView.Loading
        viewModelScope.launch(Dispatchers.IO) {
           val url =  ApiRepository.showInstructions()
            if (url.isNotEmpty()) {
                _stickersState.value = StickersStateView.WebMode(url, data)
            }
            else _stickersState.value = StickersStateView.Error
        }
    }

    fun backReq(onBackReq: () -> Unit) {
        if(_stickersState.value is StickersStateView.WebMode) {
            val data = (_stickersState.value as StickersStateView.WebMode).stickers
            _stickersState.value = StickersStateView.Success(data)
        }
        else {
            onBackReq()
        }
    }
}