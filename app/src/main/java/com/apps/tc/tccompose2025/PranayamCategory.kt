package com.apps.tc.tccompose2025

import androidx.compose.foundation.Image
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
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.apps.tc.tccompose2025.dialog.CommonAppDialog
import com.apps.tc.tccompose2025.ui.theme.actBeautyDark
import com.apps.tc.tccompose2025.ui.theme.actBeautyLite
import com.apps.tc.tccompose2025.ui.theme.colorAccent
import com.apps.tc.tccompose2025.ui.theme.colorBlack
import com.apps.tc.tccompose2025.ui.theme.colorPrimary
import com.apps.tc.tccompose2025.view.Header
import com.apps.tc.tccompose2025.view.TitleDescription

@Composable
fun PranayamamCategory() {
    val scrollState = rememberScrollState()
    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        Column {
            Header(
                heading = "பிராணாயாமம்",
                bgColor = actBeautyDark,
                textColor = colorAccent
            )
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(scrollState)
            ) {
                BannerSection()
                PayirchiDetail()
            }
        }

        Image(
            painter = painterResource(R.drawable.ic_share),
            contentDescription = "filter",
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .clickable { }
                .padding(8.dp)
                .size(48.dp)
        )
    }
}

@Composable
private fun BannerSection() {
    var showDialog by remember { mutableStateOf(false) }
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                color = actBeautyLite
            ),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            painter = painterResource(R.drawable.ic_pranayamam),
            contentDescription = "",
            modifier = Modifier
                .size(150.dp)
        )

        Column(
            modifier = Modifier
                .padding(8.dp)
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = "பிராணாயாமம்",
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.titleLarge,
                color = MaterialTheme.colorScheme.onPrimary
            )

            Text(
                text = "இனிய இறை அனுபவம்",
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .padding(top = 4.dp),
                style = MaterialTheme.typography.titleSmall,
                color = MaterialTheme.colorScheme.onPrimary
            )

            Text(
                text = "விகிதம் - 2:1:2:1 \\n (Inhale : Hold : Exhale : Retain)",
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .padding(top = 4.dp),
                style = MaterialTheme.typography.labelMedium,
                color = MaterialTheme.colorScheme.onPrimary
            )

            TextButton(
                onClick = {showDialog = true},
                colors = ButtonDefaults.textButtonColors(
                    containerColor = colorPrimary,
                    contentColor = colorBlack
                ),
                shape = RectangleShape,
                modifier = Modifier
                    .padding(top = 4.dp),
            ) {
                Text(
                    text = "பயிற்சியை தொடங்கவும்",
                    style = MaterialTheme.typography.bodySmall,
                    textAlign = TextAlign.Center
                )
            }

            if (showDialog) {
                CommonAppDialog(
                    title = "எச்சரிக்கை!",
                    desc = "",
                    confirmText = "ஏற்கிறேன்",
                    cancelText = "பின்செல்",
                    onConfirm = {},
                    onCancel = { showDialog = false }
                )
            }
        }
    }
}

@Composable
private fun PayirchiDetail() {
    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(
            text = "காணொளி",
            style = MaterialTheme.typography.titleMedium,
            color = actBeautyLite,
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        )

        Image(
            painter = painterResource(R.drawable.youtube_icon),
            contentDescription = "",
            modifier = Modifier
                .padding(vertical = 8.dp)
                .fillMaxWidth()
                .size(150.dp)
        )

        TitleDescription(
            title = "பயன்கள்",
            desc = "பயன்கள்",
            textAlign = TextAlign.Start,
            textColor = actBeautyLite,
        )

        TitleDescription(
            title = "சிறந்த பலன்களுக்கு",
            desc = "சிறந்த பலன்களுக்கு",
            textAlign = TextAlign.Start,
            textColor = actBeautyLite,
        )

        TitleDescription(
            title = "பொதுவான தகவல்கள்",
            desc = "பொதுவான தகவல்கள்",
            textAlign = TextAlign.Start,
            textColor = actBeautyLite,
        )

        TitleDescription(
            title = "பின்குறிப்பு",
            desc = "பின்குறிப்பு",
            textAlign = TextAlign.Start,
            textColor = actBeautyLite,
        )

        TitleDescription(
            title = "செய்முறை",
            desc = "தமிழ் உலகின் பழமையான மொழிகளில் ஒன்றாகும். " +
                    "இதன் இலக்கிய மரபுகள் பல ஆயிரம் ஆண்டுகளாக தொடர்ந்திருக்கின்றன. " +
                    "சங்க காலத்திலிருந்து ஆரம்பமாகி, இன்று வரை தமிழ் மொழி பல்வேறு " +
                    "பரிணாமங்களை கண்டுள்ளது. தமிழின் கவிதைகள், கதைகள், மற்றும் " +
                    "பழமொழிகள் அதன் பண்பாட்டு வளத்தை வெளிப்படுத்துகின்றன",
            textAlign = TextAlign.Center,
            textColor = actBeautyLite,
        )
    }
}

@Composable
@Preview( showSystemUi = true, showBackground = true)
fun PreviewPranayamam() {
    PranayamamCategory()
}
