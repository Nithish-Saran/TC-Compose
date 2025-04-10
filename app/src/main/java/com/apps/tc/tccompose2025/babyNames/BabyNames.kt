package com.apps.tc.tccompose2025.babyNames

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import com.apps.tc.tccompose2025.App
import com.apps.tc.tccompose2025.dialog.BabyNameDialog
import com.apps.tc.tccompose2025.log
import com.apps.tc.tccompose2025.ui.theme.actBabyNamesDark
import com.apps.tc.tccompose2025.ui.theme.colorPrimary
import com.apps.tc.tccompose2025.view.CommonList
import com.apps.tc.tccompose2025.view.GridMenu
import com.apps.tc.tccompose2025.view.Header
import kotlinx.coroutines.launch

@Composable
fun BabyNames(app: App, onReturn: () -> Unit) {
    val viewModel: BabyNameViewModel = viewModel()
    val state = viewModel.babyNameState.collectAsState().value
    val scope = rememberCoroutineScope()
    val showDialog = remember { mutableStateOf(false) }
    val selectedMenu = remember { mutableStateOf("குழந்தை பெயர்கள்") }
    val selectedMenuId = remember { mutableIntStateOf(-1) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = actBabyNamesDark)
    ) {
        Header(
            heading = selectedMenu.value,
            bgColor = Color(0xFF36006E),
            textColor = colorPrimary,
            onBackReq = {
                viewModel.backReq {
                    onReturn()
                }
            }
        )
        when(state) {
            is BabyNameViewState.Loading -> {log("load")}
            is BabyNameViewState.Menu -> {
                GridMenu(state.menu.toList()) {
                    showDialog.value = true
                    selectedMenuId.intValue = it
                    // update the category type as title
                    selectedMenu.value = state.menu[it].second
                }
                log("menu")
            }
            is BabyNameViewState.BabyNames -> {

                CommonList(state.names) { }
                log(state.names)
                log("list")
            }
            is BabyNameViewState.Error -> {
                log(state.errorMsg)
            }
        }

        BabyNameDialog(
            app = app,
            showDialog = showDialog.value,
            selectedMenu = selectedMenuId.intValue,
            onDismiss = {
                showDialog.value = false
            },
            onReturn = {star, starIndex, isGirl ->
                log(starIndex)
                viewModel.loadBabyNames(
                    isGirl = isGirl,
                    menuId = selectedMenuId.intValue,
                    starIndex = starIndex
                )
                showDialog.value = false
                // to add the star name in title if selected
                if (starIndex >= 0) {
                    selectedMenu.value = "${selectedMenu.value} - $star"
                }
            },
        )
    }

    LaunchedEffect(Unit) {
        scope.launch {
            viewModel.getBabyNamesData(app)
        }
    }
}

@Composable
@Preview( showSystemUi = true, showBackground = true)
fun PreviewBabyNames() {
    BabyNames(App()){}
}