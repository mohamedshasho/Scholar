package com.scholar.center.ui.classes

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.scholar.center.R
import com.scholar.center.adapter.CardClassITemAdapter
import com.scholar.center.databinding.FragmentClassesBinding
import com.scholar.domain.model.ClassRoom
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ClassesFragment : Fragment(R.layout.fragment_classes) {

    private val viewModel: ClassesViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val binding = FragmentClassesBinding.bind(view)

        val navController = findNavController()
        val layout = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        val classAdapter = CardClassITemAdapter<ClassRoom> { id ->
            navController.navigate(ClassesFragmentDirections.actionClassesToClassGroup(classId = id))
        }

        binding.classesRecyclerView.run {
            layoutManager = layout
            adapter = classAdapter
        }
        binding.classesToolbarBackButton.setOnClickListener {
            navController.popBackStack()
        }
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.CREATED) {
                launch {
                    viewModel.classRooms.collect { classRooms ->
                        classAdapter.setData(classRooms)
                    }
                }
                launch {
                    viewModel.loading.collect { isLoading ->
                        binding.classesProgressBar.apply {
                            if (!isLoading) {
                                visibility = View.GONE
                            }
                        }
                    }
                }
            }
        }
    }
}