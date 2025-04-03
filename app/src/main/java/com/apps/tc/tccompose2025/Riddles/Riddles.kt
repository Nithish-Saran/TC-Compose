package com.apps.tc.tccompose2025.Riddles

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import com.apps.tc.tccompose2025.ui.theme.colorAccent
import com.apps.tc.tccompose2025.ui.theme.colorPrimary
import com.apps.tc.tccompose2025.view.GridMenu
import com.apps.tc.tccompose2025.view.Header

@Composable
fun Riddles() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = colorAccent)
    ) {
        Header(
            heading = "விடுகதை விளயாட்டு",
            bgColor = Color(0xFFB51E25),
            textColor = colorPrimary,
            onBackReq = {}
        )
        GridMenu(emptyList()) { }   //todo:handle
    }
}

@Composable
@Preview( showSystemUi = true, showBackground = true)
fun PreviewRiddle() {
    Riddles()
}