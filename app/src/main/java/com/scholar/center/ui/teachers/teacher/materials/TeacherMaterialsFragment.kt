package com.scholar.center.ui.teachers.teacher.materials

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import com.scholar.center.R
import com.scholar.center.adapter.MaterialAdapter
import com.scholar.center.databinding.FragmentTeacherMaterialsBinding
import com.scholar.center.model.UiState
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch


@AndroidEntryPoint
class TeacherMaterialsFragment : Fragment(R.layout.fragment_teacher_materials) {

    private val viewModel  : TeacherMaterialsVM by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val binding = FragmentTeacherMaterialsBinding.bind(view)

        val materialsAdapter = MaterialAdapter(
            navigateToDetails = {}
        )

        binding.teacherMaterialsRecyclerView.run {
            layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
            adapter = materialsAdapter
        }

        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.CREATED) {
                viewModel.uiState.collectLatest { state ->
                    when (state) {
                        is UiState.Success -> {
                            state.data?.let { list ->
//                                materialsAdapter.setList(list)
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