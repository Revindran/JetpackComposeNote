package com.raveendran.composenote.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.raveendran.composenote.model.Note

@Database(
    entities = [Note::class],
    version = 1
)

abstract class NoteDatabase : RoomDatabase() {
    abstract fun noteDao(): NoteDao
}