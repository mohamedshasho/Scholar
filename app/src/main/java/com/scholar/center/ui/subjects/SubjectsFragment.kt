package com.scholar.center.ui.subjects

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
import com.scholar.center.adapter.MaterialAdapter
import com.scholar.center.databinding.FragmentSubjectsBinding
import com.scholar.center.model.UiState
import com.scholar.center.ui.MainFragmentDirections
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SubjectsFragment : Fragment(R.layout.fragment_subjects) {

    private val viewModel: SubjectsVM by viewModels()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val binding = FragmentSubjectsBinding.bind(view)

        val subjectsAdapter = MaterialAdapter(
            navigateToTeacher = { teacherID ->
                findNavController().navigate(MainFragmentDirections.actionMainToTeacher(teacherId = teacherID))
            },
            navigateToDetails = {}
        )

        binding.subjectsRecyclerView.run {
            layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
            adapter = subjectsAdapter
        }

        binding.subjectsToolbarBackButton.setOnClickListener {
            findNavController().popBackStack()
        }

        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.CREATED) {
                viewModel.uiState.collectLatest { state ->
                    when (state) {
                        is UiState.Success -> {
                            state.data?.let { list ->
                                subjectsAdapter.setList(list)
                            }
                        }
                        is UiState.Error -> {}
                        else -> {}
                    }
                }
            }
        }
    }
}