package com.rafiul.lovenotes.di

import android.content.Context
import com.rafiul.lovenotes.database.NoteDao
import com.rafiul.lovenotes.database.NoteDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@InstallIn(SingletonComponent::class)
@Module
object DatabaseModule {

    @Singleton
    @Provides
    fun provideDatabase(@ApplicationContext context: Context):NoteDatabase = NoteDatabase.invoke(context)


    @Singleton
    @Provides
    fun provideDao(noteDatabase: NoteDatabase):NoteDao = noteDatabase.getNoteDao()
}