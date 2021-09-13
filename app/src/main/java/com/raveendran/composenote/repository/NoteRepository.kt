package com.raveendran.composenote.repository

import com.raveendran.composenote.db.NoteDao
import com.raveendran.composenote.model.Note
import javax.inject.Inject


class NoteRepository @Inject constructor(
    private val dao: NoteDao
) {
    suspend fun insertNote(note: Note) = dao.insert(note = note)
    suspend fun deleteNote(note: Note) = dao.delete(note = note)
    suspend fun updateNote(note: Note) = dao.update(note = note)
    fun getAllNotes() = dao.getAllNotes()
    fun searchNote(query: String) = dao.searchNote(query = query)
    fun getNote(title: String) = dao.getNote(title = title)
}