package com.rafiul.lovenotes.fragments

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.navigation.fragment.findNavController
import com.rafiul.lovenotes.R
import com.rafiul.lovenotes.base.BaseFragment
import com.rafiul.lovenotes.databinding.FragmentAddNoteBinding
import com.rafiul.lovenotes.model.Note
import com.rafiul.lovenotes.utils.enableHomeAsUp
import com.rafiul.lovenotes.utils.showAppBar
import com.rafiul.lovenotes.utils.showToast
import com.rafiul.lovenotes.utils.toggleVisibility
import com.rafiul.lovenotes.viewmodel.NoteViewModelAlternative
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class AddNoteFragment : BaseFragment<FragmentAddNoteBinding>(FragmentAddNoteBinding::class), MenuProvider {

    private val noteViewModelAlternative by viewModels<NoteViewModelAlternative>()
    private lateinit var saveMenuItem: MenuItem
    private val inProgress = false

    companion object{
        private const val EXECUTION_TIME: Long = 2000L
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        showAppBar()

        val menuHost: MenuHost = requireActivity()
        menuHost.addMenuProvider(this, viewLifecycleOwner, Lifecycle.State.RESUMED)

    }

    private fun saveNote() {
        val noteTitle = binding.addNoteTitle.text.toString().trim()
        val noteDescription = binding.addNoteDesc.text.toString().trim()

        saveMenuItem.isEnabled = false
        enableHomeAsUp(false)

        if (noteTitle.isNotEmpty()) {
            val note = Note(0, noteTitle, noteDescription)
            noteViewModelAlternative.insertNote(note)
           with(binding){
               main.toggleVisibility(inProgress)
               progressOverlay.srcOver.toggleVisibility(!inProgress)
           }

            Handler(Looper.getMainLooper()).postDelayed({
                context?.showToast(getString(R.string.note_saved))
                findNavController().navigate(R.id.action_addNoteFragment_to_homeFragment)
            },EXECUTION_TIME)

        } else {
            context?.showToast(getString(R.string.please_enter_note_title))
            saveMenuItem.isEnabled = true
            enableHomeAsUp(true)
        }
    }


    override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
        menu.clear()
        menuInflater.inflate(R.menu.add_note_menu, menu)
        saveMenuItem = menu.findItem(R.id.saveMenu)
    }

    override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
        return when (menuItem.itemId) {
            R.id.saveMenu -> {
                saveNote()
                true
            }
            else -> false
        }
    }

}

