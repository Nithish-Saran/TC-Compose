package com.apps.tc.tccompose2025.dialog

import androidx.appcompat.widget.DialogTitle
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.apps.tc.tccompose2025.models.Bg
import com.apps.tc.tccompose2025.models.Flower
import com.apps.tc.tccompose2025.models.God
import com.apps.tc.tccompose2025.models.Song
import com.apps.tc.tccompose2025.poojacorner.view.PoojaDialogItem
import com.apps.tc.tccompose2025.ui.theme.colorAccent
import com.apps.tc.tccompose2025.ui.theme.colorCommonText
import com.apps.tc.tccompose2025.ui.theme.colorGold
import com.apps.tc.tccompose2025.ui.theme.colorPrimary
import com.apps.tc.tccompose2025.ui.theme.colorTextLite

@Composable
fun PoojaDialog(
    title: String,
    god: List<God>? = null,
    song: List<Song>? = null,
    flower: List<Flower>? = null,
    bgs: List<Bg>? = null,
    onDismiss: () -> Unit,
    onReturn: (Int) -> Unit
) {
    AppDialog(
        content = {
            LazyColumn(
                modifier = Modifier
                    .background(color = colorGold)
                    .fillMaxWidth()
            )
            {
                stickyHeader {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(colorCommonText)
                            .padding(vertical = 8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        IconButton(onClick = { onDismiss() }) {
                            Icon(
                                imageVector = Icons.Default.ArrowBack,
                                contentDescription = "Back",
                                tint = colorPrimary,
                                modifier = Modifier
                                    .size(24.dp)
                            )
                        }

                        Text(
                            text = "விருப்பமான $title தேர்ந்தெடுக்கவும் ", // or leave empty if not needed
                            style = MaterialTheme.typography.labelSmall,
                            color = colorPrimary,
                            textAlign = TextAlign.Start,
                            modifier = Modifier
                                .padding(start = 4.dp, end = 8.dp)
                        )
                    }
                }

                when {
                    god != null -> {
                        items(god.size) { index ->
                            PoojaDialogItem(
                                image = god[index].godImage,
                                title = god[index].name,
                                desc = god[index].desc,
                                index = index,
                                onReturn = onReturn
                            )
                            HorizontalDivider()
                        }
                    }
                    song != null -> {
                        items(song.size) { index ->
                            PoojaDialogItem(
                                image = song[index].godImage,
                                title = song[index].title,
                                desc = "",
                                index = index,
                                onReturn = onReturn
                            )
                            HorizontalDivider()
                        }
                    }
                    flower != null -> {
                        items(flower.size) { index ->
                            PoojaDialogItem(
                                image = flower[index].flowerImage,
                                title = flower[index].name,
                                desc = flower[index].desc,
                                index = index,
                                onReturn = onReturn
                            )
                            HorizontalDivider()
                        }
                    }
                    bgs != null -> {
                        items(bgs.size) { index ->
                            PoojaDialogItem(
                                image = bgs[index].bgImage,
                                title = bgs[index].name,
                                desc = bgs[index].desc,
                                index = index,
                                onReturn = onReturn
                            )
                            HorizontalDivider()
                        }
                    }
                }
            }
        }
    ) { onDismiss() }
}