package com.rafiul.lovenotes.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.rafiul.lovenotes.model.Note


private const val DATABASE_NAME = "note_db"

@Database(entities = [Note::class], version = 1)
abstract class NoteDatabase : RoomDatabase() {
    abstract fun getNoteDao(): NoteDao


//    companion object{
//
//        private var database: NoteDatabase?=null
//
//        fun getInstance(context:Context): NoteDatabase {
//            return if (database ===null){
//
//                database =Room.databaseBuilder(context, NoteDatabase::class.java,
//                    DATABASE_NAME)
//                    .allowMainThreadQueries() .build()
//
//                database as NoteDatabase
//            }else{
//                database as NoteDatabase
//            }
//
//        }
//    }

//    companion object {
//        @Volatile
//        private var instance: NoteDatabase? = null
//        private val LOCK = Any()
//
//        operator fun invoke(context: Context) = instance ?: synchronized(LOCK) {
//            instance ?: createDatabase(context).also {
//                instance = it
//            }
//        }
//
//        private fun createDatabase(context: Context) = Room.databaseBuilder(
//            context.applicationContext,
//            NoteDatabase::class.java,
//            DATABASE_NAME
//        ).build()


    companion object {
        @Volatile
        private var instance: NoteDatabase? = null
        private val LOCK = Any()

        operator fun invoke(context: Context): NoteDatabase = instance ?: synchronized(LOCK) {
            instance ?: buildDatabase(context).also { instance = it }
        }

        private fun buildDatabase(context: Context) = Room.databaseBuilder(
            context.applicationContext,
            NoteDatabase::class.java,
            DATABASE_NAME
        ).build()
    }
}

