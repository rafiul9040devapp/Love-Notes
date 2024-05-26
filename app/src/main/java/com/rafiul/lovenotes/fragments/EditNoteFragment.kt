package com.rafiul.lovenotes.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.rafiul.lovenotes.R
import com.rafiul.lovenotes.databinding.FragmentEditNoteBinding
import com.rafiul.lovenotes.model.Note
import com.rafiul.lovenotes.utils.runWithProgressBar
import com.rafiul.lovenotes.utils.showAlertDialog
import com.rafiul.lovenotes.utils.showToast
import com.rafiul.lovenotes.viewmodel.NoteViewModelAlternative
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class EditNoteFragment: Fragment(R.layout.fragment_edit_note), MenuProvider {

    private var editNoteBinding: FragmentEditNoteBinding? = null
    private val binding get() = editNoteBinding!!

    private val noteViewModelAlternative by viewModels<NoteViewModelAlternative>()

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
                    noteViewModelAlternative.updateNote(note)
                    navigateToHomeScreen(view)
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
                noteViewModelAlternative.deleteNote(currentNote)
                context?.showToast(getString(R.string.note_deleted))
                navigateToHomeScreen(view)
            },
            negativeButtonText = getString(R.string.cancel)
        )

    }

    private fun navigateToHomeScreen(view: View) {
//        with(view) {
//            runWithProgressBar(3000) {
//                findNavController().popBackStack()
//            }
//        }

        findNavController().popBackStack(R.id.homeFragment,false)

//        view.runWithProgressBar(1500){
//            findNavController().navigate(R.id.action_editNoteFragment_to_homeFragment)
//        }
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