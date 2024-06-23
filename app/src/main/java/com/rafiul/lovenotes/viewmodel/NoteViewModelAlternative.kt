package com.rafiul.lovenotes.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rafiul.lovenotes.R
import com.rafiul.lovenotes.model.Note
import com.rafiul.lovenotes.repository.NoteRepository
import com.rafiul.lovenotes.utils.NotificationHelper
import com.rafiul.lovenotes.utils.TextToSpeechHelper
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NoteViewModelAlternative @Inject constructor(
    @ApplicationContext private val context: Context,
    private val noteRepository: NoteRepository,
    private val notificationHelper: NotificationHelper,
) : ViewModel() {

    private var initialLoadCompleted = false
    private var notificationShownForEmptyList = false

    init {
       showNotificationForEmptyNoteList()
    }

    private fun showNotificationForEmptyNoteList() {
        getAllNotes().observeForever { notes ->
            if (initialLoadCompleted && notes.isEmpty() && !notificationShownForEmptyList) {
                notificationShownForEmptyList = true
                notificationHelper.showNotification(
                    context.getString(R.string.there_is_no_love_notes),
                    context.getString(R.string.make_new_notes_for_your_loved_ones)
                )
            }
            initialLoadCompleted = true
        }
    }

    fun insertNote(note: Note) = viewModelScope.launch {
        noteRepository.insertNote(note)
    }

    fun updateNote(note: Note) = viewModelScope.launch {
        noteRepository.updateNote(note)
    }

    fun deleteNote(note: Note) = viewModelScope.launch {
        noteRepository.deleteNote(note)
    }

    fun getAllNotes() = noteRepository.getAllNotes()

    fun searchNote(query: String?) = noteRepository.searchNotes(query)
}


