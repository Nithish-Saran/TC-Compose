package com.apps.tc.tccompose2025.numerology

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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import com.apps.tc.tccompose2025.App
import com.apps.tc.tccompose2025.R
import com.apps.tc.tccompose2025.dialog.CommonAlertDialog
import com.apps.tc.tccompose2025.models.DOBMatch
import com.apps.tc.tccompose2025.ui.theme.actMarMatchLite
import com.apps.tc.tccompose2025.view.Header
import kotlinx.coroutines.launch

@Composable
fun Numerology(app: App, onBackReq: () -> Unit, onReturn: (String) -> Unit) {
    var selectedOption = remember { mutableIntStateOf(0) }
    val options = listOf("பெயரை கணிக்க", "பொருத்தம் பார்க்க")
    var yourName by remember { mutableStateOf("") }
    var matchName by remember { mutableStateOf("") }
    var dob by remember { mutableStateOf("") }
    val scrollState = rememberScrollState()

    val viewModel: NumerologyViewModel = viewModel()
    val state = viewModel.numerologyViewState.collectAsState().value
    val dialogState = viewModel.dialogState.collectAsState().value
    val scope = rememberCoroutineScope()

    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(scrollState)
        ) {
            Header(
                heading = "எண் கணித பலன்கள்",
                bgColor = actMarMatchLite,
                textColor = Color.White,
            ) { onBackReq() }

            Row(
                modifier = Modifier
                    .padding(vertical = 8.dp, horizontal = 8.dp)
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.Start,
                verticalAlignment = Alignment.CenterVertically
            ) {
                options.forEachIndexed { index, text ->
                    Row(
                        modifier = Modifier
                            .weight(1f),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        RadioButton(
                            selected = selectedOption.intValue == index,
                            onClick = {
                                selectedOption.intValue = index
                                if (index == 0) {
                                    viewModel.showDobView()
                                } else {
                                    viewModel.showNameMatchView()
                                }
                            },
                        )
                        Text(
                            text = text,
                            style = MaterialTheme.typography.labelLarge,
                        )
                    }
                }
            }

            when (val result = state) {
                is NumerologyViewState.Loading -> {
                    // Optional loading indicator here
                }

                is NumerologyViewState.DobMatchState -> {
                    InputText(
                        input = yourName,
                        label = "தங்களது பெயர் (ஆங்கிலத்தில்)",
                        keyBoard = KeyboardType.Text,
                        onValueChange = { yourName = it }
                    )

                    InputText(
                        input = dob,
                        label = "பிறந்த தேதி (உதா - DDMMYYYY)",
                        keyBoard = KeyboardType.Number,
                        onValueChange = { dob = it }
                    )
                    CommonView(
                        onMatchResultReq = {
                            viewModel.getDobMatch(yourName, dob)
                        },
                        onHtmlReq = {onReturn("general/about_numerology.html")}
                    )

                    if (state is NumerologyViewState.DobMatchState.DobMatch) {
                        val dobMatch = viewModel.getResultForDOB(result.dobValue)
                        DobMatchView(result.dobpothuPalan, dobMatch, result.dobValue)
                    }
                }

                is NumerologyViewState.NameMatchState -> {
                    InputText(
                        input = yourName,
                        label = "தங்களது பெயர் (ஆங்கிலத்தில்)",
                        keyBoard = KeyboardType.Text,
                        onValueChange = { yourName = it }
                    )

                    InputText(
                        input = matchName,
                        label = "பொருத்தம் பார்க்க வேண்டிய பெயர்",
                        keyBoard = KeyboardType.Text,
                        onValueChange = { matchName = it }
                    )

                    Text(
                        text = stringResource(R.string.name_match_desc),
                        style = MaterialTheme.typography.bodyMedium,
                        modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
                    )

                    CommonView(
                        onMatchResultReq = {
                            viewModel.getNameMatch(yourName, matchName, app)
                        },
                        onHtmlReq = {onReturn("general/about_numerology.html")}
                    )

                    if (state is NumerologyViewState.NameMatchState.NameMatch) {
                        NameMatchView(
                            nameMatch = result.result,
                            namePothuPalan = result.namepothuPalan
                        )
                    }
                }
            }
        }

        if (dialogState is DialogState.Error) {
            CommonAlertDialog(
                title = dialogState.error.first,
                desc = dialogState.error.second,
                showDialog = true,
                confirmText = "OK",
                onConfirm = {
                    viewModel.closeDialog()
                }
            )
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
                .clickable { }
                .padding(8.dp)
                .size(32.dp),
            tint = Color.White
        )
    }
    LaunchedEffect(Unit) {
        scope.launch {
            viewModel.showDobView()
        }
    }
}

@Composable
private fun CommonView(onMatchResultReq: () -> Unit, onHtmlReq: () -> Unit) {
    Row(
        modifier = Modifier
            .padding(horizontal = 16.dp)
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Button(
            onClick = {
                onMatchResultReq()
            },
            modifier = Modifier.weight(0.9f),
            shape = RoundedCornerShape(8.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = actMarMatchLite,
                contentColor = Color.White
            )
        ) {
            Text(
                text = "சமர்ப்பிக்கவும்",
                style = MaterialTheme.typography.labelMedium
            )
        }

        Button(
            onClick = {
                onHtmlReq()
            },
            modifier = Modifier.weight(1f),
            shape = RoundedCornerShape(8.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = actMarMatchLite,
                contentColor = Color.White
            )
        ) {
            Text(
                text = "About Numerology",
                style = MaterialTheme.typography.labelMedium
            )
        }
    }

    HorizontalDivider(
        modifier = Modifier
            .padding(8.dp),
        thickness = 3.dp,
        color = Color.Black
    )
}

@Composable
fun DobMatchView(pothuPalan: String, matchResult: DOBMatch?, dobValue: Int) {

    PalanView(title = "பொதுவான கணிப்பு", desc = pothuPalan)

    matchResult.let {
        PalanView(title = "ராசியான எண் ", desc = dobValue.toString())
        PalanView(title = "ராசியான ரத்தினம்", desc = it?.stone ?: "-")
        PalanView(title = "ராசியான திசை", desc = it?.direction ?: "-")
        PalanView(title = "நலம் தரும் தொழில்கள்", desc = it?.business ?: "-")
        PalanView(title = "ராசியான கிழமைகள்", desc = it?.days ?: "-")
        PalanView(title = "ஆகாத கிழமைகள்", desc = it?.noDays ?: "-")
        PalanView(title = "ராசியான தேதிகள்", desc = it?.dates ?: "-")
        PalanView(title = "இந்த எண்ணில் உள்ள நெகட்டிவ், பாசிட்டிவ் விஷயங்கள்", desc = it?.events ?: "-")
        PalanView(title = "நீங்கள் வணங்க வேண்டிய தெய்வம்", desc = it?.gods ?: "-")
    }
}

@Composable
fun NameMatchView(nameMatch: Array<Pair<String, String>>, namePothuPalan: String) {
    PalanView(title = "பொதுவான கணிப்பு", desc = namePothuPalan)
    nameMatch.forEach {
        PalanView(title = it.first, desc = it.second)
    }
}

@Composable
private fun InputText(
    input: String, label: String, keyBoard: KeyboardType,
    onValueChange: (String) -> Unit
) {
    OutlinedTextField(
        value = input,
        onValueChange = { onValueChange(it) },
        placeholder = {
            Text(
                text = label,
                style = MaterialTheme.typography.bodySmall,
            )
        },
        modifier = Modifier
            .padding(horizontal = 16.dp, vertical = 4.dp)
            .fillMaxWidth(),
        singleLine = true,
        keyboardOptions = KeyboardOptions.Default.copy(
            keyboardType = keyBoard
        ),
        shape = RoundedCornerShape(8.dp),
        colors = TextFieldDefaults.colors(
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            focusedContainerColor = Color.Gray.copy(alpha = 0.2f),
            unfocusedContainerColor = Color.Gray.copy(alpha = 0.2f),
            focusedTextColor = Color.Black,
        )
    )
}

@Composable
private fun PalanView(title: String, desc: String) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp),
    ) {
        Text(
            text = title,
            style = MaterialTheme.typography.titleMedium,
            color = actMarMatchLite,
            modifier = Modifier.padding(8.dp)
        )

        Text(
            text = desc,
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier.padding(8.dp)
        )
    }
}

@Composable
@Preview(showSystemUi = true, showBackground = true)
fun PreviewNumerology() {
    //Numerology(App()) {}
}