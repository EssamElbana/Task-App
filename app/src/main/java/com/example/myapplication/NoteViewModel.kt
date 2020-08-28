package com.example.myapplication

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.example.myapplication.model.Note
import kotlinx.coroutines.CompletableJob
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch

class NoteViewModel(application: Application) : AndroidViewModel(application) {

    private val noteRepository: NoteRepository = NoteRepository(application)
    private val allNotes: LiveData<List<Note>> = noteRepository.getAllNotes()
    private val doAsync = CoroutineScope(IO)

    fun insert(note: Note) = doAsync.launch { noteRepository.insertNote(note) }
    fun update(note: Note) = doAsync.launch { noteRepository.updateNote(note) }
    fun delete(note: Note) = doAsync.launch { noteRepository.deleteNote(note) }
    fun deleteAll() = doAsync.launch { noteRepository.deleteAllNotes() }
    fun getAllNotes() = allNotes
}