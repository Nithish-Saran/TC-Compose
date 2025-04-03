package com.apps.tc.tccompose2025.rewind

import com.apps.tc.tccompose2025.models.RewindData

sealed class RewindViewState {
    data object Loading: RewindViewState()
    data object Error: RewindViewState()
    data class Success(val data: List<RewindData>): RewindViewState()

}