package com.apps.tc.tccompose2025.planetory

import com.apps.tc.tccompose2025.models.PlanetoryData

sealed class PlanetoryViewState {
    object Loading : PlanetoryViewState()
    data class Success(val planetData: PlanetoryData) : PlanetoryViewState()
    data class Error(val message: String) : PlanetoryViewState()
}