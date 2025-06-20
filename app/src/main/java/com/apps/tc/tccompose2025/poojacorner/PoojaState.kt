package com.apps.tc.tccompose2025.poojacorner

import com.apps.tc.tccompose2025.models.God
import com.apps.tc.tccompose2025.models.PoojaData
import com.apps.tc.tccompose2025.models.PoojaService
import com.apps.tc.tccompose2025.models.Theme

sealed class PoojaState {
    object Loading : PoojaState()

    data class ShowPoojaItems(
        val poojaData: PoojaData,
        val godData: God,
        val themes: Theme,
        val flowers: PoojaService
    ) : PoojaState()

    data class Error(val message: String) : PoojaState()
}
