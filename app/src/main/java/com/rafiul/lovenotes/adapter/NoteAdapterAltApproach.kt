package com.rafiul.lovenotes.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.rafiul.lovenotes.databinding.NoteLayoutBinding
import com.rafiul.lovenotes.model.Note

class NoteAdapterAltApproach(private var noteActionListener: NoteActionListener) :
    ListAdapter<Note, NoteAdapterAltApproach.NoteViewModelAltApproach>(NoteDiffCallBack()) {

    interface NoteActionListener {
        fun showDetailsOfNote(note: Note)
    }

    inner class NoteViewModelAltApproach(private val binding: NoteLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(note: Note) {

            with(binding) {
                noteTitle.text = note.noteTitle
                noteDesc.text = note.noteDescription
            }

            itemView.setOnClickListener {
                noteActionListener.showDetailsOfNote(note)
            }
        }

    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewModelAltApproach {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = NoteLayoutBinding.inflate(layoutInflater, parent, false)
        return NoteViewModelAltApproach(binding)
    }


    override fun onBindViewHolder(holder: NoteViewModelAltApproach, position: Int) =
        holder.bind(getItem(position))

//    companion object {
//        fun inflateViewHolder(parent: ViewGroup): NoteViewModelAltApproach {
//            val layoutInflater = LayoutInflater.from(parent.context)
//            val binding = NoteLayoutBinding.inflate(layoutInflater, parent, false)
//            return NoteAdapterAltApproach(parent.context as NoteActionListener).NoteViewModelAltApproach(
//                binding
//            )
//        }
//    }

}

class NoteDiffCallBack : DiffUtil.ItemCallback<Note>() {
    override fun areItemsTheSame(oldItem: Note, newItem: Note): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: Note, newItem: Note): Boolean {
        return oldItem.id == newItem.id
    }

}



