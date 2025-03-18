package com.apps.tc.tccompose2025.view

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp

@Composable
fun TitleDescription(
    textAlign: TextAlign,
    textColor : Color,
    title: String,
    desc: String
) {
    Text(
        text = title,
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        textAlign = textAlign,
        color = textColor,
        style = MaterialTheme.typography.titleMedium
    )

    Text(
        text = desc,
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        color = Color.Black,
        style = MaterialTheme.typography.bodyMedium
    )
}