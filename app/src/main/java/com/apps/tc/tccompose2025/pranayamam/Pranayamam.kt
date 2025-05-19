package com.apps.tc.tccompose2025.pranayamam

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import com.apps.tc.tccompose2025.App
import com.apps.tc.tccompose2025.dialog.ListDialog
import com.apps.tc.tccompose2025.ui.theme.actBabyNamesDark
import com.apps.tc.tccompose2025.ui.theme.colorPrimary
import com.apps.tc.tccompose2025.view.GridMenu
import com.apps.tc.tccompose2025.view.Header
import kotlinx.coroutines.launch

@Composable
fun Pranayamam(app: App, onReturn: (String) -> Unit, onBackReq: () -> Unit) {

    val viewModel: PranayamamViewModel = viewModel()
    val state = viewModel.pranayamamState.collectAsState().value
    val scope = rememberCoroutineScope()
    val showDialog = remember { mutableStateOf(false) }
    var selectedMenuId = remember { mutableIntStateOf(-1) }

    val pranayamamFaqsOne = arrayOf(
        "பிராணாயாமம் என்றால் என்ன?",
        "பிராணாயாமத்தின் வகைகள் யாவை?",
        "பிராணாயாமத்தை அனுதினமும் செய்து வருவதால் என்ன பயன்?",
        "பிராணாயாமத்தின் விதிமுறைகளைப் பற்றி விரிவாகக் கூறவும்?",
        "பிராணாயாமத்தை எங்கு, எப்படி, எப்போது செய்யலாம்?",
        "பிராணயாமத்தில் ஆடைக் கட்டுப்பாடு மற்றும் கையாள வேண்டிய ஆசன முறைகள் பற்றி விளக்குக?",
        "பிராணாயாமத்தை யார், யார் தவிர்த்தல் நலம்?",
        "பிராணாயாமத்தில் மூச்சு விடும் நிலை பற்றி விளக்குக?",
        "பிராணயாமத்தில் பூரகம், கும்பகம், ரேசகம் என்றால் என்ன?",
        "அடிப்படையில் ஒரு பிராணாயாமத்தை எவ்வாறு செய்ய வேண்டும்?",
        "ஆலோம் விலோம் பிராணாயாமம் (நாடி சுத்தி அல்லது மத்திம பிராணாயாமம்) செய்முறை பற்றி சுருக்கமாகக் கூறுக?",
        "கபாலபதிப் பிராணாயாமத்தை எவ்வாறு செய்யலாம்?",
        "அக்னிசார் பிராணாயாமம் செய்வது எப்படி?",
        "பஸ்திரிகா பிராணாயாமம் செய்வது எப்படி?",
        "சூர்ய நாடிப் பிராணாயாமம் செய்வது எப்படி?",
        "சந்திர நாடிப் பிராணாயாமம் செய்வது எப்படி?",
        "உஜ்ஜயி பிராணாயாமம் செய்வது எப்படி?",
        "ப்ராமரி பிராணாயாமம் செய்வது எப்படி?"
    )

    val pranayamamFaqsTwo = arrayOf(
        "தூய்மையான உடை", "சரியான உடல்மொழி", "அமைதியன சூழல்", "வெறும் வயிற்றில் ஆசனம்",
        "ஓய்வு", "சுவாசம்", "சரியான யுத்தி", "நிலைப்பு தன்மை",
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = actBabyNamesDark)
    ) {
        Header(
            heading = "பிராணாயாமம்",
            bgColor = Color(0xFF36006E),
            textColor = colorPrimary,
            onBackReq = { onBackReq() }
        )
        when (state) {
            is PranayamamViewState.Loading -> {

            }

            is PranayamamViewState.Menu -> {
                GridMenu(state.menu.toList()) {
                    selectedMenuId.intValue = it
                    when (it) {
                        0, 1 -> showDialog.value = true
                        2 -> onReturn("pranayamam/pranayamam_benefits.html")
                    }
                }
                when (selectedMenuId.intValue) {
                    0 -> {
                        if (showDialog.value) {
                            ListDialog(
                                header = "பிராணாயாமம் அறிந்தும் அறியாததும்",
                                titles = pranayamamFaqsOne.toList(),
                                showDialog = showDialog.value,
                                onDismiss = {
                                    showDialog.value = false
                                },
                                onReturn = {
                                    showDialog.value = false
                                    onReturn("pranayamam/pranayamam_${it+1}.html")
                                }
                            )
                        }
                    }

                    1 -> {
                        if (showDialog.value) {
                            ListDialog(
                                header = "ஆரம்ப நிலை பிராணாயாமம்",
                                titles = pranayamamFaqsTwo.toList(),
                                showDialog = showDialog.value,
                                onDismiss = {
                                    showDialog.value = false
                                },
                                onReturn = {
                                    showDialog.value = false
                                    onReturn("pranayamam/pranayamam_10${it+1}.html")
                                }
                            )
                        }
                    }
                }
            }
        }
    }

    LaunchedEffect(Unit) {
        scope.launch {
            viewModel.getPranayamamMenu(app)
        }
    }
}

@Composable
@Preview(showSystemUi = true, showBackground = true)
fun PreviewPranayama() {
    //Pranayamam {}
}