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
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.rafiul.lovenotes.MainActivity
import com.rafiul.lovenotes.R
import com.rafiul.lovenotes.databinding.FragmentAddNoteBinding
import com.rafiul.lovenotes.databinding.FragmentHomeBinding
import com.rafiul.lovenotes.model.Note
import com.rafiul.lovenotes.utils.runWithProgressBar
import com.rafiul.lovenotes.utils.showToast
import com.rafiul.lovenotes.utils.toggleVisibility
import com.rafiul.lovenotes.viewmodel.NoteViewModel
import com.rafiul.lovenotes.viewmodel.NoteViewModelAlternative
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


@AndroidEntryPoint
class AddNoteFragment : Fragment(R.layout.fragment_add_note), MenuProvider {

    private lateinit var binding: FragmentAddNoteBinding

    private val noteViewModelAlternative by viewModels<NoteViewModelAlternative>()

    private lateinit var saveMenuItem: MenuItem
    private val inProgress = false
    private val EXECUTION_TIME: Long = 2500L

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAddNoteBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val menuHost: MenuHost = requireActivity()
        menuHost.addMenuProvider(this, viewLifecycleOwner, Lifecycle.State.RESUMED)

    }

    private fun saveNote() {
        val noteTitle = binding.addNoteTitle.text.toString().trim()
        val noteDescription = binding.addNoteDesc.text.toString().trim()

        saveMenuItem.isEnabled = false

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

