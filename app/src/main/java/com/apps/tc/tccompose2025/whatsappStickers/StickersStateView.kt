package com.apps.tc.tccompose2025.whatsappStickers

import com.apps.tc.tccompose2025.models.StickersData

sealed class StickersStateView {
    data object Loading: StickersStateView()
    data class Success(val stickers: List<StickersData> ): StickersStateView()
    data class WebMode(val url: String, val stickers: List<StickersData>): StickersStateView()
    data object Error: StickersStateView()
}