package com.apps.tc.tccompose2025.BabyNames

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.apps.tc.tccompose2025.ui.theme.colorGoldBg
import com.apps.tc.tccompose2025.ui.theme.colorPrimary
import com.apps.tc.tccompose2025.view.CommonList
import com.apps.tc.tccompose2025.view.Header

@Composable
fun BabyNamesList() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        Header(
            heading = "குழந்தை பெயர்கள்",
            bgColor = Color(0xFF36006E),
            textColor = colorPrimary,
            onBackReq = {}
        )
        Text(
            text = "* Please tap on the names, to share it!",
            style = MaterialTheme.typography.titleSmall,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    color = colorGoldBg
                )
                .padding(8.dp,)
        )
        CommonList(emptyList()) {
            //todo:handle
        }
    }
}

@Composable
@Preview( showSystemUi = true, showBackground = true)
fun PreviewBabyName() {
    BabyNamesList()
}