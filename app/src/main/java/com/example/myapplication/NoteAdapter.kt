package com.example.myapplication

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.model.Note
import kotlinx.android.synthetic.main.note_item.view.*

class NoteAdapter(val itemSelectedInterface: ItemSelectedInterface) :
    RecyclerView.Adapter<NoteAdapter.NoteViewHolder>() {

    private val diffCallback = object : DiffUtil.ItemCallback<Note>() {
        override fun areItemsTheSame(oldItem: Note, newItem: Note) = oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: Note, newItem: Note) = oldItem == newItem
    }

    private val differ = AsyncListDiffer(this, diffCallback)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.note_item, parent, false)
        return NoteViewHolder(itemView)
    }

    override fun getItemCount(): Int = differ.currentList.size

    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        holder.bindDataView(differ.currentList[position])
    }

    inner class NoteViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val textViewTitle = itemView.text_view_title as TextView
        private val textViewDescription = itemView.text_view_description as TextView
        private val textViewPriority = itemView.text_view_priority as TextView

        init {
            itemView.setOnClickListener {
                itemSelectedInterface.onItemSelected(differ.currentList[adapterPosition])
            }
        }

        fun bindDataView(note: Note) {
            textViewTitle.text = note.title
            textViewDescription.text = note.description
            textViewPriority.text = note.priority.toString()
        }

    }

    fun setNotes(notes: List<Note>) = differ.submitList(notes)

    fun getNoteAtPosition(position: Int) = differ.currentList[position]

    interface ItemSelectedInterface {
        fun onItemSelected(note: Note)
    }
}