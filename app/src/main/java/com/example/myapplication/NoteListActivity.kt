package com.example.myapplication

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.model.Note
import kotlinx.android.synthetic.main.activity_main.*

const val ADD_NOTE_REQUEST_CODE = 0
const val EDIT_NOTE_REQUEST_CODE = 1

class NoteListActivity : AppCompatActivity() {

    private lateinit var noteViewModel: NoteViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recycler_view.layoutManager = LinearLayoutManager(this)
        recycler_view.setHasFixedSize(true)
        val noteAdapter = NoteAdapter(object: NoteAdapter.ItemSelectedInterface{
            override fun onItemSelected(note: Note) {
                val intent = Intent(this@NoteListActivity,NoteDetailsActivity::class.java)
                intent.putExtra("id",note.id)
                intent.putExtra("description",note.description)
                intent.putExtra("title",note.title)
                intent.putExtra("priority",note.priority)

                startActivityForResult(intent,EDIT_NOTE_REQUEST_CODE)
            }
        })
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
            val intent = Intent(this, NoteDetailsActivity::class.java)
            startActivityForResult(intent, ADD_NOTE_REQUEST_CODE)
        }

        val itemTouchCallback = object :
            ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean = false

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val note = noteAdapter.getNoteAtPosition(viewHolder.adapterPosition)
                noteViewModel.delete(note)
            }
        }

        val itemTouchHelper = ItemTouchHelper(itemTouchCallback)
        itemTouchHelper.attachToRecyclerView(recycler_view)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (resultCode) {
            Activity.RESULT_OK ->
                when(requestCode) {
                    ADD_NOTE_REQUEST_CODE -> {
                        val title = data?.getStringExtra("title")?: ""
                        val description = data?.getStringExtra("description")?: ""
                        val priority = data?.getIntExtra("priority", 1)?: 1
                        noteViewModel.insert(Note(title, description, priority))
                        Toast.makeText(this, "note saved", Toast.LENGTH_SHORT).show()
                    }
                    EDIT_NOTE_REQUEST_CODE -> {
                        val title = data?.getStringExtra("title")?: ""
                        val description = data?.getStringExtra("description")?: ""
                        val priority = data?.getIntExtra("priority", 1)?: 1
                        val id = data?.getIntExtra("id",-1)?: -1
                        if(id != -1) {
                            val note = Note(title,description,priority)
                            note.id = id
                            noteViewModel.update(note)
                            Toast.makeText(this, "note updated", Toast.LENGTH_SHORT).show()
                        } else {
                            Toast.makeText(this, "note not saved", Toast.LENGTH_SHORT).show()
                        }
                    }
                    else -> return
                }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_activity_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.delete_all_notes -> {
                deleteAllNotes()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun deleteAllNotes() = noteViewModel.deleteAll()

}