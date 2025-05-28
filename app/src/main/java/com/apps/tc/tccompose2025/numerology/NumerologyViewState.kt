package com.apps.tc.tccompose2025.numerology

sealed class NumerologyViewState {
    data object Loading: NumerologyViewState()
    sealed class DobMatchState: NumerologyViewState() {
        data object DefaultView: DobMatchState()
        data class DobMatch(val dobValue: Int, val dobpothuPalan: String ): DobMatchState()
    }
    sealed class NameMatchState: NumerologyViewState() {
        data object DefaultView: NameMatchState()
        data class NameMatch(val result: Array<Pair<String, String>>, val namepothuPalan: String): NameMatchState()
    }
}

sealed class DialogState {
    data class Error(val error: Pair<String, String>): DialogState()
    data object None: DialogState()
}