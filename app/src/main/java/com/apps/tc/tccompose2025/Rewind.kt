package com.apps.tc.tccompose2025

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ScrollableTabRow
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRowDefaults.SecondaryIndicator
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.apps.tc.tccompose2025.dialog.ListDialog
import com.apps.tc.tccompose2025.ui.theme.colorAccent
import com.apps.tc.tccompose2025.ui.theme.colorGoldBg
import com.apps.tc.tccompose2025.ui.theme.colorPrimary
import com.apps.tc.tccompose2025.ui.theme.colorPrimaryDark
import com.apps.tc.tccompose2025.ui.theme.colorWhite
import com.apps.tc.tccompose2025.view.FloatingButton
import com.apps.tc.tccompose2025.view.Header

@Composable
fun Rewind() {
    val selectedTab = remember { mutableIntStateOf(0) }
    var showFilterDialog by remember { mutableStateOf(false) }
    val context = LocalContext.current
    val headings = context.resources.getStringArray(R.array.rewind_categories)
    val months = context.resources.getStringArray(R.array.tamil_months_gregorian).toList()

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(color = colorPrimary)
    ) {
        Header(
            heading = "Rewind 2023",
            bgColor = MaterialTheme.colorScheme.primary,
            textColor = colorAccent,
            withBack = false
        )

        ScrollableTabRow(
            selectedTabIndex = selectedTab.intValue,
            containerColor = colorPrimaryDark,
            contentColor = colorWhite,
            modifier = Modifier.fillMaxWidth(),
            edgePadding = 0.dp,
            indicator = {
                SecondaryIndicator(
                    modifier = Modifier
                        .tabIndicatorOffset(it[selectedTab.intValue])
                        .padding(horizontal = 8.dp),
                    height = 4.dp,
                    color = colorGoldBg
                )
            }
        ) {
            headings.forEachIndexed { index, letter ->
                Tab(
                    selected = selectedTab.intValue == index,
                    onClick = { selectedTab.intValue = index },
                    text = {
                        Text(
                            text = letter,
                            style = MaterialTheme.typography.titleSmall,
                            textAlign = TextAlign.Center,
                        )
                    },
                )
            }
        }

        RewindHomeList()

        FloatingButton(
            image = R.drawable.year_book_filter_btn
        ) {
            showFilterDialog = true
        }

        ListDialog(
            titles = months,
            showDialog = showFilterDialog,
            onDismiss = {
                showFilterDialog = false
            },
            onReturn = {
                //todo:handle here
            }
        )
    }
}

@Composable
private fun RewindHomeList() {
    LazyColumn(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 12.dp, vertical = 8.dp)
    ) {
        items(10) {
            Column(
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                ) {
                    Text(
                        text = "This is a long text that should wrap properly without being cut off.",
                        style = MaterialTheme.typography.titleMedium,
                        color = Color.Black,
                        modifier = Modifier.weight(0.6f),
                    )

                    AsyncImage(
                        model = "https://cdn.kadalpura.com/calendar/tamil/year_book/2023/3111.png",
                        contentDescription = "Article Image",
                        contentScale = ContentScale.Fit,
                        modifier = Modifier
                            .weight(0.4f)
                            .height(80.dp)
                    )
                }

                Text(
                    text = "3-2-2023, வெள்ளிக்கிழமை",
                    style = MaterialTheme.typography.labelMedium,
                    modifier = Modifier.fillMaxWidth(),
                    color = Color.Black,
                )

                Text(
                    text = "This is a long text that should wrap properly without being cut off.",
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier.fillMaxWidth(),
                    color = Color.Black,
                )
                HorizontalDivider(
                    modifier = Modifier
                        .padding(vertical = 18.dp)
                )
            }
        }
    }
}

@Composable
@Preview(showSystemUi = true, showBackground = true)
fun PreviewRewind() {
    Rewind()
}