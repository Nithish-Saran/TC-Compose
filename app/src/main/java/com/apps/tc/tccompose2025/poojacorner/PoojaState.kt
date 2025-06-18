package com.apps.tc.tccompose2025.poojacorner

import com.apps.tc.tccompose2025.models.PoojaData

sealed class PoojaState {
    object Loading : PoojaState()
    data class Success(val poojaData: Array<PoojaData>) : PoojaState()
    data class Error(val message: String) : PoojaState()
}