package com.apps.tc.tccompose2025

import android.content.Context
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.apps.tc.tccompose2025.dialog.CommonDialog
import com.apps.tc.tccompose2025.dialog.CorrectAns
import com.apps.tc.tccompose2025.dialog.WrongAnsDialog
import com.apps.tc.tccompose2025.ui.theme.colorAccent
import com.apps.tc.tccompose2025.ui.theme.colorPrimaryDark
import com.apps.tc.tccompose2025.view.Header
import org.json.JSONArray
import org.json.JSONObject
import java.io.InputStreamReader

@Composable
fun RiddlesPlay(selectedId: Int) {
    val context = LocalContext.current
    var question by remember { mutableStateOf("") }
    var answer by remember { mutableStateOf("") }
    var hint by remember { mutableStateOf("") }
    var title by remember { mutableStateOf("") }
    var size by remember { mutableStateOf(0) }
    val selectedLetters = remember { mutableStateListOf<Char>() }
    var shuffledHints by remember { mutableStateOf(listOf<Char>()) }
    var currentIndex by remember { mutableStateOf(0) }
    var riddlesList by remember { mutableStateOf(listOf<JSONObject>()) }
    var showWrongDialog by remember { mutableStateOf(false) }
    var showCommonDialog by remember { mutableStateOf(false) }
    var showCorrectAnsDialog by remember { mutableStateOf(false) }


    // Load a riddle based on index
    fun loadRiddle(index: Int, riddles: List<JSONObject>) {
        if (index in riddles.indices) {
            val riddleObject = riddles[index]
            question = riddleObject.getString("question")
            answer = riddleObject.getString("answer").trim()
            hint = riddleObject.getString("hint")

            val answerChars = answer.toList()
            val extraTamilLetters = context.resources.getStringArray(R.array.tamil_letters).flatMap { it.toList() }
            shuffledHints = (answerChars + extraTamilLetters.shuffled().take(3)).shuffled()
            selectedLetters.clear()
            Log.d("WHILOG", "Answer: $answer")
            Log.d("WHILOG", "Answer: ${shuffledHints.joinToString()}")
        }
    }

    Header(
        heading = title,
        bgColor = colorPrimaryDark,
        textColor = colorAccent,
        onBackReq = {}
    )

    Column(
        modifier = Modifier.fillMaxSize().padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        PaginationUI (
            currentPage = currentIndex + 1,
            totalPages = size,
            onPrevious = {
                if (currentIndex > 0) {
                    currentIndex--
                    loadRiddle(currentIndex, riddlesList)
                }
            },
            onNext = {
                if (currentIndex < size - 1) {
                    currentIndex++
                    loadRiddle(currentIndex, riddlesList)
                }
            }
        )

        Text(
            text = question,
            modifier = Modifier.fillMaxWidth().padding(16.dp),
            textAlign = TextAlign.Center
        )

        Row(
            modifier = Modifier.fillMaxWidth().padding(16.dp),
            horizontalArrangement = Arrangement.Center
        ) {
            Text(
                text = "HINT",
                color = colorPrimaryDark,
                fontSize = 18.sp,
                modifier = Modifier
                    .padding(end = 32.dp)
                    .clickable {
                        showCommonDialog = true
                    }
            )
            Text(
                text = "CLEAR",
                color = colorPrimaryDark,
                fontSize = 18.sp,
                modifier = Modifier.clickable { selectedLetters.clear() }
            )
        }

        AnswerBox(answer.length, selectedLetters.toList())
        Log.d("WHILOG", "Answer Length: ${answer.length}")
        Log.d("WHILOG", "Answer: $answer")

        HintBox(shuffledHints) { letter ->
            if (selectedLetters.size < answer.length) {
                selectedLetters.add(letter)

                if (selectedLetters.size == answer.length) {
                    val userAnswer = selectedLetters.joinToString("")
                    if (userAnswer == answer) {
                        showCorrectAnsDialog = true
                    } else {
                        showWrongDialog = true
                    }
                }
            }
        }

        CorrectAns(
            showDialog = showCorrectAnsDialog,
            onConfirm = {
                showCorrectAnsDialog = false
                currentIndex++ // Move to the next question
                loadRiddle(currentIndex, riddlesList)
            },
            title = "சபாஷ!" ,
            message = "மிகச் சரியான பதில்.",
            btnTxt = "அடுத்த கேள்விக்கு செல்லவும்..."
        )

        CommonDialog(
            showDialog = showCommonDialog,
            onConfirm = {
                showCommonDialog = false
            },
            title = "சாடைக்குறிப்பு (HINT)" ,
            message = hint,
            btnTxt = "OK"
        )

        WrongAnsDialog(
            showDialog = showWrongDialog,
            onTryAgain = {
                selectedLetters.clear()
                showWrongDialog = false
            },
            onNextQuestion = {
                if (currentIndex < size - 1) {
                    currentIndex++
                    loadRiddle(currentIndex, riddlesList)
                    selectedLetters.clear()
                }
                showWrongDialog = false
            }
        )

        Box(
            modifier = Modifier
                .fillMaxSize()
        ) {
            FloatingActionButton(
                onClick = {  },
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .size(48.dp),
                containerColor = Color.Green,
                shape = CircleShape
            ) {
                Icon(
                    imageVector = Icons.Default.Share,
                    contentDescription = "Main Action",
                    tint = Color.White,
                    modifier = Modifier
                        .size(36.dp)
                )
            }
        }
    }

    // Load JSON and filter by ID
    LaunchedEffect(selectedId) {
        val jsonString = loadJsonFromAssets(context, "riddles.json")
        val jsonArray = JSONArray(jsonString)

        for (i in 0 until jsonArray.length()) {
            val riddleCategory = jsonArray.getJSONObject(i)
            if (riddleCategory.getInt("id") == selectedId) {  // Filter by ID
                title = riddleCategory.getString("title")
                val riddlesArray = riddleCategory.getJSONArray("riddles")

                if (riddlesArray.length() > 0) {
                    riddlesList = List(riddlesArray.length()) { riddlesArray.getJSONObject(it) }
                    size = riddlesList.size
                    selectedLetters.clear()
                    loadRiddle(currentIndex, riddlesList) // Load the first riddle
                }
                break
            }
        }
    }
    LaunchedEffect(currentIndex) {
        loadRiddle(currentIndex, riddlesList)  // Ensures only 1 update per index change
    }
}

@Composable
fun AnswerBox(answerLength: Int, selectedLetters: List<Char>) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(4), // Adjust the column count as needed
        modifier = Modifier.fillMaxWidth().padding(16.dp),
        horizontalArrangement = Arrangement.Center
    ) {
        items(answerLength) { index ->
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
                modifier = Modifier.padding(8.dp)
            ) {
                Text(
                    text = selectedLetters.getOrNull(index)?.toString() ?: "",
                    style = MaterialTheme.typography.titleMedium,
                    color = colorPrimaryDark,
                )
                Box(
                    modifier = Modifier
                        .width(40.dp) // Adjust based on design
                        .height(4.dp)
                        .background(colorPrimaryDark)
                )
            }
        }
    }
}

@Composable
fun HintBox(hintLetters: List<Char>, onLetterClick: (Char) -> Unit) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(4), // 2 letters per row (adjust based on layout)
        modifier = Modifier.fillMaxWidth().padding(16.dp)
    ) {
        items(hintLetters.size) { index ->
            Box(
                modifier = Modifier
                    .padding(8.dp)
                    .size(50.dp) // Adjust for circular look
                    .background(
                        color = colorPrimaryDark,
                        shape = CircleShape
                    )
                    .clickable { onLetterClick(hintLetters[index]) },
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = hintLetters[index].toString(),
                    style = MaterialTheme.typography.titleSmall,
                    color = MaterialTheme.colorScheme.surface
                )
            }
        }
    }
}


@Composable
fun PaginationUI(currentPage: Int, totalPages: Int, onPrevious: () -> Unit, onNext: () -> Unit) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center,
        modifier = Modifier
            .fillMaxWidth()
    ) {
        IconButton(
            onClick = onPrevious,
            enabled = currentPage > 1,
        ) {
            Icon(
                imageVector = Icons.Default.ArrowBack,
                contentDescription = "Previous",
                tint = if (currentPage > 1) colorPrimaryDark else Color.Gray
            )
        }

        Text(
            text = "$currentPage of $totalPages",
            color = colorPrimaryDark,
            style = MaterialTheme.typography.labelLarge,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .weight(1f)
        )

        IconButton(
            onClick = onNext,
            enabled = currentPage < totalPages
        ) {
            Icon(
                imageVector = Icons.Default.ArrowForward,
                contentDescription = "Next",
                tint = colorPrimaryDark
            )
        }
    }
}

@Composable
@Preview( showSystemUi = true, showBackground = true)
fun PreviewRiddles() {
    RiddlesPlay(1)
}