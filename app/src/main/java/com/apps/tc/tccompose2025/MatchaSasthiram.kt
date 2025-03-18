package com.apps.tc.tccompose2025

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.apps.tc.tccompose2025.ui.theme.colorGoldBg
import com.apps.tc.tccompose2025.ui.theme.colorPrimary
import com.apps.tc.tccompose2025.view.CommonList
import com.apps.tc.tccompose2025.view.Header
import org.json.JSONArray

@Composable
fun MatchaSasthiram(){
    val context = LocalContext.current
    val titleList = remember { mutableStateListOf<String>() }
    val palanList = remember { mutableStateListOf<String>() }
    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Header(
            heading = "மச்ச சாஸ்திரம்",
            bgColor = Color(0xFF36006E),
            textColor = colorPrimary,
            withBack = false
        )

        CommonList(titleList) {}

        LaunchedEffect(Unit) {
            val data = loadJsonFromAssets(context, "macham-palankal.json")
            val jsonArray = JSONArray(data)

            repeat(jsonArray.length()) {
                val jsonObject = jsonArray.getJSONObject(it)
                titleList.add(jsonObject.getString("title"))
                val palankalJsonArray = jsonObject.getJSONArray("palankal")
                palanList.add(palankalJsonArray.toString())
            }
        }
    }
}

@Composable
@Preview( showSystemUi = true, showBackground = true)
fun PreviewMatchaSasthiram() {
    MatchaSasthiram()
}