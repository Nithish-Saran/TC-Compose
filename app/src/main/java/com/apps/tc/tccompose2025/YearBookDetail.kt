package com.apps.tc.tccompose2025

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.apps.tc.tccompose2025.ui.theme.actHistoryLite
import com.apps.tc.tccompose2025.ui.theme.colorPrimary
import com.apps.tc.tccompose2025.ui.theme.colorWhite

@Composable
fun YearBookDetail() {
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
            Image(
                painter = painterResource(id = R.drawable.img_thoranam),
                contentDescription = null,
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(2f) // 2:1 Ratio
            )

            Text(
                text = "12 JAN 2019",
                color = colorWhite,
                style = MaterialTheme.typography.titleMedium,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .background(color = Color.Black.copy(alpha = 0.4f))
                    .align(Alignment.BottomCenter)
            )
        }

        Text(
            text = "தமிழகத்திற்கு ரூ.5,010 கோடியில் சாலை " +
                    "திட்டங்களுக்கு அடிக்கல் நாட்டப்பட்டது!",
            style = MaterialTheme.typography.titleSmall,
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
                text = "தமிழகத்தில் ரூபாய் 5,010 கோடியில் தேசிய நெடுஞ்சாலை திட்டங்களுக்கு " +
                        "பிரதமர் மோடி அடிக்கல் நாட்டினார். ...",
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
    YearBookDetail()
}
