package com.apps.tc.tccompose2025

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.apps.tc.tccompose2025.ui.theme.actMarMatchLite
import com.apps.tc.tccompose2025.view.Header

@Composable
fun Numerology(onBackReq: () -> Unit) {
    var selectedOption = remember { mutableIntStateOf(0) }
    val options = listOf("பெயரை கணிக்க", "பொருத்தம் பார்க்க")
    var name =  remember { mutableStateOf("") }
    var dob = remember { mutableStateOf("") }

    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
        ) {
            Header(
                heading = "எண் கணித பலன்கள்",
                bgColor = actMarMatchLite,
                textColor = Color.White,
            ) { onBackReq() }

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Start,
                verticalAlignment = Alignment.CenterVertically
            ) {
                options.forEachIndexed { index, text ->
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        RadioButton(
                            selected = selectedOption.intValue == index,
                            onClick = { selectedOption.intValue = index },
                        )
                        Text(
                            text = text,
                            style = MaterialTheme.typography.bodyMedium,
                        )
                    }

                }
            }

            // Input Fields for "பெயரை கணிக்க"
            if (selectedOption.intValue == 0) {
                Column(
                    modifier = Modifier
                        .padding(horizontal = 8.dp)
                        .fillMaxWidth(),
                        //.padding(horizontal = 16.dp),
                    verticalArrangement = Arrangement.Center
                ) {
                    TextField(
                        value = name.value,
                        onValueChange = { name.value = it },
                        label = {
                            Text(
                                text = "தங்களது பெயர் (ஆங்கிலத்தில்)"
                            )
                        },
                        modifier = Modifier.fillMaxWidth(),
                        singleLine = true
                    )

                    OutlinedTextField(
                        value = dob.value,
                        onValueChange = { dob.value = it },
                        label = { Text("பிறந்த தேதி (உதா - DDMMYYYY)") },
                        modifier = Modifier.fillMaxWidth(),
                        singleLine = true,
                        keyboardOptions = KeyboardOptions.Default.copy(
                            keyboardType = KeyboardType.Number
                        )
                    )
                }
            }
        }
        Icon(
            painter = painterResource(R.drawable.share),
            contentDescription = "filter",
            modifier = Modifier
                .padding(bottom = 8.dp, end = 8.dp)
                .align(Alignment.BottomEnd)
                .background(
                    color = Color.Green,
                    shape = CircleShape
                )
                .clickable {  }
                .padding(8.dp)
                .size(32.dp),
            tint = Color.White
        )
    }
}

@Composable
@Preview(showSystemUi = true, showBackground = true)
fun PreviewNumerology() {
    Numerology(){}
}