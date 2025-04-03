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
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
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
import com.apps.tc.tccompose2025.R
import com.apps.tc.tccompose2025.ui.theme.colorPrimary

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BabyNameDialog() {
    var expanded by remember { mutableStateOf(false) }
    val options = listOf("நட்சத்திரம் 1", "நட்சத்திரம் 2", "நட்சத்திரம் 3") // Example options
    var selectedOption by remember { mutableStateOf(options[0]) }

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
                    text = "தேர்வு செய்யவும்",
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.surface,
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
                            text = selectedOption,
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
                        options.forEach { option ->
                            DropdownMenuItem(
                                text = {
                                    Text(
                                        text = option,
                                        textAlign = TextAlign.Center,
                                        modifier = Modifier
                                            .fillMaxWidth()
                                    )
                                   },
                                onClick = {
                                    selectedOption = option
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
                    )
                    VerticalDivider(
                        modifier = Modifier
                            .height(IntrinsicSize.Max)
                            .width(1.dp)
                            .background(Color.Black) // Ensure visibility
                    )

                    GenderSelectionButton(
                        "பெண் பெயர்கள்",
                        R.drawable.baby_girl
                    )
                }
            }
        }
    ) { }
}

@Composable
fun GenderSelectionButton(text: String, icon: Int) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
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
    BabyNameDialog()
}
