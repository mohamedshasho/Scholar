package com.scholar.center.ui.teachers.teachersSearch

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.MenuInflater
import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.scholar.center.R
import com.scholar.center.adapter.TeacherComparator
import com.scholar.center.adapter.TeacherProfileAdapter
import com.scholar.center.databinding.FragmentTeachersSearchBinding
import com.scholar.center.unit.closeKeyboard
import com.scholar.center.unit.openKeyboard
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class TeachersSearchFragment : Fragment(R.layout.fragment_teachers_search), MenuProvider {

    private val viewModel: TeachersSearchVM by viewModels()
    private val navController by lazy { findNavController() }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        val binding = FragmentTeachersSearchBinding.inflate(inflater)
        val toolbar = binding.teachersSearchToolbar
        (activity as AppCompatActivity).setSupportActionBar(toolbar)
        (activity as AppCompatActivity).supportActionBar?.setDisplayHomeAsUpEnabled(true)
        val menuHost: MenuHost = requireActivity()
        menuHost.addMenuProvider(this, viewLifecycleOwner, Lifecycle.State.RESUMED)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val binding = FragmentTeachersSearchBinding.bind(view)

        val teacherProfileAdapter = TeacherProfileAdapter(TeacherComparator) { id ->
            navController.navigate(
                TeachersSearchFragmentDirections.actionTeachersSearchToTeacher(
                    teacherId = id
                )
            )
        }

        binding.teachersSerachRecyclerView.apply {
            layoutManager =
                GridLayoutManager(requireContext(), 2, LinearLayoutManager.VERTICAL, false)
            adapter = teacherProfileAdapter
        }
        lifecycleScope.launch {
            viewModel.teachers.collectLatest { pagingData ->
                teacherProfileAdapter.submitData(pagingData)
            }
        }

        lifecycleScope.launch {
            viewModel.loading.collectLatest { loading ->
                binding.teachersSerachProgressBar.visibility =
                    if (loading) View.VISIBLE else View.GONE
            }
        }
    }

    override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
        menuInflater.inflate(R.menu.materials_search_bar, menu)
        val search = menu.findItem(R.id.action_search).actionView as SearchView
        search.queryHint = getString(R.string.enter_teacher_name)


        search.onActionViewExpanded()
        search.requestFocus()
        search.openKeyboard(activity)


        search.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(newText: String?): Boolean {
                viewModel.searchForTeachers(newText)
                view?.closeKeyboard(activity)
                return true
            }

            override fun onQueryTextChange(p0: String?): Boolean {
                return false
            }

        })

    }


    override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
        // Handle the menu selection
        return when (menuItem.itemId) {
            android.R.id.home -> {
                navController.popBackStack()
                true
            }
            R.id.action_search -> {
                true
            }
            else -> false
        }
    }
}