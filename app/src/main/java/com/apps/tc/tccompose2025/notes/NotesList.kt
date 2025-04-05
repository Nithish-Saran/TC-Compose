package com.apps.tc.tccompose2025.notes

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.apps.tc.tccompose2025.R
import com.apps.tc.tccompose2025.displayStringWithSlash
import com.apps.tc.tccompose2025.models.ReminderNote
import com.apps.tc.tccompose2025.ui.theme.colorPrimary

@Composable
fun NotesList(data: Array<ReminderNote>, onReturn: (Int) -> Unit) {
    LazyColumn(
        modifier = Modifier
            .background(
                color = colorPrimary
            )
            .fillMaxWidth(),
        horizontalAlignment = Alignment.Start,
    ) {
        itemsIndexed(data) { index, note ->
            Column(
                modifier = Modifier
                    .padding(horizontal = 8.dp)
                    .fillMaxWidth()
                    .clickable {
                        onReturn(index)
                    }
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.Start,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.main_ic_notes),
                        contentDescription = "",
                        modifier = Modifier
                            .padding(8.dp)
                            .size(48.dp)
                    )

                    Column(
                        modifier = Modifier
                            .weight(1f),
                        verticalArrangement = Arrangement.Center,
                    ) {
                        Text(
                            text = note.title,
                            style = MaterialTheme.typography.titleMedium,
                        )

                        Text(
                            text = note.desc,
                            style = MaterialTheme.typography.bodyMedium,
                        )
                    }
                }

                Text(
                    text = "Reminder for: ${note.reminder.displayStringWithSlash}",
                    style = MaterialTheme.typography.bodySmall,
                    textAlign = TextAlign.Center,
                    color = Color.Gray,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 2.dp)
                )

                HorizontalDivider()
            }
        }
    }
}

@Composable
@Preview(showSystemUi = true, showBackground = true)
fun PreviewList() {
    NotesList(emptyArray()) {}
}