package com.scholar.center.ui.teachers

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.scholar.center.R
import com.scholar.center.adapter.TeacherComparator
import com.scholar.center.adapter.TeacherProfileAdapter
import com.scholar.center.databinding.FragmentTeachersBinding
import com.scholar.center.ui.main.MainFragmentDirections
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch


@AndroidEntryPoint
class TeachersFragment : Fragment(R.layout.fragment_teachers) {

    private val viewModel: TeachersVM by viewModels()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val binding = FragmentTeachersBinding.bind(view)


        val rootController = activity?.findNavController(R.id.root_nav_host_fragment)


        val teacherProfileAdapter = TeacherProfileAdapter(TeacherComparator) { teacherId ,imageView->
            imageView.transitionName = teacherId.toString()
            val extras = FragmentNavigatorExtras(
                imageView to teacherId.toString()
            )

            rootController?.navigate(MainFragmentDirections.actionMainToTeacher(teacherId = teacherId),extras)
        }

        binding.teachersProfileRecyclerView.apply {
            layoutManager =
                GridLayoutManager(requireContext(), 2, LinearLayoutManager.VERTICAL, false)
            adapter = teacherProfileAdapter
        }
        binding.teachersSearchView.setOnClickListener {
            rootController?.navigate(MainFragmentDirections.actionHomeToTeachersSearch())
        }
        lifecycleScope.launch {
            viewModel.teachers.collectLatest { pagingData ->
                teacherProfileAdapter.submitData(pagingData)
            }
        }
    }
}