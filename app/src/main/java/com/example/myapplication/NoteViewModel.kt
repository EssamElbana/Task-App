package com.example.myapplication

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.example.myapplication.model.Note

class NoteViewModel(application: Application) : AndroidViewModel(application) {

    private val noteRepository: NoteRepository = NoteRepository(application)
    private val allNotes: LiveData<List<Note>> = noteRepository.getAllNotes()

    fun insert(note: Note) {
        noteRepository.insert(note)
    }

    fun update(note: Note) = noteRepository.update(note)
    fun delete(note: Note) = noteRepository.delete(note)
    fun deleteAll() = noteRepository.deleteAll()
    fun getAllNotes() = allNotes
}