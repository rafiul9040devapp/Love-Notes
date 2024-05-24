package com.rafiul.lovenotes.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.rafiul.lovenotes.model.Note
import com.rafiul.lovenotes.repository.NoteRepository
import kotlinx.coroutines.launch

class NoteViewModel(application: Application, private val repository: NoteRepository) :
    AndroidViewModel(application) {

    fun insertNote(note: Note) = viewModelScope.launch {
        repository.insertNote(note)
    }

    fun updateNote(note: Note) = viewModelScope.launch {
        repository.updateNote(note)
    }

    fun deleteNote(note: Note) = viewModelScope.launch {
        repository.deleteNote(note)
    }

    fun getAllNotes() = repository.getAllNotes()

    fun searchNote(query:String?) = repository.searchNotes(query)



}