package com.apps.tc.tccompose2025.view

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.apps.tc.tccompose2025.ui.theme.colorPrimary

@Composable
fun CommonList(items: List<String>, onReturn: (Int) -> Unit) {
    LazyColumn(
        modifier = Modifier
            .background(
                color = colorPrimary
            )
            .fillMaxWidth(),
        horizontalAlignment = Alignment.Start,
    ) {
        itemsIndexed(items) { index, value ->
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable {
                        onReturn(index)
                    }
            ) {
                Text(
                    text = value,
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier
                        .padding(vertical = 8.dp, horizontal = 16.dp)
                )
                HorizontalDivider()
            }
        }
    }
}

@Composable
@Preview(showSystemUi = true, showBackground = true)
fun PreviewList() {
    CommonList(emptyList()) {}
}