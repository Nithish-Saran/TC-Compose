package com.apps.tc.tccompose2025.riddles

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import com.apps.tc.tccompose2025.models.RiddlesData
import com.apps.tc.tccompose2025.ui.theme.colorAccent
import com.apps.tc.tccompose2025.ui.theme.colorGoldBg
import com.apps.tc.tccompose2025.ui.theme.colorPrimary
import com.apps.tc.tccompose2025.view.GridMenu
import com.apps.tc.tccompose2025.view.Header
import kotlinx.coroutines.launch

@Composable
fun Riddles(onBackReq: () -> Unit, onNextReq: (RiddlesData) -> Unit) {
    val scope = rememberCoroutineScope()
    val viewModel: RiddleViewModel = viewModel()
    val riddleMenuState = viewModel.riddleMenu.collectAsState().value
    val riddleData = viewModel.riddlesData.collectAsState().value
    val context = LocalContext.current
    var color = colorAccent

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = color)
    ) {
        Header(
            heading = "விடுகதை விளயாட்டு",
            bgColor = Color(0xFFB51E25),
            textColor = colorPrimary,
            onBackReq = {
                onBackReq()
            }
        )

        when(riddleMenuState) {
            is RiddlesViewState.Loading -> {
                ///CircularProgressIndicator()
            }
            is RiddlesViewState.Menu -> {
                GridMenu(riddleMenuState.riddle) {
                    onNextReq(riddleData[it])
                }
            }
            is RiddlesViewState.Error -> {
                //todo:handle
            }
        }
           //todo:handle
    }

    LaunchedEffect(Unit) {
        scope.launch {
            viewModel.fetchRiddleMenu(context)
        }
    }
}

@Composable
@Preview( showSystemUi = true, showBackground = true)
fun PreviewRiddle() {
    Riddles(onBackReq = {}, onNextReq = {})
}