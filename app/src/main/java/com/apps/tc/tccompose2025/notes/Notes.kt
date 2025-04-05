package com.apps.tc.tccompose2025.notes

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Text
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.apps.tc.tccompose2025.App
import com.apps.tc.tccompose2025.R
import com.apps.tc.tccompose2025.dialog.CommonAlertDialog
import com.apps.tc.tccompose2025.dialog.CommonAppDialog
import com.apps.tc.tccompose2025.dialog.NotesAddDialog
import com.apps.tc.tccompose2025.ui.theme.colorAccent
import com.apps.tc.tccompose2025.ui.theme.colorPrimary
import com.apps.tc.tccompose2025.ui.theme.colorPrimaryDark
import com.apps.tc.tccompose2025.ui.theme.colorTextLite
import com.apps.tc.tccompose2025.view.Header
import kotlinx.coroutines.launch

@Composable
fun Notes(app: App) {
    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    val viewModel: NotesViewModel = viewModel()
    val state = viewModel.notesViewState.collectAsState().value
    val showNoteDialog = remember { mutableStateOf(false) }
    val showEmptyFieldsDialog = remember { mutableStateOf(false) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                color = colorPrimary
            )
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
        ) {
            Header(
                heading = "Notes",
                bgColor = colorPrimaryDark,
                textColor = colorTextLite
            ) { }

            when(state) {
                is NotesViewState.Loading -> {

                }
                is NotesViewState.NoData -> {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "குறிப்புகள் எதுவும் இல்லை...",
                            style = MaterialTheme.typography.titleMedium,
                            color = colorAccent,
                            textAlign = TextAlign.Center
                        )
                    }
                }
                
                is NotesViewState.Empty -> {
                    CommonAlertDialog(
                        title = "Sorry!",
                        desc = "Please Enter the title and text",
                        showDialog = showEmptyFieldsDialog.value,
                        confirmText = "OK"
                    ) {
                        showNoteDialog.value = true
                        showEmptyFieldsDialog.value = false
                    }
                }

                is NotesViewState.NotesList -> {
                    CommonAlertDialog(
                        title = state.message.toString(),
                        desc = "",
                        showDialog = showEmptyFieldsDialog.value,
                        confirmText = "OK"
                    ) {

                    }
                }
            }

            NotesAddDialog(
                showDialog = showNoteDialog.value,
                onDismiss = { showNoteDialog.value = false }
            ) { title, note ->
                if (title.isNotEmpty() && note.isNotEmpty()) {
                    showNoteDialog.value = false
                    scope.launch {
                        viewModel.addNote(app, Pair(title, note))
                    }
                } else {
                    showEmptyFieldsDialog.value = true
                    viewModel.showEmptyFieldsAlert()
                }
            }
        }

        Image(
            painter = painterResource(R.drawable.ic_add),
            contentDescription = "filter",
            modifier = Modifier
                .padding(bottom = 8.dp, end = 8.dp)
                .align(Alignment.BottomEnd)
                .background(
                    color = Color.Transparent,
                    shape = CircleShape
                )
                .clickable {
                    showNoteDialog.value = true
                }
                .padding(8.dp)
                .size(48.dp)
        )
    }

    LaunchedEffect(Unit) {
        scope.launch {
            viewModel.getNotes(app)
        }
    }

}

@Composable
@Preview( showSystemUi = true, showBackground = true)
fun PreviewNotes() {
    Notes(App())
}