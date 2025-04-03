package com.apps.tc.tccompose2025.Pranayamam

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import com.apps.tc.tccompose2025.R
import com.apps.tc.tccompose2025.ui.theme.actBabyNamesDark
import com.apps.tc.tccompose2025.ui.theme.colorPrimary
import com.apps.tc.tccompose2025.view.GridMenu
import com.apps.tc.tccompose2025.view.Header

@Composable
fun Pranayamam() {
    val menuItems = listOf(
        Pair(R.drawable.ic_pranayamam_body,  "Pranayammam"),
        Pair(R.drawable.ic_pranayamam_cold,  "Pranayammam"),
        Pair(R.drawable.ic_pranayamam_blood,  "Pranayammam"),
        Pair(R.drawable.ic_pranayamam_start,  "Pranayammam"),
        Pair(R.drawable.ic_pranayamam_burning,  "Pranayammam"),
        Pair(R.drawable.ic_pranayamam_thyroid,  "Pranayammam"),
        Pair(R.drawable.ic_pranayamam_benefits,  "Pranayammam"),
        Pair(R.drawable.ic_pranayamam_heat_tolow,  "Pranayammam"),
        Pair(R.drawable.ic_pranayamam_heat_tohigh,  "Pranayammam"),
        Pair(R.drawable.ic_pranayamam_memory_power,  "Pranayammam"),
        Pair(R.drawable.ic_pranayamam_mind_peace,  "Pranayammam"),
        Pair(R.drawable.ic_pranayamam_shining_face,  "Pranayammam"),
        Pair(R.drawable.ic_pranayamam_weight_loss,  "Pranayammam"),
    )
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = actBabyNamesDark)
    ) {
        Header(
            heading = "பிராணாயாமம்",
            bgColor = Color(0xFF36006E),
            textColor = colorPrimary,
            onBackReq = {}
        )
        GridMenu(menuItems) { }
    }

}

@Composable
@Preview( showSystemUi = true, showBackground = true)
fun PreviewPranayama() {
    Pranayamam()
}