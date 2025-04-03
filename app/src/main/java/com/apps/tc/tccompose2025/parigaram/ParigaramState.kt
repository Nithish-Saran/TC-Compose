package com.apps.tc.tccompose2025.parigaram

import com.apps.tc.tccompose2025.models.ParigaramData

sealed class ParigaramState {
    data object Loading: ParigaramState()
    data class ParigaramList( val parigaramData: List<ParigaramData>): ParigaramState()
}