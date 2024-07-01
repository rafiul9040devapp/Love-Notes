package com.rafiul.lovenotes.adapter

import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import com.rafiul.lovenotes.base.BaseAdapter
import com.rafiul.lovenotes.databinding.NoteLayoutBinding
import com.rafiul.lovenotes.model.Note

class NoteAdapterWithBase(private var noteActionListener: NoteActionListenerWithBase):
    BaseAdapter<Note,NoteLayoutBinding>(NoteDiffCallBackWithBase(),NoteLayoutBinding::inflate){

    interface NoteActionListenerWithBase {
        fun showDetailsOfNote(note: Note)
        fun readTheNoteTitle(noteTitle: TextView, fullText: String)
    }

    override fun bind(binding: NoteLayoutBinding, item: Note) {
        with(binding) {
            noteTitle.text = item.noteTitle
            noteDesc.text = item.noteDescription

            noteTitle.setOnClickListener {
                noteActionListener.readTheNoteTitle(noteTitle,item.noteTitle)
            }

            root.setOnClickListener {
                noteActionListener.showDetailsOfNote(item)
            }
        }
    }
}

class NoteDiffCallBackWithBase : DiffUtil.ItemCallback<Note>() {
    override fun areItemsTheSame(oldItem: Note, newItem: Note): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: Note, newItem: Note): Boolean {
        return oldItem.id == newItem.id
    }

}