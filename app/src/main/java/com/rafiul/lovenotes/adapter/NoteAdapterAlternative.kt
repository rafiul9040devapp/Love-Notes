package com.rafiul.lovenotes.adapter

import android.graphics.Color
import android.text.Spannable
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.rafiul.lovenotes.databinding.NoteLayoutBinding
import com.rafiul.lovenotes.model.Note
import com.rafiul.lovenotes.viewmodel.NoteViewModelAlternative
import javax.inject.Inject

class NoteAdapterAlternative(private var listener: Listener) :
    ListAdapter<Note, NoteAdapterAlternative.NoteViewHolderAlternative>(COMPARATOR) {

    interface Listener {
        fun showDetailsOfNote(note: Note)
        fun readTheNoteTitle(noteTitle: TextView,fullText: String)
    }

    inner class NoteViewHolderAlternative(private val binding: NoteLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(note: Note) {
            with(binding) {
                noteTitle.text = note.noteTitle
                noteDesc.text = note.noteDescription
                noteTitle.setOnClickListener {
                    listener.readTheNoteTitle(noteTitle,note.noteTitle)
                }
            }

            itemView.setOnClickListener {
                listener.showDetailsOfNote(note)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolderAlternative {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = NoteLayoutBinding.inflate(layoutInflater, parent, false)
        return NoteViewHolderAlternative(binding)
    }

    override fun onBindViewHolder(holder: NoteViewHolderAlternative, position: Int) =
        holder.bind(getItem(position))

    companion object {
        val COMPARATOR = object : DiffUtil.ItemCallback<Note>() {
            override fun areItemsTheSame(oldItem: Note, newItem: Note): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: Note, newItem: Note): Boolean {
                return oldItem.id == newItem.id
            }

        }
    }
}