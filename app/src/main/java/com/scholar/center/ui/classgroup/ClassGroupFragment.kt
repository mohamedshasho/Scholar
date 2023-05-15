package com.scholar.center.ui.classgroup

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.scholar.center.R
import com.scholar.center.adapter.MaterialSubjectAdapter
import com.scholar.center.adapter.SubjectAdapter
import com.scholar.center.databinding.FragmentClassGroupBinding
import com.scholar.data.model.CategoryLocal


class ClassGroupFragment : Fragment(R.layout.fragment_class_group) {
    private var materialSubjectAdapter: MaterialSubjectAdapter? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val binding = FragmentClassGroupBinding.bind(view)

        val mSubjects = listOf(
            CategoryLocal(name = "name1", image = ""),
            CategoryLocal(name = "name2", image = ""),
            CategoryLocal(name = "name3", image = ""),
            CategoryLocal(name = "name4", image = "")
        )
        materialSubjectAdapter = MaterialSubjectAdapter(mSubjects) { position ->
            materialSubjectAdapter?.setItemSelected(position)
        }

        val subjectAdapter = SubjectAdapter(mSubjects)

        binding.classGroupMaterialsSubjectsRecyclerView.apply {
            layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
            adapter = materialSubjectAdapter
            setHasFixedSize(true)
        }

        binding.classGroupSubjectsRecyclerView.apply {
            layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
            adapter = subjectAdapter
            setHasFixedSize(true)
        }

        binding.classGroupToolbarBackButton.setOnClickListener {
            findNavController().popBackStack()
        }
    }
}