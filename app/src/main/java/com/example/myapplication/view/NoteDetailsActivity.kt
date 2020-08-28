package com.example.myapplication.view

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.myapplication.R
import kotlinx.android.synthetic.main.activity_add_note.*

class NoteDetailsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_note)

        number_picker_priority.minValue = 1
        number_picker_priority.maxValue = 10

        actionBar?.setHomeAsUpIndicator(R.drawable.ic_close)
        if (intent.hasExtra("id")) {
            title = "Edit Note"
            edit_text_title.setText(intent.getStringExtra("title"))
            edit_text_description.setText(intent.getStringExtra("description"))
            number_picker_priority.value = intent.getIntExtra("priority",1)
        } else
            title = "Add Note"
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.add_note_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.save_note -> {
                saveNote()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun saveNote() {
        val title = edit_text_title.text.toString()
        val description = edit_text_description.text.toString()
        val priority = number_picker_priority.value

        if (title.isEmpty() || description.isEmpty()) {
            Toast.makeText(
                this, "Please insert a title and a description",
                Toast.LENGTH_SHORT
            ).show()
            return
        }

        val data = Intent()
        data.putExtra("title", title)
        data.putExtra("description", description)
        data.putExtra("priority", priority)
        if(intent.hasExtra("id"))
            data.putExtra("id",intent.getIntExtra("id",-1))
        setResult(Activity.RESULT_OK, data)
        finish()
    }

}