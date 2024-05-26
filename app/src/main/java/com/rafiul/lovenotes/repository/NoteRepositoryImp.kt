package com.rafiul.lovenotes.repository


import androidx.lifecycle.LiveData
import com.rafiul.lovenotes.database.NoteDao
import com.rafiul.lovenotes.model.Note
import javax.inject.Inject

class NoteRepositoryImp @Inject constructor(private val noteDao: NoteDao) : NoteRepository {
    override suspend fun insertNote(note: Note) = noteDao.insertNote(note)

    override suspend fun updateNote(note: Note) = noteDao.updateNote(note)

    override suspend fun deleteNote(note: Note) = noteDao.deleteNote(note)

    override fun getAllNotes(): LiveData<List<Note>> = noteDao.getAllNotes()

    override fun searchNotes(query: String?): LiveData<List<Note>> = noteDao.searchNotes(query)
}