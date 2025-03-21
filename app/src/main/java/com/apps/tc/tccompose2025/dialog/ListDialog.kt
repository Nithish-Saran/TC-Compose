package com.apps.tc.tccompose2025.dialog

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.apps.tc.tccompose2025.ui.theme.colorAccent
import com.apps.tc.tccompose2025.ui.theme.colorPrimaryDark
import com.apps.tc.tccompose2025.view.CommonList
import com.apps.tc.tccompose2025.view.Header

@Composable
fun ListDialog(
    titles: List<String>,
    showDialog: Boolean,
    onDismiss: () -> Unit,
    onReturn: () -> Unit
) {
    if (showDialog) {
        AppDialog(
            content = {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                ) {
                    Header(
                        heading = "தேர்வு செய்யவும்",
                        bgColor = colorPrimaryDark,
                        textColor = colorAccent,
                        onBackReq = {}
                    )
                    CommonList(items = titles) {
                        onReturn()
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