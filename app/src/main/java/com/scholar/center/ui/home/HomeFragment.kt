package com.scholar.center.ui.home

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.scholar.center.R
import com.scholar.center.adapter.MaterialSubjectAdapter
import com.scholar.center.adapter.MaterialAdapter
import com.scholar.center.databinding.FragmentHomeBinding
import com.scholar.center.ui.MainFragmentDirections
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class HomeFragment : Fragment(R.layout.fragment_home) {

    private val viewModel: HomeVM by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val binding = FragmentHomeBinding.bind(view)

        val categoriesShimmer = binding.homeMaterialsSubjectsShimmerLayout
        val materialsShimmer = binding.homeMaterialsShimmerLayout

        val navController = findNavController()
        val rootNavController = activity?.findNavController(R.id.root_nav_host_fragment)

        val materialAdapter = MaterialAdapter(
            navigateToTeacher = { teacherID ->
                rootNavController?.navigate(MainFragmentDirections.actionMainToTeacher(teacherId = teacherID))
            },
            navigateToDetails = { id ->
                rootNavController?.navigate(MainFragmentDirections.actionMainToMaterial(id))
            }
        )
        val materialSubjectAdapter = MaterialSubjectAdapter()
        binding.homeMaterialsSubjectsRecyclerView.apply {
            layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
            adapter = materialSubjectAdapter
        }

        lifecycleScope.launch {
            viewModel.materials.collect { materials ->
                materialAdapter.setList(materials)
            }
        }
        lifecycleScope.launch {
            viewModel.categories.collect { categories ->
                materialSubjectAdapter.setMaterialSubjectList(categories)
            }
        }
        lifecycleScope.launch {
            viewModel.loading.collect { loading ->
                if (loading) {
                    categoriesShimmer.startShimmer()
                    materialsShimmer.startShimmer()
                } else {
                    categoriesShimmer.stopShimmer()
                    materialsShimmer.stopShimmer()
                    categoriesShimmer.visibility = View.GONE
                    materialsShimmer.visibility = View.GONE
                }
            }
        }

        binding.homeSubjectsRecyclerView.apply {
            layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
            adapter = materialAdapter
        }



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