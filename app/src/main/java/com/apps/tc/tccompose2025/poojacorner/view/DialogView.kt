package com.apps.tc.tccompose2025.poojacorner.view

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.apps.tc.tccompose2025.dialog.AppDialog
import com.apps.tc.tccompose2025.models.Bg
import com.apps.tc.tccompose2025.models.Flower
import com.apps.tc.tccompose2025.models.God
import com.apps.tc.tccompose2025.models.Song
import com.apps.tc.tccompose2025.ui.theme.colorCommonText
import com.apps.tc.tccompose2025.ui.theme.colorTextTitle

@Composable
fun PoojaDialogItem(
    image: String,
    title: String,
    desc: String,
    index: Int,
    onReturn: (Int) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onReturn(index) }
            .padding(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        AsyncImage(
            model = image,
            contentDescription = null,
            contentScale = ContentScale.FillHeight,
            modifier = Modifier.size(100.dp)
        )
        Column(
            modifier = Modifier
                .padding(start = 12.dp)
                .wrapContentHeight(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.Start
        ) {
            Text(
                text = title,
                style = MaterialTheme.typography.bodyMedium,
                color = colorTextTitle
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = desc,
                style = MaterialTheme.typography.bodySmall,
                color = colorCommonText
            )
        }
    }
}