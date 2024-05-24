package com.rafiul.lovenotes.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.lifecycle.Lifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.rafiul.lovenotes.MainActivity
import com.rafiul.lovenotes.R
import com.rafiul.lovenotes.adapter.NoteAdapter
import com.rafiul.lovenotes.databinding.FragmentHomeBinding
import com.rafiul.lovenotes.model.Note
import com.rafiul.lovenotes.viewmodel.NoteViewModel


class HomeFragment : Fragment(R.layout.fragment_home), SearchView.OnQueryTextListener,
    MenuProvider {


    private var homeBinding: FragmentHomeBinding? = null
    private val binding get() = homeBinding!!

    private lateinit var noteViewModel: NoteViewModel
    private lateinit var noteAdapter: NoteAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        homeBinding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val menuHost: MenuHost = requireActivity()
        menuHost.addMenuProvider(this, viewLifecycleOwner, Lifecycle.State.RESUMED)

        noteViewModel = (activity as MainActivity).noteViewModel
        setUpRecyclerView()

        with(binding) {
            addNoteFab.setOnClickListener {
                findNavController().navigate(R.id.action_homeFragment_to_addNoteFragment)
            }
        }
    }


//    private fun updateTheUI(note: List<Note>?) {
//        if (note != null) {
//            if (note.isNotEmpty()) {
//                with(binding) {
//                    emptyNotesImage.visibility = View.GONE
//                    homeRecyclerView.visibility = View.VISIBLE
//                }
//            } else {
//                with(binding) {
//                    emptyNotesImage.visibility = View.VISIBLE
//                    homeRecyclerView.visibility = View.GONE
//                }
//            }
//        }
//    }


    //going to use this in the utils 
    private fun View.toggleVisibility(isVisible: Boolean) {
        this.visibility = if (isVisible) View.VISIBLE else View.GONE
    }

    private fun updateTheUI(notes: List<Note>?) {
        notes?.let {
            val hasNotes = it.isNotEmpty()
//            binding.emptyNotesImage.toggleVisibility(!hasNotes)
//            binding.homeRecyclerView.toggleVisibility(hasNotes)

            with(binding) {
                emptyNotesImage.toggleVisibility(!hasNotes)
                homeRecyclerView.toggleVisibility(hasNotes)
            }
        }
    }


    private fun setUpRecyclerView() {
        noteAdapter = NoteAdapter()
        binding.homeRecyclerView.apply {
            layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
            setHasFixedSize(true)
            adapter = noteAdapter
        }

        activity?.let {
            noteViewModel.getAllNotes().observe(viewLifecycleOwner) { notes ->
                noteAdapter.differ.submitList(notes)
                updateTheUI(notes)
            }
        }

    }


    private fun searchNote(query: String?) {
        val searchQuery = "%$query"
        noteViewModel.searchNote(searchQuery).observe(viewLifecycleOwner) { notes->
            noteAdapter.differ.submitList(notes)
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

    override fun onDestroy() {
        super.onDestroy()
        homeBinding = null
    }


}