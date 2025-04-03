package com.apps.tc.tccompose2025.Palankal

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.apps.tc.tccompose2025.ui.theme.colorGoldBg
import com.apps.tc.tccompose2025.ui.theme.colorPrimary
import com.apps.tc.tccompose2025.view.CommonList
import com.apps.tc.tccompose2025.view.Header
import kotlinx.coroutines.launch

@Composable
fun Palankal(mode: Int, onReturn: () -> Unit){
    val context = LocalContext.current
    val viewModel: PalankalViewModel = viewModel()
    val palanState = viewModel.palankalState.collectAsState().value
    val scope = rememberCoroutineScope()
    val palanKalTitle = remember { mutableStateListOf<String>() }
    val title =
    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Header(
            heading =  when(mode) {
                0 -> "மச்ச சாஸ்திரம்"
                1 -> "கனவுகளும் அதன் பலன்களும்"
                else -> "பழமொழிகள்"
            },
            bgColor = Color(0xFF36006E),
            textColor = colorPrimary,
            onBackReq = {
                viewModel.backReq {
                    onReturn()
                }
            }
        )

        when(palanState) {
            is PalankalState.Download -> { //todo:handle
            }
            is PalankalState.Loading -> {
                CircularProgressIndicator()
            }
            is PalankalState.Empty -> {}
            is PalankalState.Success -> {
                palanKalTitle.clear()
                palanState.palankalData.map {
                    palanKalTitle.add(it.first)
                }
                CommonList(palanKalTitle) {
                    viewModel.fetchPalankalList(palanState.palankalData, it)
                }
            }
            is PalankalState.ShowDetail -> {
                Text(
                    text = "* Please tap on the palan, to share it!",
                    style = MaterialTheme.typography.titleSmall,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(
                            color = colorGoldBg
                        )
                        .padding(8.dp,)
                )
                CommonList(palanState.palankalList.second.toList()) { }
            }
        }

        LaunchedEffect(Unit) {
            scope.launch {
                viewModel.fetchPalankal(context, mode)
            }
        }
    }
}

@Composable
@Preview( showSystemUi = true, showBackground = true)
fun PreviewMatchaSasthiram() {
    Palankal(0){}
}