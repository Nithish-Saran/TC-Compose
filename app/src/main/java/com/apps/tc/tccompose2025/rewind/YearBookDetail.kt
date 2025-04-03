package com.apps.tc.tccompose2025.rewind

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.apps.tc.tccompose2025.models.RewindData
import com.apps.tc.tccompose2025.ui.theme.actHistoryLite
import com.apps.tc.tccompose2025.ui.theme.colorPrimary
import com.apps.tc.tccompose2025.ui.theme.colorWhite

@Composable
fun YearBookDetail(rewindData: RewindData, year: Int) {
    val scrollState = rememberScrollState()
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(colorPrimary)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            AsyncImage(
                model = "https://cdn.kadalpura.com/calendar/tamil/year_book/$year/${rewindData.key}.png",
                contentDescription = "Article Image",
                contentScale = ContentScale.FillWidth,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
            )

            Text(
                text = "${rewindData.date} ${rewindData.month} ${year}",
                color = colorWhite,
                style = MaterialTheme.typography.bodyMedium,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .background(color = Color.Black.copy(alpha = 0.4f))
                    .align(Alignment.BottomCenter)
            )
        }

        Text(
            text = rewindData.title,
            style = MaterialTheme.typography.bodyMedium,
            color = colorWhite,
            modifier = Modifier
                .padding(top = 4.dp)
                .fillMaxWidth()
                .background(actHistoryLite)
                .padding(8.dp)
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(scrollState)
        ) {
            Text(
                text = rewindData.desc,
                style = MaterialTheme.typography.bodyMedium,
                color = Color.Black,
                modifier = Modifier.padding(8.dp)
            )
        }
    }
}

@Composable
@Preview(showSystemUi = true, showBackground = true)
fun PreviewYearBook() {
    YearBookDetail(RewindData(
        date = TODO(),
        month = TODO(),
        title = TODO(),
        key = TODO(),
        desc = TODO()
    ), 2024)
}
