package com.example.myapplication.data_source

import android.app.Application
import androidx.lifecycle.LiveData
import com.example.myapplication.data_source.local_database.NoteDatabase
import com.example.myapplication.model.Note

class NoteRepository(application: Application) {
    private val noteDao = NoteDatabase(application).noteDao()
    private val allNotes: LiveData<List<Note>> = noteDao.getAllNotes()

    suspend fun insertNote(note: Note) = noteDao.insert(note)

    suspend fun updateNote(note: Note) = noteDao.update(note)

    suspend fun deleteNote(note: Note) = noteDao.delete(note)

    suspend fun deleteAllNotes() = noteDao.deleteAllNotes()

    fun getAllNotes() = allNotes
}