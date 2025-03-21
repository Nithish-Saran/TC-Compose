package com.apps.tc.tccompose2025.view

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.apps.tc.tccompose2025.ui.theme.colorPrimary
import com.apps.tc.tccompose2025.ui.theme.colorWhite

@Composable
fun Header(
    heading: String,
    bgColor: Color,
    textColor: Color = colorWhite,
    onBackReq: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(bgColor),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = Icons.Default.ArrowBack,
            contentDescription = "Main Action",
            tint = colorWhite,
            modifier = Modifier
                .padding(start = 8.dp)
                .size(28.dp)
                .clickable(
                    enabled = true,
                    onClick = {
                        onBackReq()
                    }
                )
        )

        Text(
            text = heading,
            color = textColor,
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier
                .padding(vertical = 8.dp)
                .fillMaxWidth()

        )
    }
}

@Composable
@Preview( showSystemUi = true, showBackground = true)
fun PreviewHeader() {
    Header(
        "Heading", colorPrimary,
        textColor = colorPrimary,
        onBackReq = {}
    )
}