package com.apps.tc.tccompose2025.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.apps.tc.tccompose2025.ui.theme.colorGoldBg
import com.apps.tc.tccompose2025.ui.theme.colorPrimaryDark

@Composable
fun ShareNote() {
    Text(
        text = "கீழே உள்ள தகவல்களை தொடுவதன் மூலம் நீங்கள் மற்ற செயலியில் பகிரலாம்",
        style = MaterialTheme.typography.labelSmall,
        color = colorPrimaryDark,
        modifier = Modifier
            .fillMaxWidth()
            .background(
                color = colorGoldBg
            )
            .padding(vertical = 8.dp, horizontal = 16.dp)
    )
}