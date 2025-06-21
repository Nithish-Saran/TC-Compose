package com.apps.tc.tccompose2025.planetory

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class PlanetoryViewModel: ViewModel()  {
    private val _planetoryState = MutableStateFlow<PlanetoryViewState>(PlanetoryViewState.Loading)
    val planetoryState: StateFlow<PlanetoryViewState> = _planetoryState.asStateFlow()
}