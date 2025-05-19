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
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.apps.tc.tccompose2025.R
import com.apps.tc.tccompose2025.WebScreenMode
import com.apps.tc.tccompose2025.models.DOBMatch
import com.apps.tc.tccompose2025.ui.theme.actMarMatchLite
import com.apps.tc.tccompose2025.view.Header
import com.apps.tc.tccompose2025.view.WebScreen
import kotlinx.coroutines.launch

@Composable
fun Numerology(onBackReq: () -> Unit) {
    var selectedOption = remember { mutableIntStateOf(0) }
    val options = listOf("பெயரை கணிக்க", "பொருத்தம் பார்க்க")
    var yourName by remember { mutableStateOf("") }
    var matchName by remember { mutableStateOf("") }
    var dob by remember { mutableStateOf("") }
    val scrollState = rememberScrollState()
    var showWebView by remember { mutableStateOf(false) }

    val viewModel: NumerologyViewModel = viewModel()
    val state = viewModel.numerologyViewState.collectAsState().value
    val scope = rememberCoroutineScope()

    if (showWebView) {
        WebScreen(
            mode = WebScreenMode.Assets,
            uri = "general/about_numerology.html"
        )
    }
    else {
        showWebView = false
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
                                onClick = { selectedOption.intValue = index },
                            )
                            Text(
                                text = text,
                                style = MaterialTheme.typography.labelLarge,
                            )
                        }
                    }
                }

                InputText(
                    input = yourName,
                    label = "தங்களது பெயர் (ஆங்கிலத்தில்)",
                    keyBoard = KeyboardType.Text,
                    onValueChange = { yourName = it }
                )

                if (selectedOption.intValue == 0) {
                    InputText(
                        input = dob,
                        label = "பிறந்த தேதி (உதா - DDMMYYYY)",
                        keyBoard = KeyboardType.Number,
                        onValueChange = { dob = it }
                    )
                } else {
                    InputText(
                        input = matchName,
                        label = "பொருத்தம் பார்க்க வேண்டிய பெயர்",
                        keyBoard = KeyboardType.Text,
                        onValueChange = { matchName = it }
                    )

                    Text(
                        text = "* தங்கள் துணையின் பெயரோ, ஊரின் பெயரோ, தொழில் நிறுவனத்தின் " +
                                "பெயரோ, தொழில் புரிய முனையும் பங்குதாரர் பெயரோ, அல்லது தாங்கள்" +
                                "வாங்கவிருக்கும் வாகனத்தின் பெயரென நீங்கள் எதன் கூடவும் உங்கள்" +
                                "பெயர் பொருத்தம் பார்க்கலாம்.",
                        style = MaterialTheme.typography.bodyMedium,
                        modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
                    )
                }

                Row(
                    modifier = Modifier
                        .padding(horizontal = 8.dp)
                        .fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Button(
                        onClick = {
                            if (selectedOption.intValue == 0) {
                                scope.launch {
                                    viewModel.getDobMatch(yourName, dob)
                                }
                            }
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
                            showWebView = true
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
                when (state) {
                    is NumerologyViewState.Loading -> {}
                    is NumerologyViewState.DobMatch -> {
                        if (selectedOption.value == 0) {
                            val dobMatch = getResultForDOB(state.dobValue)

                            var result =
                                "தங்கள் பிறந்த தேதியை வைத்துக் கணக்கிடப்பட்டு வந்த விதி எண் '${state.dobValue}', " +
                                        "உங்கள் பெயரின் கூட்டு எண் '${state.nameVal}'.\n\n"
                            result += if (state.areFriendly)
                                "தங்கள் பெயரின் கூட்டுத் தொகை, தங்களது விதி எண் உடன் நட்பு ரீதியாகப் பொருந்துகிறது."
                            else
                                "தங்கள் பெயரின் கூட்டுத் தொகை, தங்கள் விதி எண்ணிற்கு விரோதமாக அமைந்து உள்ளது." +
                                        "அதனை சரியான அலைவரிசையில் தாங்கள் அமைத்துக் கொள்வது நல்லது."

                            PalanView(
                                title = "பொதுவான கணிப்பு",
                                desc = result
                            )
                            dobMatch?.let {
                                PalanView(title = "ராசியான எண் ", desc = state.dobValue.toString())
                                PalanView(title = "ராசியான ரத்தினம்", desc = it.stone)
                                PalanView(title = "ராசியான திசை", desc = it.direction)
                                PalanView(title = "நலம் தரும் தொழில்கள்", desc = it.business)
                                PalanView(title = "ராசியான கிழமைகள்", desc = it.days)
                                PalanView(title = "ஆகாத கிழமைகள்", desc = it.noDays)
                                PalanView(title = "ராசியான தேதிகள்", desc = it.dates)
                                PalanView(title = "இந்த எண்ணில் உள்ள நெகட்டிவ், பாசிட்டிவ் விஷயங்கள்", desc = it.events)
                                PalanView(title = "நீங்கள் வணங்க வேண்டிய தெய்வம்", desc = it.gods)
                            }
                        }

                    }
                    is NumerologyViewState.Success -> TODO()
                    is NumerologyViewState.Error -> TODO()
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
                    .clickable { }
                    .padding(8.dp)
                    .size(32.dp),
                tint = Color.White
            )
        }
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

private fun getResultForDOB(dob: Int): DOBMatch? = when (dob) {
    1 -> DOBMatch(
        stone = "மாணிக்கம்",
        direction = "கிழக்கு",
        business = "உங்கள் நாயகன் சூரியன் ஆவார். எனவே சட்டத் துறை, அரசியல், காவல், மின்சாரம் சம்மந்தமான தொழில், அறுவை சிகிச்சை நிபுணர், பட்டாசு தொழில், அறிவியல் ஆராய்ச்சி, போக்குவரத்து துறை அல்லது அது சார்ந்த தொழில்கள், தலைவர் அல்லது தலைமை தாங்கும் பதவி போன்ற இவை அனைத்துமே சூரியனை அடிப்படையாக கொண்ட தொழில்கள் ஆகும். இவை அனைத்துமே ஒன்றாம் எண் ஆதிக்கத்தில் பிறந்தவர்களுக்கு ஏற்ற தொழில் ஆகும்.",
        days = "ஞாயிறு, செவ்வாய், வியாழன்",
        noDays = "சனி",
        dates = "1, 10, 19, 28, 9, 18, 27",
        events = "சூரியனின் ஆதிக்கத்தில் பிறந்த நீங்கள் உஷ்ணமான தேகத்தை கொண்டவர்கள். அடிக்கடி உணர்ச்சி வசப்படுபவர்களாக நீங்கள் இருக்க வாய்ப்பு உண்டு. இதனால் இருதயம் சம்மந்தமான பிரச்சனைகள், ரத்த கொதிப்பு போன்ற நோய்கள் கூட உங்களுக்கு வரக் கூடும். சூரியனின் ஆதிக்கத்தில் பிறந்த நீங்கள் தலைமை பொறுப்பு, ஆன்மீக துறை  ஆகிய இந்த இரண்டிலுமே சிறப்புடன் விளங்குவீர்கள். என்றாலும் தாய், தந்தையுடன் இணக்கமாக உறவு வைத்துக் கொள்வது சற்று கடினம். உங்களை முகஸ்துதி செய்தால் போதும் எளிதில் உங்கள் அன்பை சம்பாதித்து விடலாம். புகழுக்கு ஆசை படுபவர்கள் என்றும் உங்களை சொல்லலாம். பெரும்பாலும் பேச்சினால் கெடுவீர்கள்.",
        gods = "சிவ பெருமான் வழிபாடு வெற்றியை தரும்."
    )

    2 -> DOBMatch(
        stone = "முத்து",
        direction = "வட மேற்கு",
        business = "உங்கள் நாயகன் சந்திரன் ஆவார். எனவே நீர் சம்மந்தமான தொழில்கள் (குளிர்பானங்கள்), வண்டி, வாகனங்களை அடிப்படையாக கொண்ட தொழில்கள், ஹோட்டல், தரகு, பத்திரிக்கை துறை, பாடகர், கவிஞர் என்ற இதில் ஏதேனும் ஒன்றை தொழிலாக கொண்டால் இரண்டாம் எண் காரர்கள் அதில் சாதிப்பார்கள்.",
        days = "திங்கள், செவ்வாய்",
        noDays = "புதன்",
        dates = "2, 11, 20, 29, 9, 18, 27",
        events = "சந்திரனின் ஆதிக்கத்தில் பிறந்த நீங்கள் பேச்சினால் அனைவரையும் கவர்ந்து விடுவீர்கள். உங்களுக்கு நினைவாற்றல் அதிகம். பேச்சினால் காரியத்தை சாதித்து விடுவீர்கள். எனினும் சுயநலக் காரியவாதி. உடல் மெலிதாக இருந்தாலும் ஒரு கட்டத்தில் உடல் எடை கூடி கொழுப்பு, இருதயம், ரத்த அழுத்தம் போன்ற நோய்களால் தாக்கப்படலாம். பண விஷயத்தில் மிகுந்த ஜாக்கிரதை கொண்டவர்கள் நீங்கள். சந்திரனின் ஆதிக்கத்தில் பிறந்து இருப்பதால் சிலர் பெண்கள் விஷயத்தில் கெட்ட பெயர் எடுக்க நேரிடலாம். தோல் சம்மந்தமான பிரச்சனைகள் கூட உங்களுக்கு எதிர்காலத்தில் வரக் கூடும். கவனம்.",
        gods = "பார்வதி தேவி அல்லது அம்பாள் வழிபாடு நல்லது."
    )

    3 -> DOBMatch(
        stone = "கனகபுஷ்பராகம்",
        direction = "தெற்கு",
        business = "உங்கள் நாயகன் குரு ஆவார். எனவே கல்வித் துறை, சமூகத் தொண்டுகள், தூதுவர்கள், தத்துவம், வங்கி , விளம்பரத் துறை, ஆசிரியர் என இதில் ஏதேனும் ஒரு துறையை குருவின் ஆதிக்கத்தில் இருக்கும் மூன்றாம் எண் காரர்கள் தேர்ந்தெடுத்து செய்தால் சிறப்புடன் இருப்பார்கள்.",
        days = "ஞாயிறு, திங்கள், செவ்வாய், வியாழன்",
        noDays = "வெள்ளி",
        dates = "3, 12, 30, 1, 10, 19, 28, 9, 18, 27",
        events = "குரு பகவானின் ஆதிக்கத்தில் பிறந்த நீங்கள் பெருந்தன்மை கொண்டவர்கள். யாரையும் பகைத்துக் கொள்ள விரும்பாதவர்கள். எனினும் பிறருக்கு அறிவுரை கூறி கெட்ட பெயர் எடுக்கலாம். சமூக அக்கறை கொண்டவர்கள். சிலர் ஒரு படி மேலே போய் வீட்டிற்கு உதவி செய்வதை விட சமூகத்திற்கு அதிகம் உதவி செய்து வீட்டில் கெட்ட பெயர் வாங்கலாம். எலும்பு, தோல், கொலஸ்ட்ரால் சம்மந்தமான நோய்கள் உங்களுக்கு ஏற்பட இடமுண்டு.",
        gods = "மகான்களை வழிபடலாம்"
    )

    4 -> DOBMatch(
        stone = "கோமேதகம்",
        direction = "தென்மேற்கு",
        business = "உங்கள் நாயகன் ராகு ஆவார். எனவே, வானூர்தி சம்மந்தமான தொழில்கள், தொலை தொடர்பு சம்மந்தமான தொழில்கள், பொறியியல் துறைகள், ஜோதிடம், மந்திரம்,   என இவை அனைத்திற்கும் ராகு தான் காரகர். எனவே மேற்கண்ட தொழில்களில் ஒன்று நான்காம் எண் ஆதிக்கத்தில் பிறந்தவர்களுக்கு கிடைத்தால், அவர்கள் தான் பாக்கியசாலிகள்.",
        days = "வெள்ளி, சனி",
        noDays = "ஞாயிறு",
        dates = "8, 17, 26, 4, 13, 22, 6, 15, 24",
        events = "ராகுவின் ஆதிக்கத்தில் பிறந்த நீங்கள் கையில் கிடைக்கும் பணத்தை அப்படியே செலவு செய்து விடுவீர்கள். பெரும்பாலானவர்களுக்கு சேமிக்க முடியாத அளவிற்கு பணம் செலவாகும். சிலர் அவர்களாகவே விரும்பி 'எதை கொண்டு போகப் போகிறோம்?' என்ற எண்ணத்தில் அளவு கடந்து செலவு செய்வார்கள். மது, சிகிரெட் போன்ற கெட்ட பழக்கங்கள் வர இடமுண்டு. சிலருக்கு மாமிச உணவில் நாட்டம் அதிகம் இருக்கும். தகாத பெண் சேர்க்கை ஏற்பட்டு சிலர் பாலியல் வியாதிகளை வாங்கி கொள்ள இடமுண்டு. கவனம். ராகுவின் ஆதிக்கத்தில் பிறந்து இருப்பதால் பெரும்பாலும் மூட நம்பிக்கைக்கு முக்கியத்துவம் தர மாட்டீர்கள். தெய்வத்தின் பால் அதிக ஈர்ப்பு இல்லாமல் போகலாம்.",
        gods = "துர்கையை வழிபட வெற்றி உண்டு"
    )

    5 -> DOBMatch(
        stone = "மரகதப் பச்சை",
        direction = "வடக்கு",
        business = "உங்கள் நாயகன் புதன் ஆவார். எனவே, தரகு, மளிகை கடை, தானிய வியாபாரம், எழுத்து துறை, பேச்சு அல்லது நாக்கினால் பிழைக்கும் தொழில்களான வக்கீல், சொற்பொழிவாளர், ஆசிரியர் என இது போன்ற துறைகள் 5 ஆம் எண் ஆதிக்கத்தில் பிறந்தவர்களுக்கு அமைந்து விட்டால் எண்ணற்ற சாதனையை அந்தத் தொழிலில் அடைவார்கள்.",
        days = "புதன், வெள்ளி, சனி",
        noDays = "திங்கள்",
        dates = "5, 14, 23, 6, 15, 24, 8, 17, 26",
        events = "புதனின் ஆதிக்கத்தில் பிறந்து இருப்பதால் செய்யும் தொழிலில் கண்டிப்பும், கறாறும் நிறைந்து இருக்கும். குணத்தில் சிறிது பெண்மை மேலோங்கி இருக்கும். சில 5 ஆம் எண் காரர்கள் பெண்களைக் கவரும் குணத்துடனும் இருப்பார்கள். பண விஷயத்தில் சுயநலப் புலிகளாக இருப்பீர்கள். எதையும் கணக்குப் போட்டு தான் செய்வீர்கள். கற்பனை திறன் சற்று அதிகமாக இருக்கும் என்பதால் எளிதில் தூங்க முடியாது. உங்கள் மனம் உங்களிடம் ஏதாவது ஒன்றை சொல்லிக் கொண்டே இருக்கும். அதனால் மன ஒருமைப்பாடு உங்களுக்கு அவசியம் தேவை. பெரும்பாலும் மனைவியின் போக்கின் படி தான் நடந்து கொள்வீர்கள். நரம்பு, கை - கால் இழுப்பு சம்மந்தமான பிரச்சனைகள் உங்களுக்கு வரக் கூடும். கவனம்.",
        gods = "மகாவிஷ்ணுவை வழிபட நன்மை உண்டு"
    )

    6 -> DOBMatch(
        stone = "வைரம்",
        direction = "தென்கிழக்கு",
        business = "உங்கள் நாயகன் சுக்கிரன் ஆவார். எனவே  நகைக் கடை, வெள்ளி உலோக வியாபாரம், அழகுக் கலை, கலை அல்லது கலைத் துறை  சம்மந்தமான தொழில்கள் , உளவியல் மருத்துவர் என இதில் ஏதேனும் ஒன்றை 6 ஆம் எண் ஆதிக்கத்தில் பிறந்தவர்கள் தேர்ந்தெடுத்து செய்தால் சிறப்பாக இருப்பார்கள்.",
        days = "புதன், வெள்ளி, சனி",
        noDays = "வியாழன்",
        dates = "6, 15, 24, 5, 14, 23, 8, 17, 26",
        events = "சுக்கிரனின் ஆதிக்கத்தில் பிறந்த நீங்கள் வாழும் வரை சந்தோஷமாக வாழ வேண்டும் என்று நினைப்பவர்கள். எளிமையானவர்கள். பெரும்பாலும், சந்தோஷத்திற்கு என்ன ஒரு விலையையும் தரக் கூடியவர்கள். சிலர் பெண்களின் விஷயத்தில் பலவீனமாக இருக்கலாம். எளிதில் காதல் வயப் படலாம். வெள்ளை சம்மந்தமான எந்த ஒரு தொழிலும் உங்களுக்கு லாபத்தை தரும். கையில் தேவையான சமயத்தில் பணம் வரும். முற்கால வாழ்வை காட்டிலும், பெரும்பாலும் பிற்கால வாழ்க்கை தான் உங்களுக்கு சிறப்பாக இருக்கும். தனிமை விரும்பிகள். சோகத்தை கூட சுகமாக எடுத்துக் கொள்ளும் கலாரசிகர்கள். உங்களுக்கு தொற்று நோய் கிருமிகளின் தொல்லையால் வியாதிகள் வரலாம். பெண்களாக இருந்தால் புற்றுநோய் அல்லது இருதய சம்மந்தமான நோய்களால் தாக்கப்படலாம். கவனம்.",
        gods = "ஸ்ரீ ரங்கம் பெருமாளை வழிபட நன்று"
    )

    7 -> DOBMatch(
        stone = "வைடூரியம்",
        direction = "வட மேற்கு",
        business = "உங்கள் நாயகன் கேது ஆவர். எனவே எலும்பு மருத்துவர், ரசாயன துறை, ஓட்டுநர், மீன் விற்பவர், டிடக்ட்டிவ் ஏஜென்சி, சலவை தொழில், முடி திருத்துனர் போன்ற தொழில்கள் உங்களுக்கு நல்ல வருமானத்தை தரும்.",
        days = "வெள்ளி, சனி",
        noDays = "ஞாயிறு, திங்கள்",
        dates = "7, 16, 25, 6, 15, 24, 8, 17, 26",
        events = "ஞானகாரகனான கேதுவின் ஆதிக்கத்தில் பிறந்து இருப்பதால் வாழ்வில் அதிகம் பற்றுதல் இருக்காது. தான் போன போக்கில் செல்லக் கூடியவர்கள். பெரும்பாலும் காசு, பணம் இவற்றை கஷ்டப்பட்டு சம்பாதித்து எளிதாக தொலைத்து விடுவீர்கள். உங்களுக்கு ஏற்படும் மோசமான அனுபவங்கள் உங்களை எதிர்காலத்தில் ஆன்மீகத்தை நோக்கி திருப்ப இடமுண்டு. விநாயகரை வழிபட்டு வாருங்கள் எல்லாம் நன்மையாக முடியும். ரத்த சம்மந்தமான வியாதிகளால் தொல்லை ஏற்பட இடமுண்டு. சிலருக்கு எலும்புத் தேய்மானம் வரக்கூடும். அதே போல சிறுநீரக பிரச்சனைக்கும் வாய்ப்பு உள்ளது. எப்பொழுதுமே அசைவ சாப்பாட்டை தவிர்ப்பது நல்லது.",
        gods = "விநாயகரை வழிபட வெற்றி உண்டாகும்"
    )

    8 -> DOBMatch(
        stone = "கருநீலம்",
        direction = "மேற்கு",
        business = "உங்கள் நாயகன் சனி ஆவார். எனவே விளையாட்டு சார்ந்த தொழில், இரும்புத் தொழில், சிறைச் சாலை அல்லது நீதித் துறை சம்மந்தமான தொழில், தொழிற்சாலையில் வேலை பார்ப்பது, எள் - கடுகு அல்லது கருப்பு தானியங்கள் வியாபாரம் என இவற்றில் ஏதேனும் ஒரு தொழில் உங்களுக்கு அமைந்து விட்டால் போதும். நல்ல வருமானத்தை பெறுவீர்கள்.",
        days = "வெள்ளி, சனி",
        noDays = "ஞாயிறு",
        dates = "4, 13, 22, 8, 17, 26",
        events = "சனி பகவானின் ஆதிக்கத்தில் பிறந்த நீங்கள் பெரும்பாலும் அதிக உழைப்பாளிகளாக திகழ்வீர்கள். வம்பு சண்டைக்கு போக மாட்டீர்கள் அதே சமயத்தில் வந்த சண்டையை விட மாட்டீர்கள். இந்த உலகத்துடன் உங்களால் நெருங்கி பழக முடியாது. சில சமயங்களில் நீங்கள் பிறர் கண்களுக்கு ஒரு விசித்திர மனிதராகக் கூட தெரியலாம். அப்படி எல்லா விதத்திலும் தனித்தன்மை பெற்றவர்கள் நீங்கள். எதிர்காலத்தில் ஞாபக மறதியால் அதிகம் அவஸ்தை படலாம். வயிறு, நுரையீரல் சம்மந்தமாக பிரச்சனைகள் வரலாம். கவனம். நீல மலர்களை கொண்டு ஈசனை வழிபட்டு வர சகல பாக்கியங்களும் உங்களுக்கு கிடைக்கும். இரும்பு சம்மந்தமான தொழில் செய்தால் கணிசமான லாபத்தை அடைவீர்கள்.",
        gods = "சிவன், பைரவர் போன்ற தெய்வங்களை வழிபட நன்மை உண்டு"
    )

    9 -> DOBMatch(
        stone = "பவளம்",
        direction = "தெற்கு",
        business = "உங்கள் நாயகன் செவ்வாய் ஆவார். எனவே மேலாண்மை அதிகாரிகள், போலீஸ், ராணுவம், மருத்துவம், ரசாயனம், நெருப்பு சம்மந்தமான தொழில்கள், விவசாயம் அல்லது நிலம் மூலம் கிடைக்கும் பொருள்களை அடிப்படையாக கொண்ட தொழில்கள் என இவை அனைத்தும் உங்களுக்கு நன்மையை செய்யும். இவற்றுள் ஏதேனும் ஒன்றை நீங்கள் தேர்ந்தெடுத்து செய்தால் நல்ல லாபத்தை பெறுவீர்கள்.",
        days = "ஞாயிறு, செவ்வாய், வியாழன்",
        noDays = "புதன், சனி",
        dates = "9, 18, 27, 1, 10, 28",
        events = "செவ்வாயின் ஆதிக்கத்தில் பிறந்து உள்ளதால் போர் குணம் சற்று அதிகமாக இருக்கும். வாழ்க்கையில் பல கசப்பான அனுபவங்கள் ஏற்பட்டாலும் துவண்டு போகாதவர்கள் நீங்கள். போலீஸ், ராணுவம், தீயணைப்பு துறை போன்ற துறைகளில் நீங்கள் ஈடுபட்டால் நல்ல முன்னேற்றத்தை அடைவீர்கள். அதிகமாக உணர்ச்சி வசப் படக் கூடியவர்கள் அதனால் சீக்கிரமாகவே ரத்த அழுத்தம், இருதய பாதிப்பு போன்ற நோய்கள் உங்களுக்கு வரக் கூடும். முருகப் பெருமானை சிவப்பு மலர் சாற்றி வழிபட நல்ல முன்னேற்றத்தை காண்பீர்கள்.",
        gods = "முருகப் பெருமானை வழிபட்டு வருவது நன்மை செய்யும்"
    )

    else -> null
}

@Composable
@Preview(showSystemUi = true, showBackground = true)
fun PreviewNumerology() {
    Numerology() {}
}