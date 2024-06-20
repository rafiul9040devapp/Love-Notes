package com.rafiul.lovenotes.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rafiul.lovenotes.model.Note
import com.rafiul.lovenotes.repository.NoteRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NoteViewModelAlternative @Inject constructor(private val noteRepository: NoteRepository) : ViewModel() {

//    private val _tasks = MutableLiveData<List<Note>>()
//    val tasks: LiveData<List<Note>> get() = _tasks
//
//    private var notificationShownForEmptyList = false
//
//    fun checkIfTasksEmpty() {
//        if (_tasks.value.isNullOrEmpty() && !notificationShownForEmptyList) {
//            notificationShownForEmptyList = true
//            notificationHelper.showNotification(
//                "Task List Empty",
//                "All tasks have been deleted."
//            )
//        }
//    }


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

    fun searchNote(query:String?) = noteRepository.searchNotes(query)
}