package com.apps.tc.tccompose2025.notes

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.apps.tc.tccompose2025.App
import com.apps.tc.tccompose2025.models.ReminderNote
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.Date

class NotesViewModel: ViewModel() {
    private val _notesViewState = MutableStateFlow<NotesViewState>(NotesViewState.Empty)
    val notesViewState: StateFlow<NotesViewState> = _notesViewState.asStateFlow()

    var notesList = mutableListOf<ReminderNote>()

    fun getNotes(app: App) {
        _notesViewState.value = NotesViewState.Loading
        viewModelScope.launch(Dispatchers.IO) {
            notesList.clear()
            notesList = app.getNotes().toMutableList()
            if (notesList.isNotEmpty()) {
                withContext(Dispatchers.Main) {
                    _notesViewState.value = NotesViewState.NotesList(notesList.toTypedArray())
                }
            }
            else {
                withContext(Dispatchers.Main) {
                    _notesViewState.value = NotesViewState.NoData
                }
            }
        }
    }

    fun addNote(app: App, notes: Pair<String, String>) {
        viewModelScope.launch(Dispatchers.IO) {
            if (notes.first.isEmpty() || notes.second.isEmpty()) {
                withContext(Dispatchers.Main) {
                    _notesViewState.value = NotesViewState.Empty
                }
            }
            else {
                val reminderNote = ReminderNote(notes.first, notes.second, Date())
                notesList.add(reminderNote)
                app.setNotes(notesList.toTypedArray())
                getNotes(app)
            }
        }
    }

    fun showEmptyFieldsAlert() {
        _notesViewState.value = NotesViewState.Empty
    }

    fun deleteNote(app: App, position: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            notesList.removeAt(position)
            app.setNotes(notesList.toTypedArray())
            getNotes(app)
        }
    }

}