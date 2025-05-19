package com.apps.tc.tccompose2025.pranayamam

sealed class PranayamamViewState {
    object Loading : PranayamamViewState()
    data class Menu(val menu: Array<Pair<String, String>>) : PranayamamViewState()
}