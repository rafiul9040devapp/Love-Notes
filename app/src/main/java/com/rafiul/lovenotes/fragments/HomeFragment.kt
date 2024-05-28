package com.rafiul.lovenotes.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.widget.SearchView
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.navigation.NavDirections
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.rafiul.lovenotes.R
import com.rafiul.lovenotes.adapter.NoteAdapterAlternative
import com.rafiul.lovenotes.databinding.FragmentHomeBinding
import com.rafiul.lovenotes.model.Note
import com.rafiul.lovenotes.utils.showAlertDialog
import com.rafiul.lovenotes.utils.showAppBar
import com.rafiul.lovenotes.utils.toggleVisibility
import com.rafiul.lovenotes.viewmodel.NoteViewModelAlternative
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class HomeFragment : Fragment(R.layout.fragment_home), SearchView.OnQueryTextListener, MenuProvider {

    private lateinit var binding: FragmentHomeBinding

    private val noteViewModelAlternative by viewModels<NoteViewModelAlternative>()

    private lateinit var noteAdapterAlternative: NoteAdapterAlternative

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        showAppBar()
        val menuHost: MenuHost = requireActivity()
        menuHost.addMenuProvider(this, viewLifecycleOwner, Lifecycle.State.RESUMED)

        with(binding) {
            addNoteFab.setOnClickListener {
                findNavController().navigate(R.id.action_homeFragment_to_addNoteFragment)
            }
        }

        settingUpRecyclerView()
    }

    private fun settingUpRecyclerView() {
        noteAdapterAlternative = NoteAdapterAlternative(object : NoteAdapterAlternative.Listener {
            override fun showDetailsOfNote(note: Note) {
                val direction: NavDirections =
                    HomeFragmentDirections.actionHomeFragmentToEditNoteFragment(note)
                findNavController().navigate(direction)
            }
        })
        binding.homeRecyclerView.apply {
            layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
            setHasFixedSize(true)
            adapter = noteAdapterAlternative
        }

        noteViewModelAlternative.getAllNotes().observe(viewLifecycleOwner) { notes ->
            noteAdapterAlternative.submitList(notes)
            updateTheUI(notes)
        }
    }


    override fun onResume() {
        super.onResume()
        val callback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                showExitDialogue()
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, callback)
    }

    private fun showExitDialogue() {
        context?.showAlertDialog(
            title = getString(R.string.exit),
            message = getString(R.string.are_you_sure_want_to_exit),
            positiveButtonText = getString(R.string.ok),
            positiveAction = {
                requireActivity().finish()
            },
            negativeButtonText = getString(R.string.cancel)
        )
    }



    private fun updateTheUI(notes: List<Note>?) {
        notes?.let {
            val hasNotes = it.isNotEmpty()
            with(binding) {
                emptyNotesImage.toggleVisibility(!hasNotes)
                homeRecyclerView.toggleVisibility(hasNotes)
            }
        }
    }


    private fun searchNote(query: String?) {
        val searchQuery = "%$query%"
        noteViewModelAlternative.searchNote(searchQuery).observe(viewLifecycleOwner) { notes->
            noteAdapterAlternative.submitList(notes)
        }
    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        return false
    }

    override fun onQueryTextChange(newText: String?): Boolean {
        if (newText != null) searchNote(newText)
        return true
    }

    override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
        menu.clear()
        menuInflater.inflate(R.menu.home_menu, menu)

        val menuSearch = menu.findItem(R.id.searchMenu).actionView as SearchView
        menuSearch.isSubmitButtonEnabled = false
        menuSearch.setOnQueryTextListener(this)

    }

    override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
        return false
    }


}