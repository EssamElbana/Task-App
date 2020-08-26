package com.example.myapplication

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.myapplication.model.Note

@Dao
interface NoteDao {

    @Insert
    fun insert(note: Note)

    @Update
    fun update(note: Note)

    @Delete
    fun delete(note: Note)

    @Query("Delete From note_table")
    fun deleteAllNotes()

    @Query("SELECT * From note_table ORDER BY priority DESC")
    fun getAllNotes(): LiveData<List<Note>>
}