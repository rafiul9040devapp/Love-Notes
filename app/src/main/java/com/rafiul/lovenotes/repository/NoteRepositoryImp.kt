package com.rafiul.lovenotes.repository


import androidx.lifecycle.LiveData
import com.rafiul.lovenotes.database.NoteDao
import com.rafiul.lovenotes.model.Note
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class NoteRepositoryImp @Inject constructor(private val noteDao: NoteDao) : NoteRepository {
    override suspend fun insertNote(note: Note) {
        withContext(Dispatchers.IO) {
            noteDao.insertNote(note)
        }
    }

    override suspend fun updateNote(note: Note) {
        withContext(Dispatchers.IO) {
            noteDao.updateNote(note)
        }
    }

    override suspend fun deleteNote(note: Note) {
        withContext(Dispatchers.IO) {
            noteDao.deleteNote(note)
        }
    }

    override suspend fun observeNoteList(): List<Note> {
        return withContext(Dispatchers.IO) {
            noteDao.observeNoteList()
        }
    }

    override fun getAllNotes(): LiveData<List<Note>> = noteDao.getAllNotes()

    override fun searchNotes(query: String?): LiveData<List<Note>> = noteDao.searchNotes(query)
}