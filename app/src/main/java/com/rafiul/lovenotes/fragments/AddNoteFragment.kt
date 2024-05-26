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
import com.rafiul.lovenotes.MainActivity
import com.rafiul.lovenotes.R
import com.rafiul.lovenotes.databinding.FragmentAddNoteBinding
import com.rafiul.lovenotes.model.Note
import com.rafiul.lovenotes.utils.runWithProgressBar
import com.rafiul.lovenotes.utils.showToast
import com.rafiul.lovenotes.viewmodel.NoteViewModel
import com.rafiul.lovenotes.viewmodel.NoteViewModelAlternative
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class AddNoteFragment : Fragment(R.layout.fragment_add_note), MenuProvider {

    private var addNoteBinding: FragmentAddNoteBinding? = null
    private val binding get() = addNoteBinding!!

   private val noteViewModelAlternative by viewModels<NoteViewModelAlternative>()
    private lateinit var addNoteView: View

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        addNoteBinding = FragmentAddNoteBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val menuHost: MenuHost = requireActivity()
        menuHost.addMenuProvider(this, viewLifecycleOwner, Lifecycle.State.RESUMED)
        addNoteView = view

    }

    private fun saveNote(view: View) {
        val noteTitle = binding.addNoteTitle.text.toString().trim()
        val noteDescription = binding.addNoteDesc.text.toString().trim()

        if (noteTitle.isNotEmpty()) {
            val note = Note(0, noteTitle, noteDescription)
            noteViewModelAlternative.insertNote(note)
            addNoteView.context?.showToast(getString(R.string.note_saved))

            findNavController().popBackStack()

//            with(view){
//                runWithProgressBar(3000){
//                    findNavController().popBackStack()
//                }
//            }

        } else {
            addNoteView.context?.showToast(getString(R.string.please_enter_note_title))
        }
    }

    override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
        menu.clear()
        menuInflater.inflate(R.menu.add_note_menu, menu)
    }

    override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
        return when (menuItem.itemId) {
            R.id.saveMenu -> {
                saveNote(addNoteView)
                true
            }

            else -> false
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        addNoteBinding = null
    }

}