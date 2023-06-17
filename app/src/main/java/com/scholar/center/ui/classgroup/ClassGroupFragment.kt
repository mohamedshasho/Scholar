package com.scholar.center.ui.classgroup

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.scholar.center.R
import com.scholar.center.adapter.MaterialSubjectAdapter
import com.scholar.center.adapter.MaterialAdapter
import com.scholar.center.databinding.FragmentClassGroupBinding
import com.scholar.center.ui.MainFragmentDirections


class ClassGroupFragment : Fragment(R.layout.fragment_class_group) {
    private var materialSubjectAdapter: MaterialSubjectAdapter? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val binding = FragmentClassGroupBinding.bind(view)

        materialSubjectAdapter = MaterialSubjectAdapter { position ->
            materialSubjectAdapter?.setItemSelected(position)
        }

        val materialAdapter = MaterialAdapter(
            navigateToTeacher = { teacherID ->
                findNavController().navigate(MainFragmentDirections.actionMainToTeacher(teacherId = teacherID))
            },
            navigateToDetails = {}
        )

        binding.classGroupMaterialsSubjectsRecyclerView.apply {
            layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
            adapter = materialSubjectAdapter
            setHasFixedSize(true)
        }

        binding.classGroupSubjectsRecyclerView.apply {
            layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
            adapter = materialAdapter
            setHasFixedSize(true)
        }

        binding.classGroupToolbarBackButton.setOnClickListener {
            findNavController().popBackStack()
        }
    }
}