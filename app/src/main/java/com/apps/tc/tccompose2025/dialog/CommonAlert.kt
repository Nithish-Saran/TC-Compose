package com.apps.tc.tccompose2025.dialog

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.apps.tc.tccompose2025.ui.theme.colorPrimary
import com.apps.tc.tccompose2025.ui.theme.colorPrimaryDark

@Composable
fun CommonAlertDialog(
    title: String,
    desc: String,
    showDialog: Boolean,
    confirmText: String,
    onConfirm: () -> Unit,
) {
    if (showDialog) {
        AppDialog(
            content = {
                Column(
                    modifier = Modifier
                        .background(
                            color = colorPrimary
                        )
                        .padding(12.dp)
                        .fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    Text(
                        text = title,
                        style = MaterialTheme.typography.titleMedium,
                        textAlign = TextAlign.Center,
                        modifier = Modifier
                            .fillMaxWidth()
                    )
                    Text(
                        text = desc,
                        style = MaterialTheme.typography.bodyMedium,
                        color = Color.Gray,
                        textAlign = TextAlign.Center,
                        modifier = Modifier
                            .padding(vertical = 12.dp)
                            .fillMaxWidth()
                    )
                    Text(
                        text = confirmText,
                        style = MaterialTheme.typography.labelLarge,
                        textAlign = TextAlign.Center,
                        color = colorPrimaryDark,
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable { onConfirm() }
                    )
                }
            }
        ) { onConfirm() }
    }

}

@Composable
@Preview(showSystemUi = true, showBackground = true)
fun PreviewAlertDialog() {
    CommonAlertDialog (
        title = "எச்சரிக்கை!",
        desc = "தமிழ் உலகின் பழமையான மொழிகளில் ஒன்றாகும். " +
                "இதன் இலக்கிய மரபுகள் பல ஆயிரம் ஆண்டுகளாக தொடர்ந்திருக்கின்றன. " +
                "சங்க காலத்திலிருந்து ஆரம்பமாகி, இன்று வரை தமிழ் மொழி பல்வேறு " +
                "பரிணாமங்களை கண்டுள்ளது. தமிழின் கவிதைகள், கதைகள், மற்றும் " +
                "பழமொழிகள் அதன் பண்பாட்டு வளத்தை வெளிப்படுத்துகின்றன",
        confirmText = "ஏற்கிறேன்",
        onConfirm = {},
        showDialog = true
    )
}
