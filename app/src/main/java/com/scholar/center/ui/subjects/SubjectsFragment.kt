package com.scholar.center.ui.subjects

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.scholar.center.R
import com.scholar.center.adapter.SubjectAdapter
import com.scholar.center.databinding.FragmentSubjectsBinding
import com.scholar.center.ui.MainFragmentDirections


class SubjectsFragment : Fragment(R.layout.fragment_subjects) {


    private val viewModel: SubjectsVM by viewModels()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val binding = FragmentSubjectsBinding.bind(view)

        val subjectsAdapter = SubjectAdapter(viewModel.mSubjects,
            navigateToTeacher = { teacherID ->
                findNavController().navigate(MainFragmentDirections.actionMainToTeacher(teacherId = teacherID))
            }
        )
        binding.subjectsRecyclerView.run {
            layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
            adapter = subjectsAdapter
        }

        binding.subjectsToolbarBackButton.setOnClickListener {
            findNavController().popBackStack()
        }
    }
}