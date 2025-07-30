package com.apps.tc.tccompose2025.poojacorner

import com.apps.tc.tccompose2025.models.VirtualPoojaData

sealed class PoojaState {
    object Loading : PoojaState()

    data class ShowPoojaItems(
        val poojaData: VirtualPoojaData,
    ) : PoojaState()

    data class Error(val message: String) : PoojaState()
}
