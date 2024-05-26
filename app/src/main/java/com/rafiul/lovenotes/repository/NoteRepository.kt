package com.rafiul.lovenotes.repository


import androidx.lifecycle.LiveData
import com.rafiul.lovenotes.model.Note

interface NoteRepository {

    suspend fun insertNote(note: Note)
    suspend fun updateNote(note: Note)
    suspend fun deleteNote(note: Note)


    fun getAllNotes(): LiveData<List<Note>>
    fun searchNotes(query: String?): LiveData<List<Note>>


}