package com.apps.tc.tccompose2025.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.apps.tc.tccompose2025.R
import com.apps.tc.tccompose2025.ui.theme.colorPrimary

@Composable
fun GridMenu(menu: List<Pair<Int, String>>, onItemClick: (Int) -> Unit) {

    LazyVerticalGrid(
        columns = GridCells.Fixed(3), // Adjust the column count as needed
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        horizontalArrangement = Arrangement.Center,
        verticalArrangement = Arrangement.SpaceBetween,
    ) {
        itemsIndexed(menu) { index, value ->
            Column(
                modifier = Modifier
                    .clickable { onItemClick(index) },
                verticalArrangement = Arrangement.SpaceBetween,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Image(
                    painter = painterResource(value.first),
                    contentDescription = "image",
                    modifier = Modifier
                        .padding(top = 12.dp)
                        .size(64.dp)
                )
                Text(
                    text = value.second,
                    style = MaterialTheme.typography.bodyMedium,
                    color = colorPrimary,
                    modifier = Modifier
                        .padding(horizontal = 8.dp)
                )
            }
        }
    }
}

@Composable
@Preview( showSystemUi = true, showBackground = true)
fun PreviewGridMenu() {
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
    GridMenu(menuItems, onItemClick = {})
}