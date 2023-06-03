package com.scholar.center.ui.home

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.scholar.center.R
import com.scholar.center.adapter.MaterialSubjectAdapter
import com.scholar.center.adapter.SubjectAdapter
import com.scholar.center.databinding.FragmentHomeBinding
import com.scholar.center.model.UiState
import com.scholar.center.ui.MainFragmentDirections
import com.scholar.data.model.CategoryLocal
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class HomeFragment : Fragment(R.layout.fragment_home) {

    private val viewModel: HomeVM by viewModels()


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val binding = FragmentHomeBinding.bind(view)

        val shimmer = binding.homeMaterialsSubjectsShimmerLayout


        val mSubjects = listOf(
            CategoryLocal(name = "name1", image = ""),
            CategoryLocal(name = "name2", image = ""),
            CategoryLocal(name = "name3", image = ""),
            CategoryLocal(name = "name4", image = "")
        )


        val subjectAdapter = SubjectAdapter(mSubjects,
            navigateToTeacher = { teacherID ->
                findNavController().navigate(MainFragmentDirections.actionMainToTeacher(teacherId = teacherID))
            }
        )
        val materialSubjectAdapter = MaterialSubjectAdapter()
        binding.homeMaterialsSubjectsRecyclerView.apply {
            layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
            adapter = materialSubjectAdapter
        }
        lifecycleScope.launch {
            viewModel.categories.collectLatest { state ->
                when (state) {
                    is UiState.Loading -> {
                        shimmer.startShimmer()
                    }
                    is UiState.Success -> {
                        shimmer.stopShimmer()
                        shimmer.hideShimmer()
                        state.data?.let { subjects ->
                            materialSubjectAdapter.setMaterialSubjectList(subjects)
                        }

                    }
                    else -> {
                        shimmer.stopShimmer()
                        shimmer.hideShimmer()
                    }
                }


            }
        }

        binding.homeSubjectsRecyclerView.apply {
            layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
            adapter = subjectAdapter
        }

        val navController = findNavController()
        val rootNavController = activity?.findNavController(R.id.root_nav_host_fragment)

        binding.homeStagePrimaryButton.setOnClickListener {
            rootNavController?.navigate(MainFragmentDirections.actionMainToClasses(stageId = 0))
        }
        binding.homeStageMediumButton.setOnClickListener {
            rootNavController?.navigate(MainFragmentDirections.actionMainToClasses(stageId = 1))
        }
        binding.homeStageSecondaryButton.setOnClickListener {
            rootNavController?.navigate(MainFragmentDirections.actionMainToClasses(stageId = 2))
        }
        binding.homeSubjectsSeeAllText.setOnClickListener {
            rootNavController?.navigate(MainFragmentDirections.actionHomeToSubjects())
        }
    }

}