package com.rafiul.lovenotes.fragments

import android.os.Bundle
import android.os.Handler
import android.os.Looper
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
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.rafiul.lovenotes.R
import com.rafiul.lovenotes.databinding.FragmentEditNoteBinding
import com.rafiul.lovenotes.model.Note
import com.rafiul.lovenotes.utils.enableHomeAsUp
import com.rafiul.lovenotes.utils.showAlertDialog
import com.rafiul.lovenotes.utils.showAppBar
import com.rafiul.lovenotes.utils.showToast
import com.rafiul.lovenotes.utils.toggleVisibility
import com.rafiul.lovenotes.viewmodel.NoteViewModelAlternative
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class EditNoteFragment: Fragment(R.layout.fragment_edit_note), MenuProvider {

    private lateinit var binding: FragmentEditNoteBinding

    private val noteViewModelAlternative by viewModels<NoteViewModelAlternative>()

    private lateinit var currentNote: Note
    private val args: EditNoteFragmentArgs by navArgs()

    private lateinit var deleteMenuItem: MenuItem
    private val inProgress = false

    companion object{
        private const val EXECUTION_TIME: Long = 2500L
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentEditNoteBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        showAppBar()
        val menuHost: MenuHost = requireActivity()
        menuHost.addMenuProvider(this, viewLifecycleOwner, Lifecycle.State.RESUMED)

        currentNote = args.note!!

        with(binding) {
            editNoteTitle.setText(currentNote.noteTitle)
            editNoteDesc.setText(currentNote.noteDescription)
        }

        updateNote()
    }


    private fun updateNote() {
        with(binding) {
            editNoteFab.setOnClickListener {
                val noteTitle = editNoteTitle.text.toString().trim()
                val noteDescription = editNoteDesc.text.toString().trim()

                if (noteTitle.isNotEmpty()) {
                    val note = Note(currentNote.id, noteTitle, noteDescription)
                    noteViewModelAlternative.updateNote(note)
                    deleteMenuItem.isEnabled = false
                   enableHomeAsUp(false)
                    navigateToHomeScreen{
                        context?.showToast(getString(R.string.note_is_updated))
                    }
                } else {
                    context?.showToast(getString(R.string.please_enter_note_title))
                }
            }
        }
    }

    private fun deleteNote() {
        context?.showAlertDialog(
            title = getString(R.string.delete_note),
            message = getString(R.string.do_you_want_to_delete_this_note),
            positiveButtonText = getString(R.string.delete),
            positiveAction = {
                noteViewModelAlternative.deleteNote(currentNote)
                navigateToHomeScreen{
                    context?.showToast(getString(R.string.note_deleted))
                }
            },
            negativeButtonText = getString(R.string.cancel)
        )

    }

    private fun navigateToHomeScreen(action: () -> Unit) {
        deleteMenuItem.isEnabled = false
        enableHomeAsUp(false)
        with(binding){
            main.toggleVisibility(inProgress)
            progressOverlay.srcOver.toggleVisibility(!inProgress)
        }
        Handler(Looper.getMainLooper()).postDelayed({
            action()
            findNavController().popBackStack(R.id.homeFragment,false)
        },EXECUTION_TIME)
    }


    override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
        menu.clear()
        menuInflater.inflate(R.menu.edit_note_menu, menu)
        deleteMenuItem = menu.findItem(R.id.deleteMenu)
    }

    override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
        return when (menuItem.itemId) {
            R.id.deleteMenu -> {
                deleteNote()
                true
            }
            else -> false
        }
    }
}