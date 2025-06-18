package com.apps.tc.tccompose2025.parigaram

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import com.apps.tc.tccompose2025.dialog.ListDialog
import com.apps.tc.tccompose2025.ui.theme.colorPrimary
import com.apps.tc.tccompose2025.ui.theme.colorPrimaryDark
import com.apps.tc.tccompose2025.view.CommonList
import com.apps.tc.tccompose2025.view.Header
import kotlinx.coroutines.launch

@Composable
fun ParigaraThalangal(onBackReq: () -> Unit, onReturn: (String) -> Unit) {
    val context = LocalContext.current
    val viewModel: ParigaramViewModel = viewModel()
    val parigaramState = viewModel.parigaramState.collectAsState().value
    val scope = rememberCoroutineScope()
    var showDialog by remember { mutableStateOf(false) }
    var parigramTitleIndex by remember { mutableIntStateOf(-1) }

    Column(
        modifier = Modifier
            .background(
                color = Color.Transparent
            )
            .fillMaxSize()
    ) {
        Header(
            heading = "பரிகார தலங்கள்",
            bgColor = colorPrimaryDark,
            textColor = colorPrimary
        ) { onBackReq }

        when(parigaramState) {
            is ParigaramState.Loading -> {}
            is ParigaramState.ParigaramList -> {
                CommonList(parigaramState.parigaramData.map { it.title }) {
                    showDialog = true
                    parigramTitleIndex = it
                }
                if (showDialog) {
                    ListDialog(
                        titles = parigaramState.parigaramData[parigramTitleIndex].articles.toList(),
                        onDismiss = {
                            showDialog = false
                        },
                        showDialog = showDialog
                    ) {
                        val htmlUrl = parigaramState.parigaramData[parigramTitleIndex].htmls[it]
                        onReturn("parigaram/${htmlUrl}")
                        Log.d("WHILOGS", htmlUrl)
                    }
                }
            }
        }
    }

    LaunchedEffect(Unit) {
        scope.launch {
            viewModel.fetchParigaram(context)
        }
    }
}

@Composable
@Preview( showSystemUi = true, showBackground = true)
fun PreviewParigaram() {
    //ParigaraThalangal() {}
}