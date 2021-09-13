package com.raveendran.composenote.db

import androidx.room.*
import com.raveendran.composenote.model.Note
import kotlinx.coroutines.flow.Flow

@Dao
interface NoteDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(note: Note): Long

    @Query("SELECT * FROM noteDB")
    fun getAllNotes(): Flow<List<Note>>

    @Delete
    suspend fun delete(note: Note)

    @Update
    suspend fun update(note: Note)

    @Query("SELECT * FROM noteDB WHERE title Like :query")
    fun searchNote(query: String): Flow<List<Note>>

    @Query("SELECT * FROM noteDB WHERE title Like :title")
    fun getNote(title: String): Flow<Note>
}