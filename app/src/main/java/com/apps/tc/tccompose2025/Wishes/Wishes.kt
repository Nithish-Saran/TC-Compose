package com.apps.tc.tccompose2025.Wishes

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.apps.tc.tccompose2025.ui.theme.colorPrimary
import com.apps.tc.tccompose2025.ui.theme.colorPrimaryDark
import com.apps.tc.tccompose2025.view.CommonGridMenu
import com.apps.tc.tccompose2025.view.CommonList
import com.apps.tc.tccompose2025.view.Header
import com.apps.tc.tccompose2025.view.ShareNote
import kotlinx.coroutines.launch

@Composable
fun Wishes(onReturn: () -> Unit) {
    val scope = rememberCoroutineScope()
    val viewModel: WishesViewModel = viewModel()
    val context = LocalContext.current
    val wishState = viewModel.wishesState.collectAsState().value
    val menuList = remember { mutableStateListOf<Pair<String, String>>() }
    Column(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        Header(
            heading = "வாழ்த்துக்கள்",
            bgColor = colorPrimaryDark,
            textColor = colorPrimary,
            onBackReq = {
                viewModel.backReq {
                    onReturn()
                }
            }
        )

        when(wishState) {
            is WishesState.Loading -> {}
            is WishesState.Empty -> {
                CircularProgressIndicator()
            }
            is WishesState.Download -> {}
            is WishesState.showMenu -> {
                menuList.clear()
                wishState.menuItems.map {
                    menuList.add(it)
                }
                CommonGridMenu(menuList) {
                    viewModel.fetchWishes(it)
                }
            }
            is WishesState.showList -> {
                Text(
                    text = wishState.wishList.first,
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.onPrimary,
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(
                            color = colorPrimaryDark
                        )
                        .padding(vertical = 12.dp, horizontal = 16.dp)
                )
                ShareNote()
                CommonList(wishState.wishList.second.toList()) { }
            }
        }
    }

    LaunchedEffect(Unit) {
        scope.launch {
            viewModel.fetchWishesList(context)
        }
    }
}

@Composable
@Preview( showSystemUi = true, showBackground = true)
fun PreviewWishes() {
    Wishes {  }
}