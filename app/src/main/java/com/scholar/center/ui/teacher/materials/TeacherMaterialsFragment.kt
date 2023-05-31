package com.scholar.center.ui.teacher.materials

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.scholar.center.R
import com.scholar.center.adapter.SubjectAdapter
import com.scholar.center.databinding.FragmentTeacherMaterialsBinding
import com.scholar.data.model.CategoryLocal


class TeacherMaterialsFragment : Fragment(R.layout.fragment_teacher_materials) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val binding = FragmentTeacherMaterialsBinding.bind(view)


        val mSubjects = listOf(
            CategoryLocal(name = "name1", image = ""),
            CategoryLocal(name = "name2", image = ""),
            CategoryLocal(name = "name3", image = ""),
            CategoryLocal(name = "name3", image = ""),
            CategoryLocal(name = "name3", image = ""),
            CategoryLocal(name = "name3", image = ""),
            CategoryLocal(name = "name3", image = ""),
            CategoryLocal(name = "name3", image = ""),
            CategoryLocal(name = "name4", image = "")
        )

        val subjectsAdapter = SubjectAdapter(mSubjects){

        }

        binding.teacherMaterialsRecyclerView.run {
            layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
            adapter = subjectsAdapter
        }
    }

}