package com.scholar.center.ui.teachers

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.scholar.center.R
import com.scholar.center.adapter.TeacherProfileAdapter
import com.scholar.center.databinding.FragmentTeachersBinding
import com.scholar.center.ui.MainFragmentDirections
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TeachersFragment : Fragment(R.layout.fragment_teachers) {

    private val viewModel: TeachersVM by viewModels()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val binding = FragmentTeachersBinding.bind(view)


        val rootController = activity?.findNavController(R.id.root_nav_host_fragment)

        val teacherProfileAdapter = TeacherProfileAdapter { id ->
            rootController?.navigate(MainFragmentDirections.actionMainToTeacher(teacherId = id))
        }

        binding.teachersProfileRecyclerView.apply {
            layoutManager =
                GridLayoutManager(requireContext(), 2, LinearLayoutManager.VERTICAL, false)
            adapter = teacherProfileAdapter
        }

    }
}