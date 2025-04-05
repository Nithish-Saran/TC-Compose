package com.apps.tc.tccompose2025.riddles

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.apps.tc.tccompose2025.R
import com.apps.tc.tccompose2025.dialog.CommonAlertDialog
import com.apps.tc.tccompose2025.dialog.CommonAppDialog
import com.apps.tc.tccompose2025.models.RiddlesData
import com.apps.tc.tccompose2025.ui.theme.colorPrimary
import com.apps.tc.tccompose2025.ui.theme.colorPrimaryDark
import com.apps.tc.tccompose2025.view.Header

@Composable
fun RiddlesPlay(riddleData: RiddlesData, onBackReq: () -> Unit) {
    val context = LocalContext.current

    var question by remember { mutableStateOf("") }
    var answer by remember { mutableStateOf("") }
    var hint by remember { mutableStateOf("") }
    var size by remember { mutableStateOf(riddleData.riddles.size) }

    val selectedLetters = remember { mutableStateListOf<Char>() }
    var shuffledHints by remember { mutableStateOf(listOf<Char>()) }
    var currentIndex by remember { mutableStateOf(0) }
    var showWrongDialog by remember { mutableStateOf(false) }
    var showCommonDialog by remember { mutableStateOf(false) }
    var showCorrectAnsDialog by remember { mutableStateOf(false) }

    // Load the riddle based on index
    fun loadRiddle(index: Int) {
        if (index in riddleData.riddles.indices) {
            val riddleObject = riddleData.riddles[index]
            question = riddleObject.question
            answer = riddleObject.answer.trim()
            hint = riddleObject.hint

            val extraTamilLetters = context.resources.getStringArray(R.array.tamil_letters).flatMap { it.toList() }
            shuffledHints = (answer.toList() + extraTamilLetters.shuffled().take(3)).shuffled()

            selectedLetters.clear()
        }
    }

    // Load the first riddle on first composition
    LaunchedEffect(Unit) {
        loadRiddle(currentIndex)
    }

    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Header(
                heading = riddleData.title,
                bgColor = Color(0xFFB51E25),
                textColor = colorPrimary,
                onBackReq = {onBackReq()}
            )

            PaginationUI(
                currentPage = currentIndex + 1,
                totalPages = size,
                onPrevious = {
                    if (currentIndex > 0) {
                        currentIndex--
                        loadRiddle(currentIndex)
                    }
                },
                onNext = {
                    if (currentIndex < size - 1) {
                        currentIndex++
                        loadRiddle(currentIndex)
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
                        .clickable { showCommonDialog = true }
                )
                Text(
                    text = "CLEAR",
                    color = colorPrimaryDark,
                    fontSize = 18.sp,
                    modifier = Modifier.clickable { selectedLetters.clear() }
                )
            }

            AnswerBox(answer.length, selectedLetters.toList())

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

            // Correct answer dialog
            CommonAlertDialog(
                showDialog = showCorrectAnsDialog,
                title = "சபாஷ!",
                desc = "மிகச் சரியான பதில்.",
                confirmText = "அடுத்த கேள்விக்கு செல்லவும்...",
                onConfirm = {
                    showCorrectAnsDialog = false
                    if (currentIndex < size - 1) {
                        currentIndex++
                        loadRiddle(currentIndex)
                    }
                }
            )

            // Hint dialog
            CommonAlertDialog(
                showDialog = showCommonDialog,
                title = "சாடைக்குறிப்பு (HINT)",
                desc = hint,
                confirmText = "OK",
                onConfirm = {
                    showCommonDialog = false
                }
            )

            CommonAppDialog(
                showDialog = showWrongDialog,
                title = "தவறான பதில்!",
                desc = "இந்த விடுகதைக்கு உங்கள் பதில் பொருந்தவில்லை.",
                confirmText = "NEXT",
                cancelText = "TRY AGAIN",
                onConfirm = {
                    if (currentIndex < size - 1) {
                        currentIndex++
                        loadRiddle(currentIndex)
                        selectedLetters.clear()
                    }
                    showWrongDialog = false
                },
                onCancel = {
                    selectedLetters.clear()
                    showWrongDialog = false
                }
            )
        }

        Image(
            painter = painterResource(R.drawable.ic_share),
            contentDescription = "filter",
            modifier = Modifier
                .padding(bottom = 8.dp, end = 8.dp)
                .align(Alignment.BottomEnd)
                .background(
                    color = Color.Transparent,
                    shape = CircleShape
                )
                .padding(8.dp)
                .size(48.dp)
        )
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
            .padding(vertical = 16.dp)
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
    RiddlesPlay(
        riddleData = TODO()
    ) {}
}