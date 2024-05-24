package com.rafiul.lovenotes.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.lifecycle.Lifecycle
import androidx.navigation.findNavController
import androidx.navigation.fragment.navArgs
import com.rafiul.lovenotes.MainActivity
import com.rafiul.lovenotes.R
import com.rafiul.lovenotes.databinding.FragmentEditNoteBinding
import com.rafiul.lovenotes.model.Note
import com.rafiul.lovenotes.utils.DialogueExtension.showAlertDialog
import com.rafiul.lovenotes.utils.DialogueExtension.showToast
import com.rafiul.lovenotes.utils.runWithProgressBar
import com.rafiul.lovenotes.viewmodel.NoteViewModel


class EditNoteFragment : Fragment(R.layout.fragment_edit_note), MenuProvider {

    private var editNoteBinding: FragmentEditNoteBinding? = null
    private val binding get() = editNoteBinding!!

    private lateinit var noteViewModel: NoteViewModel

    private lateinit var currentNote: Note
    private val args: EditNoteFragmentArgs by navArgs()

    private lateinit var editNoteView: View

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        editNoteBinding = FragmentEditNoteBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val menuHost: MenuHost = requireActivity()
        menuHost.addMenuProvider(this, viewLifecycleOwner, Lifecycle.State.RESUMED)

        noteViewModel = (activity as MainActivity).noteViewModel

        currentNote = args.note!!

        editNoteView = view

        with(binding) {
            editNoteTitle.setText(currentNote.noteTitle)
            editNoteDesc.setText(currentNote.noteDescription)
        }

        updateNote(editNoteView)
    }


    private fun updateNote(view: View) {
        with(binding) {
            editNoteFab.setOnClickListener {
                val noteTitle = editNoteTitle.text.toString().trim()
                val noteDescription = editNoteDesc.text.toString().trim()

                if (noteTitle.isNotEmpty()) {
                    val note = Note(currentNote.id, noteTitle, noteDescription)
                    noteViewModel.updateNote(note)

                    with(view){
                        runWithProgressBar(3000){
                            findNavController().popBackStack(R.id.homeFragment, false)
                        }
                    }

                } else {
                    context?.showToast(getString(R.string.please_enter_note_title))
                }
            }
        }
    }

    private fun deleteNote(view: View) {
        context?.showAlertDialog(
            title = getString(R.string.delete_note),
            message = getString(R.string.do_you_want_to_delete_this_note),
            positiveButtonText = getString(R.string.delete),
            positiveAction = {
                noteViewModel.deleteNote(currentNote)
                context?.showToast(getString(R.string.note_deleted))
                with(view){
                    runWithProgressBar(3000){
                        findNavController().popBackStack(R.id.homeFragment, false)
                    }
                }
            },
            negativeButtonText = getString(R.string.cancel)
        )


//        android.app.AlertDialog.Builder(activity).apply {
//            setTitle(getString(R.string.delete_note))
//            setMessage(getString(R.string.do_you_want_to_delete_this_note))
//            setPositiveButton(getString(R.string.delete)) { _, _ ->
//                noteViewModel.deleteNote(currentNote)
//                Toast.makeText(
//                    context,
//                    getString(R.string.note_deleted),
//                    Toast.LENGTH_SHORT
//                ).show()
//
//                //handler looper for nice experience
//                view.findNavController().popBackStack(R.id.homeFragment, false)
//            }
//            setNegativeButton(getString(R.string.cancel), null)
//        }.create().show()
    }


    override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
        menu.clear()
        menuInflater.inflate(R.menu.edit_note_menu, menu)
    }

    override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
        return when (menuItem.itemId) {
            R.id.deleteMenu -> {
                deleteNote(editNoteView)
                true
            }

            else -> false
        }
    }


    override fun onDestroy() {
        super.onDestroy()
        editNoteBinding = null
    }
}