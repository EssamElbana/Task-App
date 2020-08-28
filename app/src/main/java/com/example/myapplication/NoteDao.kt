package com.example.myapplication

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.myapplication.model.Note

@Dao
interface NoteDao {

    @Insert
    suspend fun insert(note: Note)

    @Update
    suspend fun update(note: Note)

    @Delete
    suspend fun delete(note: Note)

    @Query("Delete From note_table")
    suspend fun deleteAllNotes()

    @Query("SELECT * From note_table ORDER BY priority DESC")
    fun getAllNotes(): LiveData<List<Note>>
}