package com.scholar.center.ui.teacher.materials

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.scholar.center.R
import com.scholar.center.adapter.MaterialAdapter
import com.scholar.center.databinding.FragmentTeacherMaterialsBinding


class TeacherMaterialsFragment : Fragment(R.layout.fragment_teacher_materials) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val binding = FragmentTeacherMaterialsBinding.bind(view)

        val subjectsAdapter = MaterialAdapter(
            navigateToDetails = {}
        )

        binding.teacherMaterialsRecyclerView.run {
            layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
            adapter = subjectsAdapter
        }
    }

}