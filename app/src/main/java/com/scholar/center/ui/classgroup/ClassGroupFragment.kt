package com.scholar.center.ui.classgroup

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
import com.scholar.center.adapter.CategoryAdapter
import com.scholar.center.adapter.MaterialAdapter
import com.scholar.center.databinding.FragmentClassGroupBinding
import com.scholar.center.model.UiState
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch


@AndroidEntryPoint
class ClassGroupFragment : Fragment(R.layout.fragment_class_group) {
    private var categoryAdapter: CategoryAdapter? = null

    private val viewModel: ClassGroupVM by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val binding = FragmentClassGroupBinding.bind(view)

        categoryAdapter = CategoryAdapter { position ->
            categoryAdapter?.setItemSelected(position)
        }

        val materialAdapter = MaterialAdapter(
            navigateToTeacher = { teacherID ->
//                findNavController().navigate(MainFragmentDirections.actionMainToTeacher(teacherId = teacherID))
            },
            navigateToDetails = {}
        )

        binding.classGroupMaterialsSubjectsRecyclerView.apply {
            layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
            adapter = categoryAdapter
        }

        binding.classGroupSubjectsRecyclerView.apply {
            layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
            adapter = materialAdapter
        }

        binding.classGroupToolbarBackButton.setOnClickListener {
            findNavController().popBackStack()
        }


        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.CREATED) {
                viewModel.uiState.collectLatest { state ->
                    when (state) {
                        is UiState.Success -> {
                            state.data?.let { list ->
                                materialAdapter.setList(list)
                            }
                        }
                        is UiState.Error -> {}
                        else -> {
                        }
                    }
                }
            }
        }
    }
}