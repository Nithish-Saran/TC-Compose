package com.apps.tc.tccompose2025.dictionary

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.apps.tc.tccompose2025.R
import com.apps.tc.tccompose2025.ui.theme.ComposeTamilCalendar2025Theme

@Composable
fun DictionaryShareScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(8.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            Image(
                painter = painterResource(id = R.drawable.ic_launcher),
                contentDescription = null,
                modifier = Modifier
                    .size(200.dp)
                    .align(Alignment.Center)
                    .alpha(0.4f)
            )

            Column(
                modifier = Modifier
                    .align(Alignment.TopStart)
                    .padding(end = 8.dp, top = 8.dp)
            ) {
                Text(
                    text = "TextView",
                    style = MaterialTheme.typography.headlineMedium,
                )
                Text(
                    text = "TextView",
                    style = MaterialTheme.typography.bodyLarge,
                    modifier = Modifier
                        .padding(vertical = 4.dp)
                )
                Text(
                    text = "TextView",
                    style = MaterialTheme.typography.bodySmall,
                )

                Text(
                    text = "Meaning",
                    style = MaterialTheme.typography.titleLarge,
                    modifier = Modifier
                        .padding(vertical = 12.dp)
                )
                Text(
                    text = "-",
                    style = MaterialTheme.typography.bodyLarge,
                    modifier = Modifier
                        .padding(bottom = 12.dp)
                )
                Text(
                    text = "-",
                    style = MaterialTheme.typography.bodyLarge,
                )

                Text(
                    text = "Synonyms",
                    style = MaterialTheme.typography.titleLarge,
                    modifier = Modifier
                        .padding(vertical = 12.dp)
                )
                Text(
                    text = "-",
                    style = MaterialTheme.typography.bodyLarge,
                )

                Text(
                    text = "Antonyms",
                    style = MaterialTheme.typography.titleLarge,
                    modifier = Modifier
                        .padding(vertical = 12.dp)
                )
                Text(
                    text = "-",
                    style = MaterialTheme.typography.bodyLarge,
                )
            }
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        FloatingActionButton(
            onClick = {
                // todo:handle
            },
            modifier = Modifier
                .align(Alignment.BottomEnd),
            containerColor = Color.Red,
            shape = CircleShape
        ) {
            Icon(
                imageVector = Icons.Default.Share,
                contentDescription = "Share",
                modifier = Modifier
                    .background(
                        color = Color.Transparent
                )
                    .size(28.dp),
                tint = Color.Gray
            )
        }
    }
}

@Preview(showBackground = true, showSystemUi = true,)
@Composable
fun SharePreview() {
    ComposeTamilCalendar2025Theme {
        DictionaryShareScreen()
    }
}
