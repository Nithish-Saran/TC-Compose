package com.apps.tc.tccompose2025.dialog

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.apps.tc.tccompose2025.App
import com.apps.tc.tccompose2025.R
import com.apps.tc.tccompose2025.ui.theme.colorPrimary

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BabyNameDialog(
    app: App,
    showDialog: Boolean,
    selectedMenu: Int,
    onReturn: (String, Int, Boolean) -> Unit,
    onDismiss: () -> Unit
) {
    var expanded by remember { mutableStateOf(false) }
    val options = app.resources.getStringArray(R.array.natchathiram_array)
    val selectedStar = remember { mutableStateOf("நட்சத்திரம் தேர்வு செய்க")}
    val selectedStarIndex = remember { mutableIntStateOf(-1) }
    val title = remember { mutableStateOf("தேர்வு செய்யவும்") }
    val color = remember { mutableStateOf(Color.White) }

    fun validateAndReturn(isGirl: Boolean) {
        val isStarRequired = selectedMenu == 0
        val isValid = !isStarRequired || selectedStarIndex.intValue >= 0

        if (isValid) {
            title.value = "தேர்வு செய்யவும்"
            color.value = Color.White
            onReturn(selectedStar.value, selectedStarIndex.intValue, isGirl)
            onDismiss()
        } else {
            title.value = "நட்சத்திரத்தை தேர்வு செய்யவும்"
            color.value = Color.Red
        }
    }


    if (showDialog) {
        AppDialog(
            content = {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(
                            color = Color.White,
                            shape = RoundedCornerShape(8.dp)
                        ),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = title.value,
                        style = MaterialTheme.typography.bodyLarge,
                        color = color.value,
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(Color(0xFF6A1B9A))
                            .padding(8.dp),
                        textAlign = TextAlign.Center
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        text = "குழந்தையின் நட்சத்திரம்:",
                        style = MaterialTheme.typography.bodyLarge,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp)
                    )

                    ExposedDropdownMenuBox(
                        expanded = expanded,
                        onExpandedChange = { expanded = it },
                        modifier = Modifier
                            .padding(horizontal = 8.dp)
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .menuAnchor()
                                .border(
                                    width = 2.dp,
                                    color = Color(0xFF6A1B9A),
                                    shape = RoundedCornerShape(4.dp))
                                .clickable { expanded = true }
                                .padding(8.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = selectedStar.value,
                                style = MaterialTheme.typography.bodyMedium,
                                modifier = Modifier.weight(1f)
                            )
                            Icon(
                                imageVector = Icons.Default.ArrowDropDown,
                                contentDescription = "Dropdown",
                            )
                        }

                        ExposedDropdownMenu(
                            expanded = expanded,
                            onDismissRequest = { expanded = false },
                            modifier = Modifier
                                .wrapContentWidth(),
                            containerColor = colorPrimary
                        ) {
                            options.forEachIndexed { index, option ->
                                DropdownMenuItem(
                                    text = {
                                        Column(
                                            verticalArrangement = Arrangement.Center
                                        ) {
                                            Text(
                                                text = option,
                                                textAlign = TextAlign.Start,
                                                modifier = Modifier
                                                    .padding(vertical = 8.dp)
                                                    .fillMaxWidth()
                                            )
                                            HorizontalDivider()
                                        }

                                    },
                                    onClick = {
                                        selectedStar.value = option
                                        selectedStarIndex.intValue = index
                                        if (selectedMenu > 0 || selectedStarIndex.intValue >= 0) {
                                            title.value = "தேர்வு செய்யவும்"
                                            color.value = Color.White
                                        } else {
                                            title.value = "நட்சத்திரத்தை தேர்வு செய்யவும்"
                                            color.value = Color.Red
                                        }
                                        expanded = false
                                    },
                                    modifier = Modifier
                                        .fillMaxWidth()
                                )
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        text = "குழந்தையின் பாலினம்",
                        style = MaterialTheme.typography.bodyMedium,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(start = 8.dp)
                    )

                    Row(
                        modifier = Modifier
                            .padding(vertical = 8.dp, horizontal = 16.dp)
                            .fillMaxWidth()
                            .border(
                                width = 1.dp,
                                color = Color.Gray,
                            ),
                        horizontalArrangement = Arrangement.SpaceEvenly
                    ) {
                        GenderSelectionButton(
                            "ஆண் பெயர்கள்",
                            R.drawable.baby_boy
                        ) {
                            validateAndReturn(false)
                        }
                        VerticalDivider(
                            modifier = Modifier
                                .height(IntrinsicSize.Max)
                                .width(1.dp)
                                .background(Color.Black) // Ensure visibility
                        )

                        GenderSelectionButton(
                            "பெண் பெயர்கள்",
                            R.drawable.baby_girl
                        ) {
                            validateAndReturn(true)
                        }
                    }
                }
            }
        ) { onDismiss()}
    }

    LaunchedEffect(showDialog) {
        if (showDialog) {
            selectedStar.value = "நட்சத்திரம் தேர்வு செய்க"
            selectedStarIndex.intValue = -1
            title.value = "தேர்வு செய்யவும்"
            color.value = Color.White
            expanded = false
        }
    }
}

@Composable
fun GenderSelectionButton(text: String, icon: Int, onRetrun: () -> Unit) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .clickable {onRetrun()}
    ) {
        Image(
            painter = painterResource(id = icon), // Replace with actual drawable resource
            contentDescription = text,
            modifier = Modifier
                .size(128.dp)
                .clip(CircleShape)
                .padding(8.dp)
        )
        Text(
            text = text,
            style = MaterialTheme.typography.labelMedium,
            modifier = Modifier
                .padding(bottom = 8.dp)
        )
    }
}

@Composable
@Preview(showSystemUi = true, showBackground = true)
fun PreviewDialog() {
    BabyNameDialog(
        showDialog = TODO(),
        onReturn = TODO(),
        onDismiss = TODO(),
        selectedMenu = TODO(),
        app = TODO()
    )
}
