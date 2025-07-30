package com.apps.tc.tccompose2025.dialog

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringArrayResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.apps.tc.tccompose2025.R
import com.apps.tc.tccompose2025.ui.theme.colorCommonText
import com.apps.tc.tccompose2025.ui.theme.colorPrimary
import com.apps.tc.tccompose2025.ui.theme.colorPrimaryDark
import com.apps.tc.tccompose2025.ui.theme.colorTextTitle

@Composable
fun CoffeeMeetDialog(
    showDialog: Boolean,
    onConfirm: () -> Unit,
    onDismiss: () -> Unit
) {
    if (showDialog) {
        val lines = stringArrayResource(id = R.array.coffee_meetup_intro)
        AppDialog(
            content = {
                Column(
                    modifier = Modifier
                        .background(
                            color = colorPrimary
                        )
                        .padding(horizontal = 18.dp, vertical = 12.dp)
                        .fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    Row(
                        modifier = Modifier
                            .padding(bottom = 24.dp, start = 8.dp, end = 8.dp)
                            .fillMaxWidth(),
                        verticalAlignment = Alignment.Bottom,
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Text(
                            text = "எங்களுடன்",
                            style = MaterialTheme.typography.titleSmall,
                            color = colorTextTitle,
                        )
                        Image(
                            painter = painterResource(R.drawable.ic_coffee_cup),
                            contentDescription = "Coffee Cup",
                            modifier = Modifier
                                .padding(horizontal = 8.dp)
                                .size(32.dp)
                        )
                        Text(
                            text = "அருந்துங்கள்!",
                            style = MaterialTheme.typography.titleSmall,
                            color = colorTextTitle,
                        )
                    }
                    Text(
                        text = "வணக்கம் அன்பரே,",
                        style = MaterialTheme.typography.labelMedium,
                        textAlign = TextAlign.Start,
                        modifier = Modifier
                            .fillMaxWidth(),
                        color = colorCommonText,
                    )
                    Text(
                        text = lines[0],
                        style = MaterialTheme.typography.bodySmall,
                        color = colorCommonText,
                        modifier = Modifier
                            .padding(vertical = 12.dp)
                            .fillMaxWidth()
                    )
                    Text(
                        text = lines[1],
                        style = MaterialTheme.typography.bodySmall,
                        color = colorCommonText,
                        textAlign = TextAlign.Start,
                        modifier = Modifier
                            .padding(vertical = 12.dp)
                            .fillMaxWidth()
                    )
                    Text(
                        text = lines[2],
                        style = MaterialTheme.typography.bodySmall,
                        color = colorCommonText,
                        modifier = Modifier
                            .padding(vertical = 12.dp)
                            .fillMaxWidth()
                    )

                    Text(
                        text = "சரி",
                        style = MaterialTheme.typography.labelLarge,
                        textAlign = TextAlign.Center,
                        color = colorPrimaryDark,
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable { onConfirm() }
                    )
                }
            }
        ) { onDismiss() }
    }

}

@Composable
@Preview(showSystemUi = true, showBackground = true)
fun PreviewCoffeeMeeetDialog() {
    CoffeeMeetDialog(
        onConfirm = {},
        showDialog = true,
        onDismiss = {}
    )
}
