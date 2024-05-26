package com.rafiul.lovenotes.di

import com.rafiul.lovenotes.database.NoteDao
import com.rafiul.lovenotes.repository.NoteRepository
import com.rafiul.lovenotes.repository.NoteRepositoryImp
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object RepositoryModule {

    @Singleton
    @Provides
    fun provideNoteRepository(noteDao: NoteDao): NoteRepository {
        return NoteRepositoryImp(noteDao)
    }
}