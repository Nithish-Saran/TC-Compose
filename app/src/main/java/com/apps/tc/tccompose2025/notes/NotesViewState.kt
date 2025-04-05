package com.apps.tc.tccompose2025.notes

import com.apps.tc.tccompose2025.models.ReminderNote

sealed class NotesViewState {
    data object Loading : NotesViewState()
    data object NoData : NotesViewState()
    data object Empty : NotesViewState()
    //data class AddNote(val notes: Pair<String, String>) : NotesViewState()
    data class NotesList(val message: Array<ReminderNote>) : NotesViewState()

}