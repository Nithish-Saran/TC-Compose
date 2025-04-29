package com.apps.tc.tccompose2025.whatsappStickers

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.apps.tc.tccompose2025.App
import com.apps.tc.tccompose2025.WebScreenMode
import com.apps.tc.tccompose2025.log
import com.apps.tc.tccompose2025.ui.theme.actBeautyDark
import com.apps.tc.tccompose2025.ui.theme.colorAccent
import com.apps.tc.tccompose2025.ui.theme.colorPrimary
import com.apps.tc.tccompose2025.view.Header
import com.apps.tc.tccompose2025.view.WebScreen
import kotlinx.coroutines.launch

@Composable
fun Stickers(app: App, onReturn:() -> Unit) {
    val viewModel:StickersViewModel = viewModel()
    val scope = rememberCoroutineScope()
    val state = viewModel.stickersState.collectAsState().value

    LazyColumn(
        modifier = Modifier
            .background(
                color = colorPrimary
            )
            .fillMaxSize(),
        horizontalAlignment = Alignment.Start,
    ) {
        item {
            Header(
                heading = "WhatsApp Stickers",
                bgColor = actBeautyDark,
                textColor = colorAccent,
                onBackReq = {
                    viewModel.backReq {
                        onReturn()
                    }
                }
            )
        }
        when(state) {
            is StickersStateView.Loading -> {
                item {

                }
            }
            is StickersStateView.Success -> {
                items(state.stickers) { data ->
                    Row(
                        modifier = Modifier
                            .padding(8.dp)
                            .fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        AsyncImage(
                            model = "file:///android_asset/whatsapp/${data.imgUrl}",
                            contentDescription = "Article Image",
                            contentScale = ContentScale.Fit,
                            modifier = Modifier
                                .weight(0.5f)
                                .height(100.dp)
                        )
                        Column(
                            modifier = Modifier.weight(1f),
                        ) {
                            Text(
                                text = data.name,
                                style = MaterialTheme.typography.bodyMedium,
                                color = MaterialTheme.colorScheme.background
                            )
                            Text(
                                text = data.size,
                                style = MaterialTheme.typography.bodyMedium,
                                color = MaterialTheme.colorScheme.background,
                                modifier = Modifier.padding(top = 2.dp)
                            )
                            Row(
                                modifier = Modifier
                                    .padding(top = 6.dp),
                                horizontalArrangement = Arrangement.spacedBy(8.dp)
                            ) {
                                StickerButton(
                                    text = "Download"
                                ) {
                                    viewModel.downloadSticker(app, data)
                                }
                                StickerButton(
                                    text = "Instruction"
                                ) {
                                    viewModel.instruction(state.stickers)
                                }
                            }
                        }
                    }
                    HorizontalDivider()
                }
            }
            is StickersStateView.WebMode -> {
                item {
                    WebScreen(
                        mode = WebScreenMode.External,
                        uri = state.url
                    )
                }
            }
            is StickersStateView.Error -> {
                item {
                    log("Error")
                }
            }
        }
    }

    LaunchedEffect(Unit) {
        scope.launch { 
            viewModel.loadStickers(app)
        }
    }
}

@Composable
fun StickerButton(text: String, onClick: () -> Unit) {
    Button(
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF2E7D32)),
        contentPadding = PaddingValues(horizontal = 12.dp, vertical = 4.dp),
        shape = RoundedCornerShape(4.dp)
    ) {
        Text(
            text = text,
            color = Color.White,
            style = MaterialTheme.typography.bodyMedium,
            textAlign = TextAlign.Center
        )
    }
}


@Composable
@Preview( showSystemUi = true, showBackground = true)
fun PreviewStickers() {
     Stickers(App()) {}
}