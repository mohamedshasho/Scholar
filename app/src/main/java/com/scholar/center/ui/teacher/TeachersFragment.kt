package com.scholar.center.ui.teacher

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.scholar.center.R
import com.scholar.center.adapter.TeacherProfileAdapter
import com.scholar.center.databinding.FragmentTeachersBinding
import com.scholar.data.model.CategoryLocal

class TeachersFragment : Fragment(R.layout.fragment_teachers) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val binding = FragmentTeachersBinding.bind(view)

        val mSubjects = listOf(
            CategoryLocal(name = "name1", image = ""),
            CategoryLocal(name = "name2", image = ""),
            CategoryLocal(name = "name3", image = ""),
            CategoryLocal(name = "name3", image = ""),
            CategoryLocal(name = "name3", image = ""),
            CategoryLocal(name = "name3", image = ""),
            CategoryLocal(name = "name3", image = ""),
            CategoryLocal(name = "name3", image = ""),
            CategoryLocal(name = "name3", image = ""),
            CategoryLocal(name = "name3", image = ""),
            CategoryLocal(name = "name3", image = ""),
            CategoryLocal(name = "name3", image = ""),
            CategoryLocal(name = "name4", image = "")
        )

        val teacherProfileAdapter = TeacherProfileAdapter(mSubjects)

        binding.teachersProfileRecyclerView.apply {
            layoutManager = GridLayoutManager(requireContext(), 2, LinearLayoutManager.VERTICAL, false)
            adapter = teacherProfileAdapter
        }

    }
}