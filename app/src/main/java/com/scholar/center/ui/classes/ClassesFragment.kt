package com.scholar.center.ui.classes

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.scholar.center.R
import com.scholar.center.adapter.CardClassITemAdapter
import com.scholar.center.databinding.FragmentClassesBinding


class ClassesFragment : Fragment(R.layout.fragment_classes) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val binding = FragmentClassesBinding.bind(view)

        val navController = findNavController()
        val layout = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        val classAdapter = CardClassITemAdapter() { id ->
            navController.navigate(ClassesFragmentDirections.actionClassesToClassGroup(classId = id))
        }
        binding.classesRecyclerView.run {
            layoutManager = layout
            adapter = classAdapter
        }
        binding.classesToolbarBackButton.setOnClickListener {
            navController.popBackStack()
        }
    }
}