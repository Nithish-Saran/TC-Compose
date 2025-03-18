package com.apps.tc.tccompose2025.dialog

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign

@Composable
fun WrongAnsDialog(
    showDialog: Boolean,
    onTryAgain: () -> Unit,
    onNextQuestion: () -> Unit
) {
    if (showDialog) {
        AlertDialog(
            modifier = Modifier
                .fillMaxWidth(),
            onDismissRequest = {  },
            title = {
                Text(
                    text = "தவறான பதில்!",
                    style = MaterialTheme.typography.titleMedium,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                )
            },
            text = {
                Text(
                    text = "இந்த விடுகதைக்கு உங்கள் பதில் பொருந்தவில்லை!",
                    style = MaterialTheme.typography.labelMedium,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                )
           },
            confirmButton = {
                TextButton(
                    onClick = { onTryAgain() },
                ) {
                    Text(
                        text = "TRY AGAIN",
                        style = MaterialTheme.typography.titleSmall,
                        color = MaterialTheme.colorScheme.primary
                    )
                }
            },
            dismissButton = {
                TextButton(
                    onClick = { onNextQuestion() },
                ) {
                    Text(
                        text = "NEXT",
                        style = MaterialTheme.typography.titleSmall,
                        color = MaterialTheme.colorScheme.primary
                    )
                }
            }
        )
    }
}
