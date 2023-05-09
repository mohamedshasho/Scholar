package com.scholar.center.ui.home

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.scholar.center.R
import com.scholar.center.adapter.MaterialSubjectAdapter
import com.scholar.center.adapter.SubjectAdapter
import com.scholar.center.databinding.FragmentHomeBinding
import com.scholar.data.model.MaterialSubject

class HomeFragment : Fragment(R.layout.fragment_home) {
    private var binding: FragmentHomeBinding? = null
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding = FragmentHomeBinding.bind(view)

        val mSubjects = listOf(
            MaterialSubject(name = "name1", image = ""),
            MaterialSubject(name = "name2", image = ""),
            MaterialSubject(name = "name3", image = ""),
            MaterialSubject(name = "name4", image = "")
        )
        val materialSubjectAdapter = MaterialSubjectAdapter(mSubjects)

        val subjectAdapter = SubjectAdapter(mSubjects)

        binding?.homeMaterialsSubjectsRecyclerView?.apply {
            layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
            adapter = materialSubjectAdapter
        }

        binding?.homeSubjectsRecyclerView?.apply {
            layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
            adapter = subjectAdapter
        }


    }

}