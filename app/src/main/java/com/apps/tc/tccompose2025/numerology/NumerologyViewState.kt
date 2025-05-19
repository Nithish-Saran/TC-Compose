package com.apps.tc.tccompose2025.numerology

sealed class NumerologyViewState {
    data object Loading: NumerologyViewState()
    data class DobMatch(val nameVal: Int, val dobValue: Int, val areFriendly: Boolean): NumerologyViewState()
    data class Success(val data: String): NumerologyViewState()
    data object Error: NumerologyViewState()
}