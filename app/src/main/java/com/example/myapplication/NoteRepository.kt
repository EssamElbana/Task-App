package com.example.myapplication

import android.app.Application
import android.os.AsyncTask
import androidx.lifecycle.LiveData
import com.example.myapplication.model.Note

class NoteRepository(application: Application) {
    private val noteDao = NoteDatabase(application).noteDao()
    private val allNotes: LiveData<List<Note>> = noteDao.getAllNotes()

    fun insert(note: Note) {
        NoteAsync {
            noteDao.insert(note)
        }.execute()
    }

    fun update(note: Note) {
        NoteAsync {
            noteDao.update(note)
        }.execute()
    }

    fun delete(note: Note) {
        NoteAsync {
            noteDao.delete(note)
        }.execute()
    }
    fun deleteAll() {
        NoteAsync {
            noteDao.deleteAllNotes()
        }.execute()
    }

    fun getAllNotes(): LiveData<List<Note>> {
        return allNotes
    }

    private class NoteAsync(val task: () -> Unit) : AsyncTask<Void, Void, Unit>() {
        override fun doInBackground(vararg p0: Void?): Unit {
            task()
        }
    }
}