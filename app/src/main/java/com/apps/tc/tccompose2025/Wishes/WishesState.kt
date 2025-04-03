package com.apps.tc.tccompose2025.Wishes

sealed class WishesState {
    data object Loading: WishesState()
    data object Download: WishesState()
    data class showMenu(val menuItems: Array<Pair<String, String>>): WishesState()
    data class showList(val wishList: Pair<String, Array<String>>): WishesState()
    data object Empty: WishesState()
}