package com.apps.tc.tccompose2025.view

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.apps.tc.tccompose2025.R
import com.apps.tc.tccompose2025.ui.theme.colorPrimary

@Composable
fun CommonGridMenu(menu: List<Pair<String, String>>, onItemClick: (Int) -> Unit) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(3),
        modifier = Modifier
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.Center,
        verticalArrangement = Arrangement.SpaceBetween,
    ) {
        itemsIndexed(menu) { index, value ->
            Box(
                modifier = Modifier
                    .size(160.dp), // Fixed size ensures proper line drawing,
                contentAlignment = Alignment.Center
            ) {
                Column(
                    modifier = Modifier
                        .padding(horizontal = 4.dp)
                        .clickable { onItemClick(index) },
                    verticalArrangement = Arrangement.SpaceBetween,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Image(
                        painter = rememberAsyncImagePainter(
                            ImageRequest.Builder(LocalContext.current)
                                .data("file:///android_asset/wishes/${value.second}.png")
                                .crossfade(true)
                                .build()
                        ),
                        contentDescription = "image",
                        modifier = Modifier
                            .size(64.dp)
                    )
                    Text(
                        text = value.first,
                        style = MaterialTheme.typography.labelSmall,
                        color = Color.Black,
                        modifier = Modifier
                            .padding(top = 12.dp)
                            .fillMaxWidth(),
                        textAlign = TextAlign.Center
                    )
                }
                DrawGridLines(index, menu.size)
            }
        }
    }
}

@Composable
fun DrawGridLines(index: Int, itemCount: Int) {
    val columnCount = 3  // Fixed columns in the grid
    val rowCount = (itemCount + columnCount - 1) / columnCount // Total rows
    val rowIndex = index / columnCount
    val columnIndex = index % columnCount

    Canvas(
        modifier = Modifier
            .fillMaxSize()
    ) {
        val strokeWidth = 2f
        val color = Color.Gray

        // Draw vertical line (except last column)
        if (columnIndex < columnCount - 1) {
            drawLine(
                color = color,
                start = androidx.compose.ui.geometry.Offset(size.width, 0f),
                end = androidx.compose.ui.geometry.Offset(size.width, size.height),
                strokeWidth = strokeWidth
            )
        }

        // Draw horizontal line (except last row)
        if (rowIndex < rowCount - 1) {
            drawLine(
                color = color,
                start = androidx.compose.ui.geometry.Offset(0f, size.height),
                end = androidx.compose.ui.geometry.Offset(size.width, size.height),
                strokeWidth = strokeWidth
            )
        }
    }
}

@Composable
@Preview( showSystemUi = true, showBackground = true)
fun PreviewCommonGridMenu() {
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
    //CommonGridMenu(menuItems, onItemClick = {})
//    Column(
//        modifier = Modifier
//            .background(
//                color = Color.Blue
//            )
//            .fillMaxWidth()
//    ) {
//        LazyVerticalGrid(
//            columns = GridCells.Fixed(3), // Adjust the column count as needed
//            modifier = Modifier
//                .fillMaxWidth()
//                .background(
//                    color = MaterialTheme.colorScheme.primary
//                ),
//            //.padding(8.dp),
//            horizontalArrangement = Arrangement.Center,
//            verticalArrangement = Arrangement.SpaceBetween,
//        ) {
//            items(10) {
//                DrawGridLines(it, 10)
//            }
//        }
//    }

}