package com.apps.tc.tccompose2025

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import com.apps.tc.tccompose2025.ui.theme.actBabyNamesDark
import com.apps.tc.tccompose2025.ui.theme.colorPrimary
import com.apps.tc.tccompose2025.view.GridMenu
import com.apps.tc.tccompose2025.view.Header

@Composable
fun BabyNames() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = actBabyNamesDark)
    ) {
        Header(
            heading = "குழந்தை பெயர்கள்",
            bgColor = Color(0xFF36006E),
            textColor = colorPrimary,
            withBack = false
        )
        GridMenu(emptyList()) { }   //todo:handle
    }
}

@Composable
@Preview( showSystemUi = true, showBackground = true)
fun PreviewBabyNames() {
    BabyNames()
}