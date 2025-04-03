package com.apps.tc.tccompose2025.dialog

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.apps.tc.tccompose2025.ui.theme.colorAccent
import com.apps.tc.tccompose2025.ui.theme.colorPrimary
import com.apps.tc.tccompose2025.ui.theme.colorPrimaryDark
import com.apps.tc.tccompose2025.view.CommonList
import com.apps.tc.tccompose2025.view.Header

@Composable
fun ListDialog(
    header: String = "தேர்வு செய்யவும்",
    titles: List<String>,
    showDialog: Boolean,
    onDismiss: () -> Unit,
    onReturn: (Int) -> Unit
) {
    if (showDialog) {
        AppDialog(
            content = {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                ) {
                    Text(
                        text = header,
                        color = colorPrimary,
                        textAlign = TextAlign.Center,
                        style = MaterialTheme.typography.labelMedium,
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(
                                color = colorPrimaryDark
                            )
                            .padding(8.dp,)

                    )
                    CommonList(items = titles) {
                        onReturn(it)
                    }
                }
            }
        ) { onDismiss() }
    }
}

@Composable
@Preview(showSystemUi = true, showBackground = true)
fun PreviewFilterDialog() {
    ListDialog (titles = emptyList(), showDialog = true, onReturn = {}, onDismiss = {})
}