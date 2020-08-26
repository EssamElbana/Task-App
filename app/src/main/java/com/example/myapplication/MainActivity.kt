package com.example.myapplication

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myapplication.model.Note
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    private lateinit var noteViewModel: NoteViewModel
    private val addNoteRequestCode = 500
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recycler_view.layoutManager = LinearLayoutManager(this)
        recycler_view.setHasFixedSize(true)
        val noteAdapter = NoteAdapter()
        recycler_view.adapter = noteAdapter

        noteViewModel = ViewModelProvider(
            this,
            ViewModelProvider.AndroidViewModelFactory.getInstance(this.application)
        )[NoteViewModel::class.java]

        noteViewModel.getAllNotes().observe(this, Observer {
            Toast.makeText(this, "Notes $it", Toast.LENGTH_SHORT).show()
            noteAdapter.setNotes(it)
        })

        button_add_note.setOnClickListener {
            val intent = Intent(this, AddNoteActivity::class.java)
            startActivityForResult(intent,addNoteRequestCode)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode == addNoteRequestCode && resultCode == Activity.RESULT_OK) {
            val title = data?.getStringExtra("title")
            val description = data?.getStringExtra("description")
            val priority = data?.getIntExtra("priority",1)

            noteViewModel.insert(Note(title?:"",description?:"",priority?: 1))

            Toast.makeText(this,"note saved",Toast.LENGTH_SHORT).show()
        }
    }
}