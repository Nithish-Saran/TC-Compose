package com.apps.tc.tccompose2025.coffeeMeetup

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ScrollableTabRow
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRowDefaults.SecondaryIndicator
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.apps.tc.tccompose2025.App
import com.apps.tc.tccompose2025.Constant
import com.apps.tc.tccompose2025.dialog.CoffeeMeetDialog
import com.apps.tc.tccompose2025.models.Team
import com.apps.tc.tccompose2025.models.Venue
import com.apps.tc.tccompose2025.openMapLocation
import com.apps.tc.tccompose2025.sendEmail
import com.apps.tc.tccompose2025.ui.theme.ComposeTamilCalendar2025Theme
import com.apps.tc.tccompose2025.ui.theme.colorAccent
import com.apps.tc.tccompose2025.ui.theme.colorCommonText
import com.apps.tc.tccompose2025.ui.theme.colorGold
import com.apps.tc.tccompose2025.ui.theme.colorGoldBg
import com.apps.tc.tccompose2025.ui.theme.colorPrimaryDark
import com.apps.tc.tccompose2025.ui.theme.colorTextLite
import com.apps.tc.tccompose2025.ui.theme.colorTextTitle
import kotlinx.coroutines.launch

@Composable
fun CoffeeMeet(app: App) {
    val viewModel: MeetupViewModel = viewModel()
    val state = viewModel.meetupState.collectAsState().value
    val scope = rememberCoroutineScope()
    val selectedTab = remember { mutableIntStateOf(1) }
    val showDialog = remember { mutableStateOf(false) }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(color = colorGoldBg)
    ) {
        stickyHeader {
            ScrollableTabRow(
                modifier = Modifier
                    .fillMaxWidth(),
                edgePadding = 0.dp,
                selectedTabIndex = selectedTab.intValue,
                containerColor = colorGold,
                indicator = {
                    SecondaryIndicator(
                        modifier = Modifier.tabIndicatorOffset(
                            it[selectedTab.intValue]
                        ),
                        color = colorAccent,
                    )
                },
            ) {
                arrayOf("சந்திக்கும் இடங்கள்", "எங்கள் குழு").forEachIndexed { index, title ->
                    Tab(
                        selected = selectedTab.intValue == index,
                        onClick = { selectedTab.intValue = index },
                        text = {
                            Text(
                                modifier = Modifier
                                    .padding(8.dp),
                                text = title,
                                style = MaterialTheme.typography.titleSmall,
                            )
                        },
                        selectedContentColor = colorPrimaryDark,
                        unselectedContentColor = colorCommonText
                    )
                }
            }
        }
        when (state) {
            is MeetupState.Loading -> {
                item { /* Show loading UI */ }
            }

            is MeetupState.MeetingData -> {
                val venues = state.data.venues
                val team = state.data.teams
                when (selectedTab.intValue) {
                    0 -> {
                        items(items = venues) { venue ->
                            MeetupVenueView(data = venue) {
                                showDialog.value = true
                            }
                            if (showDialog.value) {
                                CoffeeMeetDialog(
                                    showDialog = showDialog.value,
                                    onConfirm = {
                                        showDialog.value = false
                                        sendEmail(app)
                                    },
                                    onDismiss = {showDialog.value = false}
                                )
                            }
                        }
                    }

                    1 -> {
                        items(items = team) { team ->
                            TeamInfoView(data = team)
                        }
                    }
                }
            }

            is MeetupState.Empty -> {
                item { Text("No meetups available") }
            }

            is MeetupState.Error -> {
                item { Text("Something went wrong") }
            }
        }
    }
    LaunchedEffect(Unit) {
        scope.launch {
            viewModel.getVenueData(app)
        }
    }
}

@Composable
fun MeetupVenueView(
    data: Venue,
    onRegister: () -> Unit
) {
    val context = LocalContext.current
    Spacer(modifier = Modifier.height(16.dp))
    Column(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        Text(
            text = data.location,
            style = MaterialTheme.typography.titleLarge,
            modifier = Modifier
                .padding(horizontal = 8.dp),
            color = colorTextTitle
        )
        Text(
            text = data.shopName,
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier
                .padding(horizontal = 8.dp),
            color = colorTextTitle
        )
        Text(
            text = "${data.date}, ${data.time}",
            style = MaterialTheme.typography.labelMedium,
            modifier = Modifier
                .padding(horizontal = 8.dp),
            color = colorCommonText
        )
        AsyncImage(
            model = "${Constant.MeetupURL}${data.bannerUrl}.png",
            contentDescription = null,
            modifier = Modifier
                .padding(vertical = 8.dp)
                .fillMaxWidth(),
            contentScale = ContentScale.Crop
        )
        Text(
            text = data.address,
            style = MaterialTheme.typography.labelMedium,
            modifier = Modifier
                .padding(horizontal = 8.dp),
            color = colorCommonText
        )
        Row(
            modifier = Modifier
                .padding(8.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Button(
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.buttonColors(colorPrimaryDark),
                onClick = {
                    openMapLocation(context, data.locationLink)
                }
            ) {
                Text(
                    text = "கூகிள் மேப்",
                    style = MaterialTheme.typography.labelSmall,
                    modifier = Modifier
                        .padding(horizontal = 8.dp),
                    color = colorTextLite
                )
            }
            Spacer(modifier = Modifier.width(16.dp))
            Button(
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.buttonColors(colorPrimaryDark),
                onClick = { onRegister() }
            ) {
                Text(
                    text = "சந்திக்க விருப்பம்",
                    style = MaterialTheme.typography.labelSmall,
                    modifier = Modifier
                        .padding(horizontal = 8.dp),
                    color = colorTextLite
                )
            }
        }
        Divider(
            color = Color.LightGray,
            thickness = 1.dp,
            modifier = Modifier.padding(top = 8.dp)
        )
    }
}

@Composable
fun TeamInfoView(data: Team) {
    Row(
        modifier = Modifier
            .background(color = colorTextLite)
            .padding(16.dp)
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        AsyncImage(
            model = data.imageUrl,
            contentDescription = null,
            modifier = Modifier
                .padding(end = 8.dp)
                .clip(RoundedCornerShape(50))
                .size(64.dp),
            contentScale = ContentScale.Crop
        )
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.Start
        ) {
            Text(
                text = data.title,
                style = MaterialTheme.typography.titleSmall,
                modifier = Modifier
                    .padding(horizontal = 8.dp),
                color = colorTextTitle
            )
            Spacer(modifier = Modifier.height(6.dp))
            Text(
                text = data.name,
                style = MaterialTheme.typography.labelMedium,
                modifier = Modifier
                    .padding(horizontal = 8.dp),
                color = colorTextTitle
            )
        }
    }
    Divider(
        color = colorCommonText,
        thickness = 1.dp,
    )
}


@Preview(showBackground = true, showSystemUi = true)
@Composable
fun MeetupPreview() {
    ComposeTamilCalendar2025Theme {
        CoffeeMeet(App())
    }
}