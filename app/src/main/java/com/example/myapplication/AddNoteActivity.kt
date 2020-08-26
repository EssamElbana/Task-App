package com.example.myapplication

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_add_note.*

class AddNoteActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_note)

        number_picker_priority.minValue = 1
        number_picker_priority.maxValue = 10

        actionBar?.setHomeAsUpIndicator(R.drawable.ic_close)
        title = "Add Note"
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.add_note_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.save_note -> {
                saveNote()
                return true
            }
            else -> return super.onOptionsItemSelected(item)
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
        setResult(Activity.RESULT_OK, data)
        finish()
    }

}