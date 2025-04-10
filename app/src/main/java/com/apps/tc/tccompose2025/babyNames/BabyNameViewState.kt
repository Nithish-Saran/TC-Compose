package com.apps.tc.tccompose2025.babyNames

sealed class BabyNameViewState {
    object Loading : BabyNameViewState()
    data class Menu(val menu: Array<Pair<String, String>>) : BabyNameViewState()
    data class BabyNames(val names: List<String>) : BabyNameViewState()
    data class Error(val errorMsg: String) : BabyNameViewState()
}