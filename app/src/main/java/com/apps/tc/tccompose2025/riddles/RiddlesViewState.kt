package com.apps.tc.tccompose2025.riddles

sealed class RiddlesViewState {
    data object Loading: RiddlesViewState()
    data object Error: RiddlesViewState()
    data class Menu(val riddle: List<Pair<String, String>>): RiddlesViewState()
    //data class Play(val riddleData: RiddlesData): RiddlesViewState()

}