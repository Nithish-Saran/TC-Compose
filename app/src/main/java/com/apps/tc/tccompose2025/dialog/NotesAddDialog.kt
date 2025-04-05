package com.apps.tc.tccompose2025.dialog

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.apps.tc.tccompose2025.ui.theme.colorGoldBg
import com.apps.tc.tccompose2025.ui.theme.colorPrimary
import com.apps.tc.tccompose2025.ui.theme.colorPrimaryDark

@Composable
fun NotesAddDialog(
    showDialog: Boolean,
    onDismiss: () -> Unit,
    onAddNote: (String, String) -> Unit,
) {
    var title = remember { mutableStateOf("") }
    val note = remember { mutableStateOf("") }

    if (showDialog) {
        AppDialog(
            content = {
                Column(
                    modifier = Modifier
                        .background(
                            color = colorPrimary
                        )
                        .padding(16.dp)
                        .fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(12.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {

                    TextField(
                        value = title.value,
                        onValueChange = { title.value = it },
                        modifier = Modifier
                            .fillMaxWidth(),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = Color(0xFF8B0000),
                            unfocusedBorderColor = Color(0xFF8B0000)
                        ),

                        placeholder = {
                            Text(
                                text = "Title",
                                style = MaterialTheme.typography.titleMedium,
                                color = Color.DarkGray,
                                modifier = Modifier
                                    .fillMaxWidth()
                            )
                        }
                    )

                    OutlinedTextField(
                        value = note.value,
                        onValueChange = { note.value = it },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(120.dp),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = Color(0xFF8B0000),
                            unfocusedBorderColor = Color(0xFF8B0000)
                        ),
                        placeholder = {
                            Text(
                                text = "notes...",
                                style = MaterialTheme.typography.bodyMedium,
                                color = Color(0xFF4F4F4F),
                                modifier = Modifier
                                    .fillMaxWidth()
                            )
                        }
                    )

                    Button(
                        onClick = { onAddNote(title.value, note.value) },
                        modifier = Modifier
                            .height(50.dp),
                        shape = RoundedCornerShape(8.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = colorPrimaryDark)
                    ) {
                        Text(
                            text = "ADD NOTES",
                            color = Color.White,
                        )
                    }
                }
            }
        ) { onDismiss() }
    }
}

@Composable
@Preview(showSystemUi = true, showBackground = true)
fun PreviewNotesDialog() {
    NotesAddDialog(
        showDialog = true,
        onAddNote = { _, _ -> },
        onDismiss = {}
    )
}