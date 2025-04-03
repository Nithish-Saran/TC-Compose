package com.apps.tc.tccompose2025.view

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.apps.tc.tccompose2025.WebScreenMode

@Composable
fun WebScreen(
    mode: WebScreenMode,
    uri: String,
    ) {
    when (mode) {
        WebScreenMode.Internal -> WebViewInternal(modifier = Modifier.fillMaxSize(), uri)
        WebScreenMode.External -> WebViewExternal(modifier = Modifier.fillMaxSize(), uri)
        WebScreenMode.Assets -> WebViewAssets(modifier = Modifier.fillMaxSize(), uri)
    }
}

