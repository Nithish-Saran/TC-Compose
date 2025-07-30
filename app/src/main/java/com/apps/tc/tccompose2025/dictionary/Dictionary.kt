package com.apps.tc.tccompose2025.dictionary

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ScrollableTabRow
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults.SecondaryIndicator
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.apps.tc.tccompose2025.R
import com.apps.tc.tccompose2025.ui.theme.ComposeTamilCalendar2025Theme
import com.apps.tc.tccompose2025.view.CommonList

@Composable
fun Dictionary() {
    val selectedTab = remember { mutableIntStateOf(1) }
    val tabs = listOf(
        "TODAY", "BROWSE", "SEARCH", "FAVOURITES"
    )
    val alphabetTabs = ('A'..'Z').map { it.toString() }
    val selectedAlphabetTab = remember { mutableIntStateOf(0) }

    var expanded by remember { mutableStateOf(false) }
    val fabSize = 48.dp
    val icons = listOf(
        R.drawable.store_rate_us,
        R.drawable.store_more_apps,
        R.drawable.store_facebook,
        R.drawable.store_mail,
        R.drawable.dict_ic_search_fab,
    )

    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(color = MaterialTheme.colorScheme.onPrimary)
        ) {
            TabRow(
                selectedTabIndex = selectedTab.intValue,
                containerColor = Color(0xFF004F4E),
                contentColor = Color(0xFF02adab),
                indicator = {
                    SecondaryIndicator(
                        modifier = Modifier
                            .tabIndicatorOffset(it[selectedTab.intValue])
                            .padding(horizontal = 8.dp),
                        height = 4.dp,
                        color = MaterialTheme.colorScheme.onPrimary,
                    )
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                tabs.forEachIndexed { index, title ->
                    Tab(
                        selected = selectedTab.intValue == index,
                        onClick = { selectedTab.intValue = index },
                        text = {
                            Text(
                                text = title,
                                style = MaterialTheme.typography.labelSmall,
                                textAlign = TextAlign.Center,
                                maxLines = 1,
                                overflow = TextOverflow.Visible
                            )
                        },
                        //modifier = Modifier.weight(1f),
                        selectedContentColor = MaterialTheme.colorScheme.onPrimary,
                        unselectedContentColor = Color(0xFF02adab),
                    )
                }
            }

            if (selectedTab.intValue == 1) {
                ScrollableTabRow(
                    selectedTabIndex = selectedAlphabetTab.intValue,
                    containerColor = Color(0xFF004F4E),
                    contentColor = Color(0xFF02adab),
                    edgePadding = 8.dp,
                    indicator = {
                        SecondaryIndicator(
                            modifier = Modifier
                                .tabIndicatorOffset(it[selectedAlphabetTab.intValue]),
                            height = 3.dp,
                            color = MaterialTheme.colorScheme.onPrimary,
                        )
                    },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    alphabetTabs.forEachIndexed { index, letter ->
                        Tab(
                            selected = selectedAlphabetTab.intValue == index,
                            onClick = { selectedAlphabetTab.intValue = index },
                            text = {
                                Text(
                                    text = letter,
                                    style = MaterialTheme.typography.labelSmall,
                                    textAlign = TextAlign.Center,
                                )
                            },
                            selectedContentColor = MaterialTheme.colorScheme.onPrimary,
                            unselectedContentColor = Color(0xFF02adab),
                        )
                    }
                }
            }

            when (selectedTab.intValue) {
                0 -> Today()
                1 -> when (selectedAlphabetTab.intValue) {
                    0 -> Browse(emptyArray())   // 0th index is A
                    1 -> Browse(emptyArray())  // 1st index is B
                    // todo: handle for remaining alphabets
                }
                2 -> Search(emptyArray())   // todo: pass the searched words list as an array
                3 -> Favourites(emptyArray())
            }
        }

        // Animated Floating Button
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            if (!expanded) {
                FloatingActionButton(
                    onClick = { expanded = !expanded },
                    modifier = Modifier
                        .padding(end = 8.dp, bottom = 12.dp)
                        .align(Alignment.BottomEnd)
                        .size(48.dp),
                    containerColor = Color.Green,
                    shape = CircleShape
                ) {
                    Icon(
                        imageVector = Icons.Default.Search,
                        contentDescription = "Main Action",
                        tint = Color.White,
                        modifier = Modifier
                            .size(36.dp)
                    )
                }
            }

            AnimatedVisibility(visible = expanded) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                    modifier = Modifier
                        .padding(bottom = 24.dp)
                        .align(Alignment.BottomEnd)
                        .offset(
                            x = (fabSize * 7).value.dp,
                            y = (fabSize * 12).value.dp
                        )
                ) {
                    icons.forEachIndexed { index, icon ->
                        FloatingActionButton(
                            onClick = {
                                when (index) {
                                    0 -> {
                                        expanded = !expanded
                                    }
                                    1 -> {
                                        expanded = !expanded
                                    }
                                    2 -> {
                                        expanded = !expanded
                                    }
                                    3 -> {
                                        expanded = !expanded
                                    }
                                    4 -> {
                                        selectedTab.intValue = 2
                                        expanded = !expanded
                                    }
                                }
                            },
                            modifier = Modifier
                                .size(48.dp),
                            containerColor = Color.Green,
                            shape = CircleShape
                        ) {
                            Icon(
                                painter = painterResource(icon),
                                contentDescription = "Icon $index",
                                tint = Color.White,
                                modifier = Modifier
                                    .size(if (index == 4) 24.dp else 48.dp)
                            )
                        }
                    }
                }
            }
        }
    }
}


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun Today() {
    LazyColumn (
        modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth()
    ) {
        stickyHeader {
            Row(
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .padding(bottom = 12.dp)
            ) {
                Column(
                    modifier = Modifier
                        .weight(1f),
                ) {
                    Text(
                        text = "Text",
                        style = MaterialTheme.typography.headlineMedium,
                        color = Color(0xFF004f4e),
                        modifier = Modifier
                            .fillMaxWidth()
                    )
                    Text(
                        text = "Text",
                        style = MaterialTheme.typography.bodyLarge,
                        color = Color(0xFF004f4e),
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 4.dp)
                    )
                    Text(
                        text = "NOUN",
                        style = MaterialTheme.typography.bodySmall,
                        modifier = Modifier
                            .fillMaxWidth()
                    )
                }
                Image(
                    painter = painterResource(R.drawable.favorite_default),
                    contentDescription = "fav_icon",
                    modifier = Modifier
                        .size(32.dp)
                )
            }
        }

        item {
            Image(
                painter = painterResource(R.drawable.ic_morning),
                contentDescription = "fav_icon",
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
                    .size(36.dp)
            )
            Text(
                text = "Text",
                style = MaterialTheme.typography.titleSmall,
                modifier = Modifier
                    .fillMaxWidth()
            )
            Text(
                text = "NOUN",
                style = MaterialTheme.typography.titleSmall,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 8.dp)
            )
            Text(
                text = "Meaning",
                style = MaterialTheme.typography.titleLarge,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 24.dp)
            )
            Text(
                text = "Text",
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 8.dp)
            )
            Text(
                text = "Text",
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 12.dp)
            )
            Text(
                text = "Synonyms",
                style = MaterialTheme.typography.titleLarge,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 24.dp)
            )
            Text(
                text = "Text",
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 8.dp)
            )

            Text(
                text = "Antonyms",
                style = MaterialTheme.typography.titleLarge,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 24.dp)
            )
            Text(
                text = "Text",
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 8.dp)
            )
            Text(
                text = "See Also",
                style = MaterialTheme.typography.titleLarge,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 24.dp)
            )
        }
    }
}

@Composable
fun Browse(browsedWords: Array<String>) {
    LazyColumnContent(browsedWords)
}

@Composable
fun Search(searchedWords: Array<String>) {
    var searchText by remember { mutableStateOf("") }
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 8.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Image(
                imageVector = Icons.Default.Search,
                contentDescription = "Search Icon",
                modifier = Modifier
                    .padding(start = 8.dp)
                    .weight(1f)
                    .size(36.dp)
            )
            TextField(
                value = searchText,
                onValueChange = { searchText = it },
                placeholder = {
                    Text(
                        text = "Search...",
                    )
                },
                singleLine = true,
                colors = TextFieldDefaults.colors(
                    unfocusedContainerColor = Color.Transparent,
                    focusedContainerColor = Color.Transparent,
                    focusedTextColor = Color.Black,
                    unfocusedTextColor = Color.Black,
                    focusedIndicatorColor = Color.Black,
                    unfocusedIndicatorColor = Color(0xFF9E9E9E),
                ),
                modifier = Modifier
                    .weight(6f)
                    .wrapContentHeight()
            )
            Image(
                imageVector = Icons.Default.Clear,
                contentDescription = "Search Icon",
                modifier = Modifier
                    .padding(end = 8.dp)
                    .weight(1f)
                    .size(36.dp)
                    .clickable { searchText  = "" }
            )
        }
        LazyColumnContent(searchedWords, emptyStateText = "ஆங்கிலம் மற்றும் தமிழ் வார்த்தைகளை தேடலாம்.")
    }
}

@Composable
fun Favourites(favWords: Array<String>) {
    LazyColumnContent(favWords, emptyStateText = "வார்த்தைகள் எதுவும் இல்லை..")
}

@Composable
fun LazyColumnContent(
    items: Array<String>,
    emptyStateText: String = "வார்த்தைகள் எதுவும் இல்லை.."
) {
    if (items.isEmpty()) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = emptyStateText,
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.bodyMedium
            )
        }
    } else {
        CommonList(items = items.toList()) { }
    }
}


@Preview(showBackground = true, showSystemUi = true,)
@Composable
fun DictionaryPreview() {
    ComposeTamilCalendar2025Theme {
        Dictionary()
    }
}