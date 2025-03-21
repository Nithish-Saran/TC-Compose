package com.apps.tc.tccompose2025.viewStates

sealed class PalankalState {
    data object Loading: PalankalState()
    data object Download: PalankalState()
    data class Success(val palankalData: Array<Pair<String, Array<String>>>): PalankalState()
    data class ShowDetail(val palankalData: Array<Pair<String, Array<String>>>,
                          val palankalList: Pair<String, Array<String>>): PalankalState()
    data object Empty: PalankalState()
}