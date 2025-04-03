package com.apps.tc.tccompose2025.rewind

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ScrollableTabRow
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRowDefaults.SecondaryIndicator
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.apps.tc.tccompose2025.R
import com.apps.tc.tccompose2025.dialog.ListDialog
import com.apps.tc.tccompose2025.models.RewindData
import com.apps.tc.tccompose2025.ui.theme.colorAccent
import com.apps.tc.tccompose2025.ui.theme.colorGoldBg
import com.apps.tc.tccompose2025.ui.theme.colorPrimary
import com.apps.tc.tccompose2025.ui.theme.colorPrimaryDark
import com.apps.tc.tccompose2025.ui.theme.colorWhite
import com.apps.tc.tccompose2025.view.FloatingButton
import com.apps.tc.tccompose2025.view.Header
import kotlinx.coroutines.launch

@Composable
fun Rewind(year: Int, onReturn: (RewindData) -> Unit) {
    var showFilterDialog by remember { mutableStateOf(false) }
    val context = LocalContext.current
    val headings = context.resources.getStringArray(R.array.rewind_categories)
    val months = context.resources.getStringArray(R.array.tamil_months_gregorian).toList()
    val viewModel: RewindViewModel = viewModel()
    val rewindState = viewModel.rewindState.collectAsState().value

    val pagerState = rememberPagerState { headings.size }
    val scope = rememberCoroutineScope()


    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(color = colorPrimary)
    ) {

        Text(
            text =  "Rewind $year",
            color = colorAccent,
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier
                .background(color = MaterialTheme.colorScheme.primary)
                .padding(vertical = 8.dp)
                .fillMaxWidth()

        )

        ScrollableTabRow(
            selectedTabIndex = pagerState.currentPage,
            containerColor = colorPrimaryDark,
            contentColor = colorWhite,
            modifier = Modifier.fillMaxWidth(),
            edgePadding = 0.dp,
            indicator = {
                SecondaryIndicator(
                    modifier = Modifier
                        .tabIndicatorOffset(it[pagerState.currentPage])
                        .padding(horizontal = 8.dp),
                    height = 4.dp,
                    color = colorGoldBg
                )
            }
        ) {
            headings.forEachIndexed { index, letter ->
                Tab(
                    selected = pagerState.currentPage == index,
                    onClick = {
                        scope.launch {
                            pagerState.animateScrollToPage(index)
                        }
                    },
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

        HorizontalPager(
            state = pagerState,
            modifier = Modifier.fillMaxSize()
        ) { pageIndex ->
            when(rewindState) {
                is RewindViewState.Loading -> {
                    CircularProgressIndicator()
                }
                is RewindViewState.Success -> {
                    RewindHomeList(rewindState.data, year) {
                        onReturn(it)
                    }
                }

                is RewindViewState.Error -> {}
            }
        }


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

    LaunchedEffect(pagerState.currentPage) {
        scope.launch {
            viewModel.fetchRewind(context, pagerState.currentPage)
        }
    }
}

@Composable
private fun RewindHomeList(
    rewind: List<RewindData>,
    year: Int,
    onReturn: (RewindData) -> Unit
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 12.dp, vertical = 8.dp)
    ) {
       items(rewind) { rewindItem ->
            Column(
                verticalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier
                    .clickable {onReturn(rewindItem)}
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                ) {
                    Text(
                        text = rewindItem.title,
                        style = MaterialTheme.typography.titleSmall,
                        color = Color.Black,
                        modifier = Modifier.weight(0.6f),
                    )

                    AsyncImage(
                        model = "https://cdn.kadalpura.com/calendar/tamil/year_book/$year/${rewindItem.key}.png",
                        contentDescription = "Article Image",
                        contentScale = ContentScale.Fit,
                        modifier = Modifier
                            .weight(0.4f)
                            .height(80.dp)
                    )
                }

                Text(
                    text = rewindItem.date,
                    style = MaterialTheme.typography.labelMedium,
                    modifier = Modifier.fillMaxWidth(),
                    color = Color.Black,
                )

                Text(
                    text = rewindItem.desc,
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier.fillMaxWidth(),
                    color = Color.Black,
                    maxLines = 3,
                    overflow = TextOverflow.Ellipsis
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
    Rewind(2024) {}
}