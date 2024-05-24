package com.rafiul.lovenotes.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.rafiul.lovenotes.databinding.NoteLayoutBinding
import com.rafiul.lovenotes.model.Note

class NoteAdapterAlternative(private var listener: Listener) :
    ListAdapter<Note, NoteAdapterAlternative.NoteViewHolderAlternative>(COMPARATOR) {


    interface Listener {
        fun showDetailsOfNote(note: Note)
    }

    inner class NoteViewHolderAlternative(private val binding: NoteLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(note: Note) {
            with(binding) {
                noteTitle.text = note.noteTitle
                noteDesc.text = note.noteDescription
            }

            itemView.setOnClickListener {
                listener.showDetailsOfNote(note)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolderAlternative {
        return NoteViewHolderAlternative(
            NoteLayoutBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
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