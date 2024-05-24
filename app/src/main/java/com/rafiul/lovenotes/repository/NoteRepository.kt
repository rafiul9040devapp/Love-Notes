package com.rafiul.lovenotes.repository

import com.rafiul.lovenotes.database.NoteDatabase
import com.rafiul.lovenotes.model.Note

class NoteRepository(private val database: NoteDatabase) {

    suspend fun insertNote(note: Note) = database.getNoteDao().insertNote(note)
    suspend fun updateNote(note: Note) = database.getNoteDao().updateNote(note)
    suspend fun deleteNote(note: Note) = database.getNoteDao().deleteNote(note)


    fun getAllNotes() = database.getNoteDao().getAllNotes()
    fun searchNotes(query: String?) = database.getNoteDao().searchNotes(query)

}