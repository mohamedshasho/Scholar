package com.scholar.center.ui.teachers

import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.scholar.center.R
import com.scholar.center.adapter.TeacherProfileAdapter
import com.scholar.center.databinding.FragmentTeachersBinding
import com.scholar.center.ui.MainFragmentDirections
import com.scholar.center.unit.Constants.TEACHER_ID_KEY
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

        val rootController = activity?.findNavController(R.id.root_nav_host_fragment)

        val teacherProfileAdapter = TeacherProfileAdapter(mSubjects) { id ->
            rootController?.navigate(MainFragmentDirections.actionMainToTeacher(teacherId = id))
        }

        binding.teachersProfileRecyclerView.apply {
            layoutManager =
                GridLayoutManager(requireContext(), 2, LinearLayoutManager.VERTICAL, false)
            adapter = teacherProfileAdapter
        }

    }
}